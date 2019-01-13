/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<EarthQuake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    public static final int LOADER_ID = 22;
    public static final String QUERY_KEY = "QUERY KEY";

    /*Url to query as {urlTOQuery}
     Provides results from the past 30 days with minimum mag of 4.0
     */
    public static final String urlToQuery = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=4.0";
    ListView earthquakeListView;
    ProgressBar progress;
    EarthQUakeAdapter earthquakeAdapter;
    TextView noNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        ArrayList<EarthQuake> earthquakes = new ArrayList<>();

        progress = (ProgressBar) findViewById(R.id.progress);
        noNet = (TextView) findViewById(R.id.noNet);
        earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeAdapter = new EarthQUakeAdapter(this, new ArrayList<EarthQuake>());
        earthquakeListView.setAdapter(earthquakeAdapter);
        // Find a reference to the {@link ListView} in the layout

        Bundle bundle = new Bundle();
        bundle.putString(QUERY_KEY, urlToQuery);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = cm.getActiveNetworkInfo();
        boolean isConnected = activeInfo != null && activeInfo.isConnectedOrConnecting();
        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            Loader<EarthQuake> loader = loaderManager.getLoader(LOADER_ID);
            if (loader == null) {
                loaderManager.initLoader(LOADER_ID, bundle, this);
            } else {
                loaderManager.restartLoader(LOADER_ID, bundle, this);
            }
        } else {
            noNet.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<ArrayList<EarthQuake>> onCreateLoader(int i, final Bundle bundle) {
        Log.i(LOG_TAG, "onStartLoading: ");
        progress.setVisibility(View.VISIBLE);
        return new EarthquakeLoader(this, urlToQuery);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<EarthQuake>> loader, ArrayList<EarthQuake> earthQuakes) {
        Log.i(LOG_TAG, "onLoadFinished: ");
        progress.setVisibility(View.GONE);
        if (earthQuakes == null) {
            noNet.setText("no earthQuakes found");
            noNet.setVisibility(View.VISIBLE);
        } else {
            earthquakeAdapter.addAll(earthQuakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<EarthQuake>> loader) {
        Log.i(LOG_TAG, "onLoaderReset: ");
    }
}

