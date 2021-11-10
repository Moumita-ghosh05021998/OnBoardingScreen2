package com.example.onboardingscreen2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    MediaPlayer mp;                 // MediaPlayer class will required for play music
    public MyService() {

    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");



    }

    @Override
    public void onCreate() {                        //this method is used for any service related  object initialization
        super.onCreate();
        mp = MediaPlayer.create(this,R.raw.emergency_alarm);       // MediaPlayer variable intanciates
    }

    @Override
    public void onStart(Intent intent, int startId) {       //this method used for  start the services
        super.onStart(intent, startId);
        mp.start();
        mp.setLooping(true);   //Repeted the song
    }

    @Override
    public void onDestroy() {                               //this method used for  stop the services
        super.onDestroy();

        mp.stop();
    }
}

