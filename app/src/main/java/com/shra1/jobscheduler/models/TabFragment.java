package com.shra1.jobscheduler.models;

import android.support.v4.app.Fragment;

public class TabFragment {
    Fragment fragment;
    String title;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TabFragment(Fragment fragment, String title) {

        this.fragment = fragment;
        this.title = title;
    }

    public TabFragment() {

    }
}
