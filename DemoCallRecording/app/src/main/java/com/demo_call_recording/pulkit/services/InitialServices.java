package com.demo_call_recording.pulkit.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.demo_call_recording.pulkit.receivers.CheckPhoneStateReceiver;

/**
 * Created by pulkit on 4/1/18.
 */

public class InitialServices extends Service {

    private final String TAG = InitialServices.class.getName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//
//        Log.i(TAG, "onStart: ");
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(new CheckPhoneStateReceiver(), intentFilter);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
