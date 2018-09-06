package com.shra1.jobscheduler.fragments.settingmodules;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.models.SelectedTimeUnit;
import com.shra1.jobscheduler.utils.Utils;

import java.util.List;

import static com.shra1.jobscheduler.App.spm;

public class SMIntervalConfigModule extends Fragment {

    EditText etPeriod;
    Spinner spSelectedTimeUnit;
    Button bSave;

    Context mCtx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCtx = container.getContext();
        View v = inflater.inflate(R.layout.sm_interval_config_module, container, false);

        initViews(v);

        fillUpSpinner();

        loadSavedIntervalConfigData();

        bSave.setOnClickListener(b -> {
            if (Utils.cantBeEmptyET(etPeriod)) return;
            String period = etPeriod.getText().toString().trim();
            spm.setPERIOD(Long.parseLong(period));
            spm.setSELECTEDINTERVALUNITPOSITION(spSelectedTimeUnit.getSelectedItemPosition());
            Utils.showRestartServiceToast(mCtx);
        });

        return v;
    }

    private void loadSavedIntervalConfigData() {
        etPeriod.setText("" + spm.getPERIOD());
        spSelectedTimeUnit.setSelection(spm.getSELECTEDINTERVALUNITPOSITION());
    }

    private void fillUpSpinner() {
        List<SelectedTimeUnit> selectedTimeUnits = SelectedTimeUnit.getList();
        ArrayAdapter<SelectedTimeUnit> selectedTimeUnitArrayAdapter = new ArrayAdapter<SelectedTimeUnit>(mCtx,
                android.R.layout.simple_spinner_item, selectedTimeUnits);
        selectedTimeUnitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSelectedTimeUnit.setAdapter(selectedTimeUnitArrayAdapter);
    }

    private void initViews(View v) {
        bSave = v.findViewById(R.id.bSave);
        etPeriod = v.findViewById(R.id.etPeriod);
        spSelectedTimeUnit = v.findViewById(R.id.spSelectedTimeUnit);
    }
}
