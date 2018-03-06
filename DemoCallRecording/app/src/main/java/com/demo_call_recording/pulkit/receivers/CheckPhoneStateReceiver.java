package com.demo_call_recording.pulkit.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.demo_call_recording.pulkit.services.CallRecordingService;
import com.demo_call_recording.pulkit.sharedPreferences.SharedPrefernceValue;

/**
 * Created by pulkit on 4/1/18.
 */

public class CheckPhoneStateReceiver extends BroadcastReceiver {

    private final String TAG = CheckPhoneStateReceiver.class.getName();
    private Intent callRecordingService;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String callNumber, callType;

    private CallRecordingService stopRecording;

    CallLog phoneCall;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "onReceive", Toast.LENGTH_SHORT).show();

        /*make object to start the call recording in background*/
        callRecordingService = new Intent(context, CallRecordingService.class);

        stopRecording = new CallRecordingService();

        sharedPreferences = context.getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        /*check phone state*/
        if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            Log.i(TAG, "onReceive: EXTRA_STATE_OFFHOOK");

            callNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            callType = "Call Uthali";

            Toast.makeText(context, callType + ": " + callNumber, Toast.LENGTH_SHORT).show();

            editor.putString(SharedPrefernceValue.CALL_NUMBER, callNumber);
            editor.putString(SharedPrefernceValue.CALL_TYPE, callType);
            editor.commit();

            context.startService(callRecordingService);

        } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            Log.i(TAG, "onReceive: EXTRA_STATE_IDLE");

            callNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            callType = "Call cut krdi";

            Toast.makeText(context, callType + ": " + callNumber, Toast.LENGTH_SHORT).show();

            context.stopService(callRecordingService);

            stopRecording.stopRecording();

        } else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            Log.i(TAG, "onReceive: EXTRA_STATE_RINGING");

            callNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            callType = "Ringing";

            Toast.makeText(context, callType + ": " + callNumber, Toast.LENGTH_SHORT).show();

        }

        /*check phone state*/
//        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
//
//            /*NEW_OUTGOING_CALL*/
//            callNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
//            callType = "Outgoing";
//
//            Toast.makeText(context, "OUTGOING_NUMBER" + callNumber, Toast.LENGTH_SHORT).show();
//
//        } else {
//
//            /*NEW_INCOMING_CALL*/
//            callNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//            callType = "Incoming";
//
//            Toast.makeText(context, "INCOMING_NUMBER: " + callNumber, Toast.LENGTH_SHORT).show();
//
//            context.startService(callRecordingService);
//
//        }
//        Toast.makeText(context, state, Toast.LENGTH_SHORT).show();

    }

}
