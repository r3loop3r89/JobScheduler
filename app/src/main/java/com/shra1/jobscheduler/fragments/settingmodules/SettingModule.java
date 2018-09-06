package com.shra1.jobscheduler.fragments.settingmodules;

import android.support.v4.app.Fragment;

public class SettingModule {
    String title;
    Fragment fragment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public SettingModule(String title, Fragment fragment) {

        this.title = title;
        this.fragment = fragment;
    }

    public SettingModule() {

    }
}
