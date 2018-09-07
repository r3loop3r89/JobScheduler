package com.shra1.jobscheduler.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.shra1.jobscheduler.database.models.LocationEntry;
import com.shra1.jobscheduler.models.SelectedTimeUnit;
import com.shra1.jobscheduler.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.shra1.jobscheduler.App.spm;

public class BgService extends Service {

    private static BgService IN = null;
    LocationManager locationManager;
    Location currentLocation = null;
    LocationListener ll = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    Disposable disposable;
    PowerManager.WakeLock wakeLock;

    public static BgService getInstance() {
        return IN;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        IN = null;
        locationManager.removeUpdates(ll);
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        wakeLock.release();
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "ShrAwake");

        wakeLock.acquire();

        IN = this;

        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                spm.getPROVIDER(), 0L, 0F,
                ll
        );


        Observable.interval(
                spm.getPERIOD(),
                SelectedTimeUnit.getList().get(spm.getSELECTEDINTERVALUNITPOSITION()).getTimeUnit())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        //((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(1000);
                        if (currentLocation != null) {
                            LocationEntry locationEntry = new LocationEntry(
                                    currentLocation.getLatitude(),
                                    currentLocation.getLongitude(),
                                    System.currentTimeMillis(),
                                    Utils.getBatteryPercentage(getApplicationContext())
                            );

                            LocationEntry.DBCommands.addLocationEntry(locationEntry);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
