package com.shra1.jobscheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.adapters.LocationEntriesListAdapter;
import com.shra1.jobscheduler.database.models.LocationEntry;

import org.joda.time.DateTime;

public class DetailsFragment extends Fragment {
    private static DetailsFragment IN = null;
    DateTime dateTime;
    Context mCtx;
    ListView lvList;
    private ImageButton ibPrev;
    private TextView tvDate;
    private ImageButton ibNext;

    public static DetailsFragment getInstance() {
        if (IN == null) {
            IN = new DetailsFragment();
        }
        return IN;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCtx = container.getContext();
        View v = inflater.inflate(R.layout.details_fragment, container, false);

        initViews(v);

        dateTime = new DateTime();
        loadData();

        ibPrev.setOnClickListener(b -> {
            dateTime = dateTime.minusDays(1);
            loadData();
        });

        tvDate.setOnClickListener(tv -> {
            dateTime = new DateTime();
            loadData();
        });

        ibNext.setOnClickListener(b -> {
            dateTime = dateTime.plusDays(1);
            loadData();
        });

        return v;
    }

    private void loadData() {
        tvDate.setText(dateTime.toString(mCtx.getResources().getString(R.string.date_format)));
        LocationEntry.DBCommands.getDataFor(dateTime, le -> {
            LocationEntriesListAdapter
                    adapter = new LocationEntriesListAdapter(mCtx, le);
            lvList.setAdapter(adapter);
        });
    }

    private void initViews(View v) {
        ibPrev = (ImageButton) v.findViewById(R.id.ibPrev);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        ibNext = (ImageButton) v.findViewById(R.id.ibNext);
        lvList = v.findViewById(R.id.lvList);
    }
}
