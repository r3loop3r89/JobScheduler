package com.shra1.jobscheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.adapters.SettingModulesListAdapter;
import com.shra1.jobscheduler.fragments.settingmodules.SMAutostartConfigModule;
import com.shra1.jobscheduler.fragments.settingmodules.SMBGServiceModule;
import com.shra1.jobscheduler.fragments.settingmodules.SMDataBackupModule;
import com.shra1.jobscheduler.fragments.settingmodules.SMIntervalConfigModule;
import com.shra1.jobscheduler.fragments.settingmodules.SMProviderConfigModule;
import com.shra1.jobscheduler.fragments.settingmodules.SettingModule;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private static SettingsFragment IN = null;
    ListView lvSFSettingModules;
    Context mCtx;

    public static SettingsFragment getInstance() {
        if (IN == null) {
            IN = new SettingsFragment();
        }
        return IN;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCtx = container.getContext();
        View v = inflater.inflate(R.layout.settings_fragment, container, false);

        initViews(v);

        List<SettingModule> settingModules = new ArrayList<>();

        settingModules.add(new SettingModule("Background Service", new SMBGServiceModule()));
        settingModules.add(new SettingModule("Provider", new SMProviderConfigModule()));
        settingModules.add(new SettingModule("Interval", new SMIntervalConfigModule()));
        settingModules.add(new SettingModule("Autostart", new SMAutostartConfigModule()));
        settingModules.add(new SettingModule("Autostart", new SMDataBackupModule()));

        SettingModulesListAdapter adapter = new SettingModulesListAdapter(mCtx, settingModules, getFragmentManager());
        lvSFSettingModules.setAdapter(adapter);

        return v;
    }

    private void initViews(View v) {
        lvSFSettingModules = v.findViewById(R.id.lvSFSettingModules);
    }
}
