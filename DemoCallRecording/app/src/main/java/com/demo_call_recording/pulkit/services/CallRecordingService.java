package com.demo_call_recording.pulkit.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.demo_call_recording.pulkit.sharedPreferences.SharedPrefernceValue;
import com.demo_call_recording.pulkit.util.AppConstant;

import java.io.File;

import java.io.IOException;
import java.util.Date;

/**
 * Created by pulkit on 4/1/18.
 */

public class CallRecordingService extends Service {

    private MediaRecorder mediaRecorder;

    private static String SAVEPATH;
    boolean isRecording = false;

    private SharedPreferences sharedPreferences;
    private String callNumber, callType;

    Handler handler;

    private AudioManager audioManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isRecording) {

            getValues();
            startRecording();

        }
        return START_STICKY;
    }

    private void getValues() {

//        callNumber = sharedPreferences.getString(SharedPrefernceValue.CALL_NUMBER, "");
//        callType = sharedPreferences.getString(SharedPrefernceValue.CALL_TYPE, "");

        String fileLocation = Environment.getExternalStorageDirectory() + File.separator + AppConstant.APP_DIR;
        File file = new File(fileLocation);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !file.isDirectory()) {
            file.mkdir();
        }
        String saveFileName = getFileSaveName();

        SAVEPATH = fileLocation + File.separator + saveFileName + "";
    }

    private String getFileSaveName() {

        Date date = new Date();
        CharSequence charSequence = DateFormat.format("yyMMdd_HHmmss", date.getTime());
        String dateFormat = charSequence.toString();
        return dateFormat;
    }


    private void startRecording() {

        //Initialize MediaRecorder class and initialize it with preferred configuration
        mediaRecorder = new MediaRecorder();

        handler = new Handler();

        initRecorder();

    }

    private void initRecorder() {

        /*set recording format*/
        try {

            audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

            //turn on speaker
//            audioManager.setSpeakerphoneOn(true);

            //increase Volume
//            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
//                    audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL) - 1, 0);

//            audioManager.unloadSoundEffects();

//            int audioSource = MediaRecorder.AudioSource.VOICE_CALL;
//            mediaRecorder.setAudioSource(audioSource);

            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mediaRecorder.setAudioSamplingRate(8000);
//            mediaRecorder.setAudioEncodingBitRate(12200);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(SAVEPATH);
            mediaRecorder.prepare();
            mediaRecorder.start();

            isRecording = true;

            Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            mediaRecorder = null;
        }

    }

    public void stopRecording(){

        isRecording = false;
//        mediaRecorder.stop();
//        mediaRecorder.reset();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        isRecording = false;
        mediaRecorder.stop();
        mediaRecorder.release();
    }

}








