package com.upmob.upmobsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.upmob.upmobevents.UpMob;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UpMob.init(this);

//        setContentView(null);

//        Handler handler = new Handler(getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                UpMob.sendEvent("333333");
//            }
//        },2000);


    }

    @Override
    protected void onResume() {
        super.onResume();
//        UpMob.init(this);
    }
}