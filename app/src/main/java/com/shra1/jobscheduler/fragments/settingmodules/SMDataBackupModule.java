package com.shra1.jobscheduler.fragments.settingmodules;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.FileIOUtils;
import com.google.gson.Gson;
import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.database.models.LocationEntry;

import java.io.File;
import java.util.List;

public class SMDataBackupModule extends Fragment {
    Button bBackup;
    Button bRestore;
    File file;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sm_backup_module, container, false);
        initViews(v);

        file = new File("/sdcard/file.txt");

        bBackup.setOnClickListener(b -> {
            LocationEntry.DBCommands.getAllData(le -> {
                String text = new Gson().toJson(le);
                FileIOUtils.writeFileFromString(file, text);
            });
        });

        bRestore.setOnClickListener(b->{
            if (file.exists()){
                String text = FileIOUtils.readFile2String(file);
                LocationEntry[] locationEntries = new Gson().fromJson(text, LocationEntry[].class);
                for (LocationEntry e : locationEntries){
                    LocationEntry.DBCommands.addLocationEntry(e);
                }
            }
        });

        return v;
    }

    private void initViews(View v) {
        bBackup = v.findViewById(R.id.bBackup);
        bRestore = v.findViewById(R.id.bRestore);
    }
}
