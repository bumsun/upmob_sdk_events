package com.upmob.upmobsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.upmob.upmobevents.UpMob;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UpMob.init(getApplicationContext(),"12345");

        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(null);
                UpMob.sendEvent("333333");
            }
        },2000);

    }
}