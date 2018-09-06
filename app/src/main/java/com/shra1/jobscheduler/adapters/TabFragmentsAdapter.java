package com.shra1.jobscheduler.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shra1.jobscheduler.models.TabFragment;

import java.util.List;

public class TabFragmentsAdapter extends FragmentPagerAdapter {

    List<TabFragment> l;

    public TabFragmentsAdapter(FragmentManager fm, List<TabFragment> l) {
        super(fm);
        this.l = l;
    }

    @Override
    public Fragment getItem(int position) {
        return l.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return l.get(position).getTitle();
    }
}
