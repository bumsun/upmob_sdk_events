package com.upmob.upmobevents;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Request {
    public interface RequestCallBack{
        public void success(JSONObject res);
        public void error(String error);
    }
    static public void send(String urlStr, RequestCallBack requestCallBack,List<NameValuePair> params) {
        String deviceInfo = DeviceInfo.getDeviceSuperInfo();
        params.add(new NameValuePair("deviceInfo",deviceInfo));

        Thread thread = new Thread(() -> {
            URL url = null;
            try {
                url = new URL(urlStr);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");
//                conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(params));
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    String response = "";
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response += line;
                    }
                    JSONObject responseJSON = new JSONObject(response);
                    requestCallBack.success(responseJSON);
                }

            } catch (Exception e) {
//                e.printStackTrace();
                requestCallBack.error(e.toString());
            }
        });
        thread.start();
    }

    static private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
