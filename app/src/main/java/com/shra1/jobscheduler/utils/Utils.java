package com.shra1.jobscheduler.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.shra1.jobscheduler.R;
import com.shra1.jobscheduler.services.BgService;

import static com.shra1.jobscheduler.App.spm;

public class Utils {

    public static void setupToolbar(AppCompatActivity a) {
        Toolbar tbToolbar;
        tbToolbar = a.findViewById(R.id.tbToolbar);
        a.setSupportActionBar(tbToolbar);
    }

    public static void showToast(Context mCtx, String text) {
        Toast.makeText(mCtx, text, Toast.LENGTH_SHORT).show();
    }

    public static boolean isMyServiceRunning(Context mCtx, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mCtx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static final String TAG="ShraX";

    public static boolean cantBeEmptyET(EditText et) {
        if (et.getText().toString().trim().length()==0){
            et.setError("Can't be empty!");
            et.requestFocus();
            return true;
        }
        return false;
    }

    public static void showRestartServiceToast(Context mCtx) {
        showToast(mCtx, "You need to restart the service for changes to take effect");
    }

    public static int getBatteryPercentage(Context context) {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);
        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;
        float batteryPct = level / (float) scale;
        return (int) (batteryPct * 100);
    }

    public static void checkAutostartAndStart(Context mCtx) {
        if (spm.getAUTOSTART()) {
            if (Utils.isMyServiceRunning(mCtx, BgService.class)) {
            } else {
                Intent bgServiceIntent = new Intent(mCtx, BgService.class);
                mCtx.startService(bgServiceIntent);
            }
        }
    }
}
