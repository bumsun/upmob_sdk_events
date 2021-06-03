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
    Integer counter = 0;
    Integer scores = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UpMob.init(getApplicationContext());

        setContentView(null);

        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                UpMob.sendEvent("333333");
            }
        },2000);

        Button button = findViewById(R.id.buttonHello);
        LinearLayout layoutTest = findViewById(R.id.layoutTest);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("2112","122121");
                Random r = new Random();
                int randomNumber = r.nextInt(10 - 0) + 0;
                Log.d("myLogs","randomNumber ="+randomNumber);

                if(randomNumber == 7 || scores < 20){
                    scores += 20;
                    Log.d("myLogs","поздравляем очков стало больше");
                }else{
                    scores -= 1;
                    Log.d("myLogs","сожалеем, очков стало меньше.");
                }

                if(scores <= 0){
                    Log.d("myLogs","Проигрышь");
                    button.setEnabled(false);
                }





                counter++;
            }
        });


//        String[] fruits = new String[1000];
//        fruits[0] = "Apple";
//        fruits[1] = "Banana";
//        fruits[2] = "Orange";
//        String apple = fruits[0];

        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("orange");

        for(String fruit:fruits){
            if(fruit.equals("apple")){
                Log.d("myLogs","яблоко");
            }

        }


        if(v.getId() == R.id.btnOk){

        }else{

        }

        // i = 0
        // i = 1
        // i = 2
        // i = 3

        Log.d("myLogs",fruits.get(0));
        Log.d("myLogs",fruits.get(1));
        Log.d("myLogs",fruits.get(2));






    }
}