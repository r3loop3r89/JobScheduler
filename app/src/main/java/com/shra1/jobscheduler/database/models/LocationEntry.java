package com.shra1.jobscheduler.database.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.AsyncTask;

import org.joda.time.DateTime;

import java.util.List;

import static com.shra1.jobscheduler.App.db;

@Entity
public class LocationEntry {
    @PrimaryKey(autoGenerate = true)
    long leid;

    double latitude;
    double longitude;
    long entryOnEpoch;
    int batteryLevel;

    public LocationEntry(double latitude, double longitude, long entryOnEpoch, int batteryLevel) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.entryOnEpoch = entryOnEpoch;
        this.batteryLevel = batteryLevel;
    }

    public LocationEntry() {

    }

    public long getLeid() {
        return leid;
    }

    public void setLeid(long leid) {
        this.leid = leid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getEntryOnEpoch() {
        return entryOnEpoch;
    }

    public void setEntryOnEpoch(long entryOnEpoch) {
        this.entryOnEpoch = entryOnEpoch;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public static class DBCommands {

        public static void addLocationEntry(LocationEntry locationEntry) {
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    db.getLocationEntryDao().addLocationEntry(locationEntry);
                    return null;
                }
            };
            asyncTask.execute();
        }

        public static void getDataFor(DateTime dateTime, getDataForCallback c) {
            AsyncTask<Void, Void, List<LocationEntry>>
                    voidVoidListAsyncTask = new AsyncTask<Void, Void, List<LocationEntry>>() {
                @Override
                protected List<LocationEntry> doInBackground(Void... voids) {
                    DateTime from = dateTime;
                    from = from.withHourOfDay(00);
                    from = from.withMinuteOfHour(00);
                    from = from.withSecondOfMinute(00);
                    from = from.withMillisOfSecond(00);
                    DateTime to = dateTime;
                    to = to.withHourOfDay(23);
                    to = to.withMinuteOfHour(59);
                    to = to.withSecondOfMinute(59);
                    to = to.withMillisOfSecond(999);

                    return db.getLocationEntryDao().getDataFor(from.getMillis(), to.getMillis());
                }

                @Override
                protected void onPostExecute(List<LocationEntry> locationEntries) {
                    super.onPostExecute(locationEntries);
                    c.onComplete(locationEntries);
                }
            };

            voidVoidListAsyncTask.execute();
        }

        public static void getAllData(getDataForCallback c) {
            AsyncTask<Void, Void, List<LocationEntry>>
                    asyncTask = new AsyncTask<Void, Void, List<LocationEntry>>() {
                @Override
                protected List<LocationEntry> doInBackground(Void... voids) {
                    return db.getLocationEntryDao().getAllData();
                }

                @Override
                protected void onPostExecute(List<LocationEntry> locationEntries) {
                    super.onPostExecute(locationEntries);
                    c.onComplete(locationEntries);
                }
            };

            asyncTask.execute();
        }

        public interface getDataForCallback {
            public void onComplete(List<LocationEntry> locationEntries);
        }
    }
}
