package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;



public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<EarthQuake>> {

    public static final String  TAG = EarthquakeLoader.class.getSimpleName();
    private String murl;
    public EarthquakeLoader(Context context,String url) {
        super(context);
        murl = url;
    }

    @Override
    public ArrayList<EarthQuake> loadInBackground() {
        String JSONresponse = "";
        ArrayList<EarthQuake> earthQuakes = new ArrayList<>();
        try {
            Log.i(TAG, "loadInBackground: started");
            URL url = new URL(murl);
            JSONresponse = NetworkUtils.startHTTPconnection(url);
            earthQuakes = parseJSONdata(JSONresponse);

        }catch (IOException e){
            Log.e(TAG, "loadInBackground: error " );
        }
            return earthQuakes;


    }
    private static ArrayList<EarthQuake> parseJSONdata(String data){
        ArrayList<EarthQuake> earthquakesJSON = new ArrayList<>();
        EarthQuake currentQuake;
        if (data == "") {
            return null;
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray features = jsonObject.getJSONArray("features");
                JSONObject earthquakes ;
                for (int i = 0; i <= 100; i++) {
                    earthquakes = features.getJSONObject(i).getJSONObject("properties");
//                the magnitude of the quake
                    double mag = earthquakes.getDouble("mag");
//                the string Value of the quake
                    String place = earthquakes.getString("place");
//                time in milliseconds
                    int time = earthquakes.getInt("time");
                    currentQuake = new EarthQuake(mag, time, place);
                    earthquakesJSON.add(currentQuake);
                }

            } catch (JSONException err) {
                Log.e(TAG, "parseJSONdata: failed");
            }
            return earthquakesJSON;
        }

    }

    @Override
    protected void onStartLoading(){
        Log.i(TAG, "onStartLoading: ");
        forceLoad();

    }
}


