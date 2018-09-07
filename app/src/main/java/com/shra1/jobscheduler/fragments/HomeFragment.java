package com.shra1.jobscheduler.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.database.models.LocationEntry;
import com.shra1.jobscheduler.services.BgService;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class HomeFragment extends Fragment {

    private static HomeFragment IN = null;
    DateTime dateTime;
    DateTime fromDateTime;
    DateTime toDateTime;
    Context mCtx;
    private TextView tvCurrentLocation;
    private Button bLoadCurrentLocation;
    private SupportMapFragment map;
    private GoogleMap mMap;
    private ImageButton ibPrev;
    private TextView tvDate;
    private ImageButton ibNext;
    private FloatingActionButton fabLoad;

    private EditText etFromTime;
    private EditText etToTime;

    private LinearLayout llTimeSelector;

    public static HomeFragment getInstance() {
        if (IN == null) {
            IN = new HomeFragment();
        }
        return IN;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCtx = container.getContext();
        View v = inflater.inflate(R.layout.home_fragment, container, false);

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

        map.getMapAsync(googleMap -> {
            mMap = googleMap;
            bLoadCurrentLocation.setOnClickListener(b -> {
                String text = "Background service is not running!";
                if (BgService.getInstance() != null) {
                    text = "Fetching location may take a while please wait and try again.";
                    if (BgService.getInstance().getCurrentLocation() != null) {
                        Location l = BgService.getInstance().getCurrentLocation();
                        text = "Current location : " + l.getLatitude() + ", " + l.getLongitude();
                        LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(latLng));
                    }
                }
                tvCurrentLocation.setText(text);
            });

            fabLoad.setOnClickListener(f -> {
                DateTime parsedFromDateTime = getParsedDateTime(etFromTime.getText().toString().trim());
                DateTime parsedToDateTime = getParsedDateTime(etToTime.getText().toString().trim());

                fromDateTime = fromDateTime.withHourOfDay(parsedFromDateTime.getHourOfDay());
                fromDateTime = fromDateTime.withMinuteOfHour(parsedFromDateTime.getMinuteOfHour());
                fromDateTime = fromDateTime.withSecondOfMinute(parsedFromDateTime.getSecondOfMinute());

                toDateTime = toDateTime.withHourOfDay(parsedToDateTime.getHourOfDay());
                toDateTime = toDateTime.withMinuteOfHour(parsedToDateTime.getMinuteOfHour());
                toDateTime = toDateTime.withSecondOfMinute(parsedToDateTime.getSecondOfMinute());

                LocationEntry.DBCommands.getDataFor(fromDateTime, toDateTime, locationEntries -> {
                    mMap.clear();
                    PolylineOptions polylineOptions = new PolylineOptions()
                            .width(5)
                            .color(Color.BLACK);

                    LatLng latLng = null;
                    for (LocationEntry e : locationEntries) {
                        latLng = new LatLng(e.getLatitude(), e.getLongitude());
                        polylineOptions.add(latLng);
                    }
                    if (latLng != null) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                    }
                    Polyline line = mMap.addPolyline(polylineOptions);

                    llTimeSelector.setVisibility(View.GONE);


                });
            });

            fabLoad.setOnLongClickListener(v1 -> {
                //show selector

                llTimeSelector.setVisibility(View.VISIBLE);

                return true;
            });
        });

        etFromTime.setOnClickListener(et -> {
            DateTime parsedFromDateTime = getParsedDateTime(etFromTime.getText().toString().trim());
           TimePickerDialog timePickerDialog = new TimePickerDialog(mCtx, (view, hourOfDay, minute) -> {
               fromDateTime = fromDateTime.withHourOfDay(hourOfDay);
               fromDateTime = fromDateTime.withMinuteOfHour(minute);
               etFromTime.setText(fromDateTime.toString(mCtx.getResources().getString(R.string.time_format)));

           },
                    parsedFromDateTime.getHourOfDay(),
                    parsedFromDateTime.getMinuteOfHour(),
                    false);

        timePickerDialog.show();
        });

        etToTime.setOnClickListener(et -> {
            DateTime parsedToDateTime = getParsedDateTime(etToTime.getText().toString().trim());
            TimePickerDialog timePickerDialog = new TimePickerDialog(mCtx, (view, hourOfDay, minute) -> {
                toDateTime = toDateTime.withHourOfDay(hourOfDay);
                toDateTime = toDateTime.withMinuteOfHour(minute);
                etToTime.setText(toDateTime.toString(mCtx.getResources().getString(R.string.time_format)));

            },
                    parsedToDateTime.getHourOfDay(),
                    parsedToDateTime.getMinuteOfHour(),
                    false);
            timePickerDialog.show();
        });

        return v;
    }

    private DateTime getParsedDateTime(String text) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(mCtx.getResources().getString(R.string.time_format));
        return dateTimeFormatter.parseDateTime(text);

    }

    private void loadData() {
        tvDate.setText(dateTime.toString(mCtx.getResources().getString(R.string.date_format)));
        fromDateTime = dateTime;
        fromDateTime = fromDateTime.withHourOfDay(00);
        fromDateTime = fromDateTime.withMinuteOfHour(00);
        fromDateTime = fromDateTime.withSecondOfMinute(00);
        fromDateTime = fromDateTime.withMillisOfSecond(00);
        toDateTime = dateTime;
        toDateTime = toDateTime.withHourOfDay(23);
        toDateTime = toDateTime.withMinuteOfHour(59);
        toDateTime = toDateTime.withSecondOfMinute(59);
        toDateTime = toDateTime.withMillisOfSecond(999);
        etFromTime.setText(fromDateTime.toString(mCtx.getResources().getString(R.string.time_format)));
        etToTime.setText(toDateTime.toString(mCtx.getResources().getString(R.string.time_format)));
    }

    private void initViews(View v) {
        tvCurrentLocation = (TextView) v.findViewById(R.id.tvCurrentLocation);
        bLoadCurrentLocation = (Button) v.findViewById(R.id.bLoadCurrentLocation);
        map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        ibPrev = (ImageButton) v.findViewById(R.id.ibPrev);
        tvDate = (TextView) v.findViewById(R.id.tvDate);
        ibNext = (ImageButton) v.findViewById(R.id.ibNext);
        fabLoad = v.findViewById(R.id.fabLoad);
        etFromTime = v.findViewById(R.id.etFromTime);
        etToTime = v.findViewById(R.id.etToTime);
        etFromTime.setFocusable(false);
        etToTime.setFocusable(false);
        llTimeSelector = v.findViewById(R.id.llTimeSelector);
        llTimeSelector.setVisibility(View.GONE);
    }
}
