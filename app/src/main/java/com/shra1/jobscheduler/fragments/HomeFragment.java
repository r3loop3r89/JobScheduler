package com.shra1.jobscheduler.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.services.BgService;

public class HomeFragment extends Fragment {

    private static HomeFragment IN = null;
    private TextView tvCurrentLocation;
    private Button bLoadCurrentLocation;

    public static HomeFragment getInstance() {
        if (IN == null) {
            IN = new HomeFragment();
        }
        return IN;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.home_fragment, container, false);

        initViews(v);

        bLoadCurrentLocation.setOnClickListener(b -> {
            String text = "Background service is not running!";
            if (BgService.getInstance() != null) {
                text = "Fetching location may take a while please wait and try again.";
                if (BgService.getInstance().getCurrentLocation() != null) {
                    Location l = BgService.getInstance().getCurrentLocation();
                    text = "Current location : " + l.getLatitude() + ", " + l.getLongitude();
                }
            }
            tvCurrentLocation.setText(text);
        });

        return v;
    }

    private void initViews(View v) {
        tvCurrentLocation = (TextView) v.findViewById(R.id.tvCurrentLocation);
        bLoadCurrentLocation = (Button) v.findViewById(R.id.bLoadCurrentLocation);
    }
}
