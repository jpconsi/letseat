/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.considine.letseat;

import com.considine.util.JSONParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This fragment displays a list of distances and travel times from a location
 */
public class DistanceListFragment extends ListFragment {

    // Defines a tag for identifying log entries
    private static final String TAG = "DistanceListFragment";

    /**
     * Fragments require an empty constructor.
     */
    public DistanceListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Distance distance = new Distance(getActivity());
        String googleMapsDistanceUrl = getResources().getString(R.string.google_map_distance_url);
        String requestParameters = "?origins=Vancouver%20BC%7CSeattle&destinations=San%20Francisco%7CVictoria%20BC&mode=driving&language=en-EN&units=imperial&sensor=false";
        String[] params = {
            googleMapsDistanceUrl + requestParameters
        };
        distance.execute(params);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        /*String[] shakespeare = getResources().getStringArray(R.array.titles);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, shakespeare);
        setListAdapter(adapter);*/
        return view; // super.onCreateView(inflater, container,
                     // savedInstanceState);
    }

    public class Distance extends AsyncTask<String, Integer, List<Map<String, Object>>> {
        private static final String SERVICE_NAME = "Distance Service";

        // JSON Node names
        private static final String TAG_ROWS = "rows";

        private static final String TAG_STATUS = "status";

        private static final String TAG_DURATION = "duration";

        private static final String TAG_DISTANCE = "distance";

        private static final String TAG_DURATION_TEXT = "text";

        private static final String TAG_DURATION_VALUE = "value";

        private static final String TAG_DISTANCE_TEXT = "text";

        private static final String TAG_DISTANCE_VALUE = "value";

        private static final String TAG_ORIGIN = "origin_addresses";

        private static final String TAG_DESTINATION = "destination_addresses";

        private final Context context_;

        public Distance(Context context) {
            context_ = context;
        }

        @Override
        protected List<Map<String, Object>> doInBackground(final String... params) {

            // Hashmap for ListView
            final List<Map<String, Object>> distanceList = new ArrayList<Map<String, Object>>();
            Log.d(SERVICE_NAME, "Do in background");
            try {
                // Creating JSON Parser instance
                final JSONParser jParser = new JSONParser();

                // getting JSON string from URL
                String url = params[0];
                // url =
                // "http://maps.googleapis.com/maps/api/distancematrix/json";
                // url =
                // "http://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver%20BC%7CSeattle&destinations=San%20Francisco%7CVictoria%20BC&mode=bicycling&language=fr-FR&sensor=false";

                ObjectMapper mapper = new ObjectMapper(); // can reuse, share
                Map<String, Object> distanceData = mapper.readValue(new URL(url),
                        new TypeReference<Map<String, Object>>() {
                        });

                for (Map.Entry<String, Object> entry : distanceData.entrySet()) {
                    Log.i(SERVICE_NAME, entry.getKey());
                    Log.i(SERVICE_NAME, entry.getValue().toString());

                }
                distanceList.add(distanceData);
                // The activity may have called stop. If so then return
                // immediately
                // because the thread is cancelled.
                if (this.isCancelled()) {
                    return null;
                }

                final JSONObject json = jParser.getJSONObjectFromUrl(url);

                // Getting Origin Address
                final JSONArray origin = json.getJSONArray(TAG_ORIGIN);
                final int originsSize = origin.length();

                // Getting Origin Address
                final JSONArray destination = json.getJSONArray(TAG_DESTINATION);
                final int destinationSize = destination.length();
                // Getting Array of Contacts
                final JSONArray rows = json.getJSONArray(TAG_ROWS);

                // looping through all distances
                for (int i = 0; i < rows.length(); i++) {
                    final JSONObject r = rows.getJSONObject(i);

                    // Storing each json item in variable
                    JSONArray elements = r.getJSONArray("elements");
                    for (int j = 0; j < elements.length(); j++) {
                        JSONObject element = elements.getJSONObject(j);
                        String status = element.getString(TAG_STATUS);
                        JSONObject duration = element.getJSONObject(TAG_DURATION);
                        String durationText = duration.getString(TAG_DURATION_TEXT);
                        String durationValue = duration.getString(TAG_DURATION_VALUE);
                        JSONObject distance = element.getJSONObject(TAG_DISTANCE);
                        String distanceText = distance.getString(TAG_DISTANCE_TEXT);
                        String distanceValue = distance.getString(TAG_DISTANCE_VALUE);

                        // creating new HashMap
                        Map<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_STATUS, origin.getString(j) + destination.getString(j));
                        map.put(TAG_DURATION, durationText);
                        map.put(TAG_DISTANCE, distanceText);

                        // adding HashMap to ArrayList
                        // distanceList.add(map);
                    }
                }
            }/* catch (JSONException e) {
                e.printStackTrace();
             }*/catch (Exception e) {
                Log.e(SERVICE_NAME, "JSON Parsing Error", e);
            }
            return distanceList;
        }

        @Override
        protected void onPostExecute(final List<Map<String, Object>> distanceList) {
            Log.d(SERVICE_NAME, "Post Execute");
            if (distanceList != null && !distanceList.isEmpty()) {
                // resources into Array
                final ListAdapter adapter = new SimpleAdapter(context_, distanceList,
                        R.layout.list_item, new String[] {
                                "destination_address", "origin_address", "rows"
                        }, new int[] {
                                R.id.status, R.id.distance, R.id.duration
                        });
                getListView().setAdapter(adapter);
            }
        }
    }

}
