package com.upmob.upmobevents;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class API {
    static public String api_url = "https://upmob.ru/api/";
    static public void performSDKTask(Request.RequestCallBack requestCallBack, String order_id, String google_user_id, String task_id) {
        String method = "performSDKTask/";
        //order_id
        //google_user_id
        //task_id

        List<NameValuePair> params = new ArrayList<>();
        params.add(new NameValuePair("order_id",order_id));
        params.add(new NameValuePair("google_user_id",google_user_id));
        params.add(new NameValuePair("task_id",task_id));

        Request.send(api_url+method,requestCallBack,params);
    }

    static public void crashSDKApp(Request.RequestCallBack requestCallBack, String order_id, String google_user_id, String errorStack) {
        String method = "crashSDKApp";
        //order_id
        //google_user_id
        //task_id

        List<NameValuePair> params = new ArrayList<>();
        params.add(new NameValuePair("order_id",order_id));
        params.add(new NameValuePair("google_user_id",google_user_id));
        params.add(new NameValuePair("errorStack",errorStack));

        Request.send(api_url+method,requestCallBack,params);
    }
}
