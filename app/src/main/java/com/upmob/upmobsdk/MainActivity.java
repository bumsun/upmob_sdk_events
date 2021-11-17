package com.upmob.upmobsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
//        Intent intent = new Intent(this, MainActivity2.class);
//        startActivity(intent);
        try {
            UpMob.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        Intent sendIntent = getPackageManager().getLaunchIntentForPackage("ru.dear.diary");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        sendIntent.putExtra("google_user_id", "google_user_id8");
        sendIntent.putExtra("order_id", "order_id1");
        startActivity(sendIntent);
    }
}