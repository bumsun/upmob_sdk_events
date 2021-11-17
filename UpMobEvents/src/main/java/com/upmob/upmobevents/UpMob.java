package com.upmob.upmobevents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

//Протестить либу таким образом:
//https://stackoverflow.com/a/60342463

public class UpMob {
    static private String referrerUrl;
    static public String google_user_id;

    static public Boolean enableCrashApp = false;
    private static String order_id;

    static public void init(Activity ctx, Boolean enableCrashApp){
        UpMob.enableCrashApp = enableCrashApp;
        UpMob.init(ctx);
    }

    static public void init(Activity act) {
        initGetIntentParams(act);
        initGetReferParams(act);
    }
    private static void initGetIntentParams(Activity act) {
        Intent intent = act.getIntent();

        String action = intent.getAction();
        Set<String> categories = intent.getCategories();
        L.d("categories:"+categories);
        Boolean isLauncher = false;
        if(categories != null){
            for (String category:categories) {
                L.d("category: "+category);
                if(category.toUpperCase().contains("LAUNCHER")){
                    isLauncher = true;
                }
            }
        }


        if(isLauncher == false){
            throw new IllegalArgumentException("Your Activity hasn't category MAIN or LAUNCHER. You need to move UpMob.init() to another Activity.");
        }

        L.d("intent.hasExtra:"+intent.hasExtra("google_user_id"));
        if (intent.hasExtra("google_user_id")) {
            google_user_id = intent.getStringExtra("google_user_id");
            L.d("initGetIntentParams google_user_id:"+google_user_id);
        }
        if (intent.hasExtra("order_id")) {
            order_id = intent.getStringExtra("order_id");
            L.d("initGetIntentParams order_id:"+order_id);
        }
    }

    private static void initGetReferParams(Activity act) {
        Handler handler = new Handler(Looper.getMainLooper());

        InstallReferrerClient referrerClient;

        referrerClient = InstallReferrerClient.newBuilder(act).build();
        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        // Connection established.
                        ReferrerDetails response = null;
                        try {
                            response = referrerClient.getInstallReferrer();
                            referrerUrl = response.getInstallReferrer();
                            long referrerClickTime = response.getReferrerClickTimestampSeconds();
                            long appInstallTime = response.getInstallBeginTimestampSeconds();
                            boolean instantExperienceLaunched = response.getGooglePlayInstantParam();

                            L.d("referrerUrl:"+referrerUrl);
                            String[] paramsList = referrerUrl.split("&");
                            for(String param: paramsList) {
                                String key = param.split("=")[0];
                                String value = param.split("=")[1];
                                if(key.equals("google_user_id")){
                                    google_user_id = value;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            initCatchCrashes();
                                        }
                                    });
                                }
                                if(key.equals("order_id")){
                                    order_id = value;
                                }
                            }



                            L.d("google_user_id:" + google_user_id);
                            L.d("order_id:" + order_id);

                            L.d("referrerClickTime:" + referrerClickTime);
                            L.d("appInstallTime:" + appInstallTime);
                            L.d("instantExperienceLaunched:" + instantExperienceLaunched);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }

    private static void initCatchCrashes() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {

                String errorStack = Log.getStackTraceString(paramThrowable);
                API.crashSDKApp(new Request.RequestCallBack() {
                    @Override
                    public void success(JSONObject res) {

                    }

                    @Override
                    public void error(String error) {

                    }
                },order_id,google_user_id,errorStack);
                if(enableCrashApp){
                    System.exit(1);
                }
            }
        });
    }


    static public void sendEvent(String task_id) {

        if(order_id != null && google_user_id != null && task_id != null){
            API.performSDKTask(new Request.RequestCallBack() {
                @Override
                public void success(JSONObject res) {
                    L.d("res = " + res.toString());
                }

                @Override
                public void error(String error) {
                    L.d("error = " + error);
                }
            },order_id,google_user_id,task_id);
        }
    }
}
