package com.shra1.jobscheduler.fragments.settingmodules;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.shra1.jobscheduler.R;

import static com.shra1.jobscheduler.App.spm;

public class SMAutostartConfigModule extends Fragment {
    CheckBox cbAutostart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sm_autostart_config_module, container, false);

        initViews(v);

        checkStatus();

        cbAutostart.setOnClickListener(cb->{
            CheckBox c = (CheckBox) cb;
            if (c.isChecked()){
                spm.setAUTOSTART(true);
            }else{
                spm.setAUTOSTART(false);
            }
        });

        return v;
    }

    private void checkStatus() {
        if (spm.getAUTOSTART()) {
            cbAutostart.setChecked(true);
        } else {
            cbAutostart.setChecked(false);
        }
    }

    private void initViews(View v) {
        cbAutostart = v.findViewById(R.id.cbAutostart);
    }
}