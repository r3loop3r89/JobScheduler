package com.shra1.jobscheduler.fragments.settingmodules;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.utils.Utils;

import static com.shra1.jobscheduler.App.spm;

public class
SMProviderConfigModule extends Fragment {
    Context mCtx;
    private RadioButton rbNetworkProvider;
    private RadioButton rbGPSProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCtx = container.getContext();

        View v = inflater.inflate(R.layout.sm_provider_config_module_layout, container, false);

        initViews(v);

        checkSelectedProvider();

        rbNetworkProvider.setOnClickListener(rb -> {
            spm.setPROVIDER(LocationManager.NETWORK_PROVIDER);
            Utils.showRestartServiceToast(mCtx);
        });

        rbGPSProvider.setOnClickListener(rb -> {
            spm.setPROVIDER(LocationManager.GPS_PROVIDER);
            Utils.showRestartServiceToast(mCtx);
        });

        return v;
    }

    private void checkSelectedProvider() {
        if (spm.getPROVIDER().equals(LocationManager.NETWORK_PROVIDER)) {
            rbNetworkProvider.setChecked(true);
        } else if (spm.getPROVIDER().equals(LocationManager.GPS_PROVIDER)) {
            rbGPSProvider.setChecked(true);
        }
    }

    private void initViews(View v) {
        rbNetworkProvider = (RadioButton) v.findViewById(R.id.rbNetworkProvider);
        rbGPSProvider = (RadioButton) v.findViewById(R.id.rbGPSProvider);
    }
}
