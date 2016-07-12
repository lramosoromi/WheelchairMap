package com.rolithunderbird.wheelchairmap.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import com.rolithunderbird.wheelchairmap.R;

/**
 * Class used to check and show a popup when using the Location in the map.
 * Created by rolithunderbird on 07.06.16.
 */
public class LocationManagerCheck {

    //Location service
    private LocationManager locationManager;
    //Boolean if location is available or not
    private Boolean locationServiceBoolean = false;
    //Type of provider (NULL, NETWORK o GPS)
    private Constants.PROVIDERTYPE providerType = Constants.PROVIDERTYPE.NULL;
    //Alert dialog
    private static AlertDialog alert;


    /**
     * Constructor. Needs the context of the app
     * @param context
     */
    public LocationManagerCheck(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        updateLocationManagerCheck();
    }

    /**
     * Getter of locationServiceBoolean
     * @return location available or not
     */
    public Boolean isLocationServiceAvailable() {
        return locationServiceBoolean;
    }

    /**
     * Getter of provider type
     * @return provider type
     */
    public Constants.PROVIDERTYPE getProviderType() {
        return providerType;
    }

    /**
     * Method that creates a alert dialog
     * @param activityObj
     * @param isError
     */
    public void createLocationServiceError(final Activity activityObj, boolean isError) {

        String alertMessage;
        String alertTitle;
        //Check if the alert should show location not available or location can improve
        if (isError) {
            alertMessage = activityObj.getBaseContext().getString(R.string.alert_dialog_location_message);
            alertTitle = activityObj.getBaseContext().getString(R.string.alert_dialog_location_title);
        }
        else {
            alertMessage = activityObj.getBaseContext().getString(R.string.alert_dialog_location_improvement_message);
            alertTitle = activityObj.getBaseContext().getString(R.string.alert_dialog_location_improvement_title);
        }
        // show alert dialog with strings set before
        AlertDialog.Builder builder = new AlertDialog.Builder(activityObj);

        builder.setMessage(alertMessage)
                .setTitle(alertTitle)
                .setCancelable(false)
                .setPositiveButton(
                        activityObj.getBaseContext().getString(R.string.alert_dialog_location_btn_settings),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //If btn setting selected, show location settings page
                                Intent intent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                activityObj.startActivity(intent);
                                alert.dismiss();
                            }
                        })
                .setNegativeButton(
                        activityObj.getBaseContext().getString(R.string.alert_dialog_location_btn_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
        alert = builder.create();
        alert.show();
    }

    /**
     * Method that checks if location enabled
     */
    public void updateLocationManagerCheck() {
        boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (networkIsEnabled && gpsIsEnabled) {
            locationServiceBoolean = true;
            providerType = Constants.PROVIDERTYPE.GPS_PROVIDER;

        } else if (!networkIsEnabled && gpsIsEnabled) {
            locationServiceBoolean = true;
            providerType = Constants.PROVIDERTYPE.GPS_PROVIDER;

        } else if (networkIsEnabled) {
            locationServiceBoolean = true;
            providerType = Constants.PROVIDERTYPE.NETWORK_PROVIDER;
        }
        else {
            locationServiceBoolean = false;
        }
    }
}