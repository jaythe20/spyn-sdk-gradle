package com.spareio.spyn_sdk;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiTask extends AsyncTask<String, String, String> {
    String urlToCall;
    TaskCompletionListener taskCompletionListener;
    String method = "POST";
    String jsonParam = "";

    public ApiTask(String method1, String uCall, String json, TaskCompletionListener tListener) {
        urlToCall = uCall;
        taskCompletionListener = tListener;
        method = method1;
        jsonParam = json;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String response = "";
        try {
            if (method.equalsIgnoreCase("POST")) {
                response = postRequest(urlToCall, jsonParam);
            } else
                response = getRequest(urlToCall);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        taskCompletionListener.OnTaskComlpeted(s);
    }


    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public String postRequest(String urlStr, String jsonBodyStr) throws IOException {
        Log.d("POST REQUEST", "URL:" + urlStr + " PARAM:" + jsonBodyStr);
        URL url = new URL(urlStr);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(jsonBodyStr.getBytes());
            outputStream.flush();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String resultToDisplay = getStringFromInputStream(new BufferedInputStream(httpURLConnection.getInputStream()));
                Log.e("SUCCESS", "" + resultToDisplay);
                return resultToDisplay;
            } else {
                if (httpURLConnection.getResponseCode() == 201) {
                    String resultToDisplay = getStringFromInputStream(new BufferedInputStream(httpURLConnection.getInputStream()));
                    Log.e("ERROR", "" + resultToDisplay);
                    return resultToDisplay;
                }
                Log.e("Error", "" + httpURLConnection.getResponseMessage() + " " + httpURLConnection.getResponseCode());
                return "" + httpURLConnection.getResponseCode();
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            Log.e("ErrorApi", "" + e.getMessage());
        }
        return "";
    }

    public String getRequest(String urlStr) throws IOException {
//        String resultToDisplay = "";
        InputStream in = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);
            in = new BufferedInputStream(urlConnection.getInputStream());
//            resultToDisplay = getStringFromInputStream(in);
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String resultToDisplay = getStringFromInputStream(new BufferedInputStream(urlConnection.getInputStream()));
                return resultToDisplay;

            } else {
                Log.e("Error", "" + urlConnection.getResponseMessage());
                return "" + urlConnection.getResponseCode();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
}
