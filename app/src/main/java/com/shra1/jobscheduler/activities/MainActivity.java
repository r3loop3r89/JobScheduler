package com.shra1.jobscheduler.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.adapters.TabFragmentsAdapter;
import com.shra1.jobscheduler.fragments.DetailsFragment;
import com.shra1.jobscheduler.fragments.HomeFragment;
import com.shra1.jobscheduler.fragments.SettingsFragment;
import com.shra1.jobscheduler.models.TabFragment;
import com.shra1.jobscheduler.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager vpMAViewPager;
    TabLayout tlMATabs;
    Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCtx = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 121
            );
        } else {
            MAIN();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Utils.showToast(mCtx, "Please allow all permissions to continue");
                finish();
                return;
            }
        }
        MAIN();
    }

    private void MAIN() {
        Utils.setupToolbar(this);

        vpMAViewPager = findViewById(R.id.vpMAViewPager);
        tlMATabs = findViewById(R.id.tlMATabs);

        List<TabFragment> tabFragments = new ArrayList<>();
        tabFragments.add(new TabFragment(HomeFragment.getInstance(), "Home"));
        tabFragments.add(new TabFragment(DetailsFragment.getInstance(), "Details"));
        tabFragments.add(new TabFragment(SettingsFragment.getInstance(), "Settings"));

        TabFragmentsAdapter adapter = new TabFragmentsAdapter(getSupportFragmentManager(), tabFragments);
        vpMAViewPager.setAdapter(adapter);
        tlMATabs.setupWithViewPager(vpMAViewPager);

        Utils.checkAutostartAndStart(mCtx);
    }

}
