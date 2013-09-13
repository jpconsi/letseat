
package com.considine.util;

import com.considine.letseat.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

public class GooglePlayServiceClient implements GooglePlayServicesClient.OnConnectionFailedListener {
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9001;

    private final String serviceDescription_;

    private final Activity context_;

    /**
     * @param activity The context (normally the UI context)
     * @param serviceDescription A name that describes the purpose of connecting
     *            to the google play services e.g. Map Service.
     */
    public GooglePlayServiceClient(Activity activity, String serviceDescription) {
        context_ = activity;
        serviceDescription_ = serviceDescription;
        servicesConnected(activity);

    }

    /**
     * @param activity The context (normally the UI context)
     * @return boolean True if successfully connected
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private final boolean servicesConnected(Activity activity) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
            // Can Google Play service provide an error dialog
            if (errorDialog != null) {

                PackageInfo pInfo;
                try {
                    pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(),
                            0);
                    // only version 11 and above support ErrorDialogFragment
                    if (pInfo.versionCode >= Build.VERSION_CODES.HONEYCOMB) {
                        ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                        // Set the dialog in the DialogFragment
                        errorFragment.setDialog(errorDialog);
                        // Show the error dialog in the DialogFragment
                        errorFragment.show(activity.getFragmentManager(), serviceDescription_);
                    }
                } catch (NameNotFoundException e) {
                    Log.w(serviceDescription_, "Unable to determine version", e);
                }
            } else {
                Log.e(serviceDescription_, "Failed to get Map Service" + resultCode);
            }
            return false;
        } else {
            Log.d(serviceDescription_, "Google Play services is available.");
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(context_,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                Log.e(serviceDescription_,
                        context_.getResources().getString(R.string.cancelled_intent), e);
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the user with
             * the error.
             */
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    connectionResult.getErrorCode(), context_,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
            PackageInfo pInfo;
            try {
                pInfo = context_.getPackageManager().getPackageInfo(context_.getPackageName(), 0);
                // only version 11 and above support ErrorDialogFragment
                if (pInfo.versionCode >= Build.VERSION_CODES.HONEYCOMB) {
                    ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                    // Set the dialog in the DialogFragment
                    errorFragment.setDialog(errorDialog);
                    // Show the error dialog in the DialogFragment
                    errorFragment.show(context_.getFragmentManager(), serviceDescription_);
                }
            } catch (NameNotFoundException e) {
                Log.w(serviceDescription_, "Unable to determine version", e);
            }
        }

    }

}
