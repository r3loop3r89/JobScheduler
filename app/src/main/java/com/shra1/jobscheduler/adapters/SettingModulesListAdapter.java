package com.shra1.jobscheduler.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.fragments.settingmodules.SettingModule;

import java.util.List;

public class SettingModulesListAdapter extends BaseAdapter {

    Context mCtx;
    List<SettingModule> l;
    FragmentManager fragmentManager;

    public SettingModulesListAdapter(Context mCtx, List<SettingModule> l, FragmentManager fragmentManager) {
        this.mCtx = mCtx;
        this.l = l;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Override
    public Object getItem(int position) {
        return l.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        SMLAViewHolder h;
        if (v == null) {
            v = LayoutInflater.from(mCtx).inflate(R.layout.setting_modules_list_item, parent, false);
            h = new SMLAViewHolder(v);
            v.setTag(h);
        } else {
            h = (SMLAViewHolder) v.getTag();
        }

        SettingModule s = (SettingModule) getItem(position);

        int containerId = h.flFragmentContainer.getId();
        Fragment oldFragment = fragmentManager.findFragmentById(containerId);
        if (oldFragment != null) {
            fragmentManager.beginTransaction().remove(oldFragment).commit();
        }
        int newContainerId = GetUniqueID();// My method
        h.flFragmentContainer.setId(newContainerId);// Set container id
        fragmentManager.beginTransaction().replace(newContainerId, s.getFragment()).commit();

        h.tvTextView.setText(s.getTitle());

        return v;
    }


    public int GetUniqueID() {
        return 111 + (int) (Math.random() * 9999);
    }

    static class SMLAViewHolder {
        TextView tvTextView;
        FrameLayout flFragmentContainer;

        public SMLAViewHolder(View v) {
            tvTextView = v.findViewById(R.id.tvTextView);
            flFragmentContainer = v.findViewById(R.id.flFragmentContainer);
        }
    }
}
