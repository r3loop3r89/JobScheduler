package com.shra1.jobscheduler.fragments.settingmodules;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.services.BgService;
import com.shra1.jobscheduler.utils.Utils;

public class SMBGServiceModule extends Fragment {
    Context mCtx;
    private TextView tvBGServiceStatus;
    private Button bStartService;
    private Button bStopService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCtx = container.getContext();
        View v = inflater.inflate(R.layout.sm_bg_service_module, container, false);

        initViews(v);

        checkIfServiceIsRunningAndUpdateStatus();

        Intent serviceIntent = new Intent(mCtx, BgService.class);

        bStartService.setOnClickListener(b -> {
            mCtx.startService(serviceIntent);
            checkIfServiceIsRunningAndUpdateStatus();
        });

        bStopService.setOnClickListener(b -> {
            mCtx.stopService(serviceIntent);
            checkIfServiceIsRunningAndUpdateStatus();
        });

        return v;
    }

    private void checkIfServiceIsRunningAndUpdateStatus() {
        String text;
        if (Utils.isMyServiceRunning(mCtx, BgService.class)) {
            text = "Service is <font color='green'>Running</font>";
            bStartService.setEnabled(false);
            bStopService.setEnabled(true);
        } else {
            text = "Service is <font color='red'>Not Running</font>";
            bStartService.setEnabled(true);
            bStopService.setEnabled(false);
        }
        Spanned spanned = Html.fromHtml(text);
        tvBGServiceStatus.setText(spanned);
    }

    private void initViews(View v) {
        tvBGServiceStatus = (TextView) v.findViewById(R.id.tvBGServiceStatus);
        bStartService = (Button) v.findViewById(R.id.bStartService);
        bStopService = (Button) v.findViewById(R.id.bStopService);
    }
}
