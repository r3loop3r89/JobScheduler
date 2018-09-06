package com.shra1.jobscheduler;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.shra1.jobscheduler.database.Db;
import com.shra1.jobscheduler.utils.SharedPreferencesManager;

public class App extends Application {

    public static SharedPreferencesManager spm;

    public static Db db = null;

    @Override
    public void onCreate() {
        super.onCreate();

        spm=SharedPreferencesManager.getInstance(this);

        db = Room.databaseBuilder(this, Db.class, "shr.db").build();

    }
}
