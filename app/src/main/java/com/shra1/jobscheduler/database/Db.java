package com.shra1.jobscheduler.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.shra1.jobscheduler.database.models.LocationEntry;
import com.shra1.jobscheduler.database.models.LocationEntryDao;

@Database(entities = {LocationEntry.class}, version = 1)
public abstract class Db extends RoomDatabase {
    public abstract LocationEntryDao getLocationEntryDao();

}
