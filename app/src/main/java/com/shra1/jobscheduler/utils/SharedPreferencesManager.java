package com.shra1.jobscheduler.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;

public class SharedPreferencesManager {
    public static SharedPreferencesManager IN = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private SharedPreferencesManager(Context mCtx) {
        sharedPreferences = mCtx.getSharedPreferences("ss", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPreferencesManager getInstance(Context mCtx) {
        if (IN == null) {
            IN = new SharedPreferencesManager(mCtx);
        }
        return IN;
    }


    public boolean getAUTOSTART() {
        return sharedPreferences.getBoolean("AUTOSTART", false);
    }

    public void setAUTOSTART(boolean AUTOSTART) {
        editor.putBoolean("AUTOSTART", AUTOSTART);
        editor.commit();
    }

    public String getPROVIDER() {
        return sharedPreferences.getString("PROVIDER", LocationManager.NETWORK_PROVIDER);
    }

    public void setPROVIDER(String PROVIDER) {
        editor.putString("PROVIDER", PROVIDER);
        editor.commit();
    }

    public long getPERIOD() {
        return sharedPreferences.getLong("PERIOD", 10);
    }

    public void setPERIOD(long PERIOD) {
        editor.putLong("PERIOD", PERIOD);
        editor.commit();
    }

    public int getSELECTEDINTERVALUNITPOSITION() {
        return sharedPreferences.getInt("SELECTEDINTERVALUNITPOSITION", 0);
    }

    public void setSELECTEDINTERVALUNITPOSITION(int SELECTEDINTERVALUNITPOSITION) {
        editor.putInt("SELECTEDINTERVALUNITPOSITION", SELECTEDINTERVALUNITPOSITION);
        editor.commit();
    }
}
