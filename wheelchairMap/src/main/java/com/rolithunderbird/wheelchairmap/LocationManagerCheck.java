package com.rolithunderbird.wheelchairmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * Class used to check and show a popup when using the Location in the map.
 * Created by rolithunderbird on 07.06.16.
 */
public class LocationManagerCheck {

    private LocationManager locationManager;
    private Boolean locationServiceBoolean = false;
    private Constants.PROVIDERTYPE providerType = Constants.PROVIDERTYPE.NULL;
    private static AlertDialog alert;


    public LocationManagerCheck(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        updateLocationManagerCheck();
    }

    public Boolean isLocationServiceAvailable() {
        return locationServiceBoolean;
    }

    public Constants.PROVIDERTYPE getProviderType() {
        return providerType;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public void createLocationServiceError(final Activity activityObj, boolean isError) {

        String alertMessage;
        String alertTitle;
        if (isError) {
            alertMessage = activityObj.getBaseContext().getString(R.string.alertDialogMessage);
            alertTitle = activityObj.getBaseContext().getString(R.string.alertDialogTitle);
        }
        else {
            alertMessage = activityObj.getBaseContext().getString(R.string.alertDialogImprovementMessage);
            alertTitle = activityObj.getBaseContext().getString(R.string.alertDialogImprovementTitle);
        }
        // show alert dialog if Internet is not connected
        AlertDialog.Builder builder = new AlertDialog.Builder(activityObj);

        builder.setMessage(alertMessage)
                .setTitle(alertTitle)
                .setCancelable(false)
                .setPositiveButton(
                        activityObj.getBaseContext().getString(R.string.alertDialogButtonSettings),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                activityObj.startActivity(intent);
                                alert.dismiss();
                            }
                        })
                .setNegativeButton(
                        activityObj.getBaseContext().getString(R.string.alertDialogButtonCancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
        alert = builder.create();
        alert.show();
    }

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
    }
}