package com.shra1.jobscheduler.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.database.models.LocationEntry;

import org.joda.time.DateTime;

import java.util.List;

public class LocationEntriesListAdapter extends BaseAdapter {
    Context mCtx;
    List<LocationEntry> le;

    public LocationEntriesListAdapter(Context mCtx, List<LocationEntry> le) {
        this.mCtx = mCtx;
        this.le = le;
    }

    @Override
    public int getCount() {
        return le.size();
    }

    @Override
    public Object getItem(int position) {
        return le.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LELAViewHolder h;
        if (v == null) {
            v = LayoutInflater.from(mCtx).inflate(R.layout.location_entries_list_item_layout, parent, false);
            h = new LELAViewHolder(v);
            v.setTag(h);
        } else {
            h = (LELAViewHolder) v.getTag();
        }

        LocationEntry e = (LocationEntry) getItem(position);
        h.tvBattery.setText(""+e.getBatteryLevel());
        h.tvId.setText("" + e.getLeid());
        h.tvLatLong.setText("Lat : " + e.getLatitude() + "\nLon : " + e.getLongitude());
        DateTime d = new DateTime(e.getEntryOnEpoch());
        h.tvTime.setText(d.toString(mCtx.getResources().getString(R.string.time_format)));
        return v;
    }

    static class LELAViewHolder {
        TextView tvId;
        TextView tvLatLong;
        TextView tvBattery;
        TextView tvTime;

        public LELAViewHolder(View v) {
            tvId = v.findViewById(R.id.tvId);
            tvLatLong = v.findViewById(R.id.tvLatLong);
            tvBattery = v.findViewById(R.id.tvBattery);
            tvTime = v.findViewById(R.id.tvTime);
        }
    }
}
