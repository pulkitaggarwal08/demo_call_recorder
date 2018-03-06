package com.demo_call_recording.pulkit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.demo_call_recording.pulkit.services.InitialServices;
import com.demo_call_recording.pulkit.util.AppConstant;
import com.demo_call_recording.pulkit.util.CommonUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        checkPermissions();
    }

    private void findIds() {


    }

    private void checkPermissions() {

        if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                Manifest.permission.RECORD_AUDIO,
                AppConstant.MY_PERMISSIONS_REQUEST_RECORD_AUDIO)) {

            if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    AppConstant.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)) {

                if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                        Manifest.permission.READ_PHONE_STATE,
                        AppConstant.MY_PERMISSIONS_REQUEST_READ_PHONE_STATE)) {

                    if (CommonUtil.checkAndRequestPermission(MainActivity.this,
                            Manifest.permission.PROCESS_OUTGOING_CALLS,
                            AppConstant.MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS)) {

                        init();
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case AppConstant.MY_PERMISSIONS_REQUEST_RECORD_AUDIO:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    checkPermissions();
                }
                break;

            case AppConstant.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    checkPermissions();
                }
                break;
            case AppConstant.MY_PERMISSIONS_REQUEST_READ_PHONE_STATE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    checkPermissions();
                }
                break;
            case AppConstant.MY_PERMISSIONS_REQUEST_PROCESS_OUTGOING_CALLS:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    checkPermissions();
                }
                break;
        }
    }

    private void init() {

        /*Detect Incoming Calls*/
        detectIncomingCalls();

        /*Start Service for Recording*/
        Intent service = new Intent(getApplicationContext(), InitialServices.class);
        startService(service);

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(70);
    }

    private void detectIncomingCalls() {

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener phoneStateListener = new PhoneStateListener() {

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
//                super.onCallStateChanged(state, incomingNumber);

                String number = incomingNumber;
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    Toast.makeText(MainActivity.this, "RINGING", Toast.LENGTH_SHORT).show();

                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    Toast.makeText(MainActivity.this, "IDLE", Toast.LENGTH_SHORT).show();

                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    Toast.makeText(MainActivity.this, "OFFHOOK", Toast.LENGTH_SHORT).show();

                }
            }
        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }


}
