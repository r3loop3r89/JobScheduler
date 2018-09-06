package com.shra1.jobscheduler.database.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LocationEntryDao {
    @Insert
    public void addLocationEntry(LocationEntry locationEntry);

    @Query("Select * from LocationEntry where entryOnEpoch >= :fromMillis and entryOnEpoch <= :toMillis")
    public List<LocationEntry> getDataFor(long fromMillis, long toMillis);

    @Query("Select * from LocationEntry")
    List<LocationEntry> getAllData();
}
