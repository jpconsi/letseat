
package com.considine.letseat;

import com.considine.letseat.map.Distance;
import com.considine.util.GooglePlayServiceClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity implements ConnectionCallbacks, LocationListener {
    private static final String SERVICE_NAME = "Map Service";

    private GoogleMap map_;

    private LocationClient locationClient_;

    private Distance distance_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GooglePlayServiceClient gpsClient = new GooglePlayServiceClient(this, SERVICE_NAME);

        locationClient_ = new LocationClient(this, this, gpsClient);
        setUpMapIfNeeded();

        String[] adobe_products = getResources().getStringArray(R.array.adobe_products);

        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.a_item,
                adobe_products));

    }

    /*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        locationClient_.connect();
        String url = this.getString(R.string.distance_data);
        distance_ = new Distance(this);
        String[] params = {
            url
        };
        distance_.execute(params);
    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        locationClient_.disconnect();
        // If the Async Thread is still running then cancel it.
        distance_.cancel(true);

        super.onStop();
    }

    public void onLocationChanged(Location location) {
        // Report to the UI that the location was updated
        String msg = "Updated Location: " + Double.toString(location.getLatitude()) + ","
                + Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        map_.moveCamera(CameraUpdateFactory.newLatLng(latlng));
    }

    @SuppressLint("NewApi")
    private void setUpMapIfNeeded() {

        // Make sure Map service is installed
        // Do a null check to confirm that we have not already instantiated the
        // map.
        if (map_ == null) {
            map_ = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.

        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    public void onConnected(Bundle arg0) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        if (map_ != null) {
            // map_.setMyLocationEnabled(true);
            Location location = locationClient_.getLastLocation();
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            map_.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        } else {
            Log.e(SERVICE_NAME, "Unable to find Map by id check Android Manifest XML setup");
        }

    }

    public void onDisconnected() {
        Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();

    }

}
