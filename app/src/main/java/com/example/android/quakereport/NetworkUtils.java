package com.example.android.quakereport;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.Proxy.Type.HTTP;

public class NetworkUtils {

    public static final String TAG =NetworkUtils.class.getSimpleName();
    public static URL urlBuilder(String baseURL){
        return null;
    }

    /**
     *
     * @param url
     * @return JSON response as a string value
     * @throws IOException
     */

    public static String startHTTPconnection(URL url) throws IOException{
        HttpURLConnection urlConnection = null;
        String JSONresponse = "";
        InputStream in = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.connect();
                    //check connecction status
                    if (urlConnection.getResponseCode() == 200) {
                        in = urlConnection.getInputStream();
                        JSONresponse = readInputStream(in);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    Log.e(TAG, "startHTTPconnection: error in making connection" );

                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                    if(in != null){
                        in.close();
                    }
                }
                return JSONresponse;
    }

    /**
     *
     * @param in
     * @return read JSON response as string
     * @throws IOException
     */

    public static String readInputStream(InputStream in) throws IOException{
            StringBuilder output =new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        while(line != null){
            output.append(line);
            line= reader.readLine();
        }
        return output.toString();
    }


}
