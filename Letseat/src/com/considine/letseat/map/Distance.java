
package com.considine.letseat.map;

import com.considine.letseat.R;
import com.considine.util.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Distance extends AsyncTask<String, Integer, List<Map<String, String>>> {
    private static final String SERVICE_NAME = "Distance Service";

    // JSON Node names
    private static final String TAG_ROWS = "rows";

    private static final String TAG_STATUS = "status";

    private static final String TAG_DURATION = "duration";

    private static final String TAG_DISTANCE = "distance";

    private final Context context_;

    public Distance(Context context) {
        context_ = context;
    }

    @Override
    protected List<Map<String, String>> doInBackground(final String... params) {

        // Hashmap for ListView
        final List<Map<String, String>> distanceList = new ArrayList<Map<String, String>>();
        Log.d(SERVICE_NAME, "Do in background");
        try {
            // Creating JSON Parser instance
            final JSONParser jParser = new JSONParser();

            // getting JSON string from URL
            String url = params[0];
            url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC&mode=bicycling&language=fr-FR&sensor=false";
            final JSONObject json = jParser.getJSONObjectFromUrl(url);
            // The activity may have called stop. If so then return immediately
            // because the thread is cancelled.
            if (this.isCancelled()) {
                return null;
            }

            // Getting Array of Contacts
            final JSONArray rows = json.getJSONArray(TAG_ROWS);

            // looping through All Contacts
            for (int i = 0; i < rows.length(); i++) {
                final JSONObject r = rows.getJSONObject(i);

                // Storing each json item in variable
                String status = r.getString(TAG_STATUS);
                String duration = r.getString(TAG_DURATION);
                String distance = r.getString(TAG_DISTANCE);

                // creating new HashMap
                Map<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                map.put(TAG_STATUS, status);
                map.put(TAG_DURATION, duration);
                map.put(TAG_DISTANCE, distance);

                // adding HashMap to ArrayList
                distanceList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return distanceList;
    }

    @Override
    protected void onPostExecute(final List<Map<String, String>> distanceList) {
        Log.d(SERVICE_NAME, "Post Execute");
        if (distanceList != null && !distanceList.isEmpty()) {
            // Updating parsed JSON data into ListView
            // storing string resources into Array

            final ListAdapter adapter = new SimpleAdapter(context_, distanceList,
                    R.layout.list_item, new String[] {
                            TAG_STATUS, TAG_DISTANCE, TAG_DURATION
                    }, new int[] {
                            R.id.status, R.id.distance, R.id.duration
                    });

        }
    }
}
