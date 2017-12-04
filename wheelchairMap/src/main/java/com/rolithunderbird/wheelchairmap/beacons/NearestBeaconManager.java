package com.rolithunderbird.wheelchairmap.beacons;

import android.content.Context;
import android.util.Log;

import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.List;

public class NearestBeaconManager {

    private static final String TAG = "NearestBeaconManager";

    private List<String> deviceIDs;

    private Listener listener;

    private String currentlyNearestDeviceID;
    private boolean firstEventSent = false;

    private BeaconManager beaconManager;

    public NearestBeaconManager(Context context, List<String> deviceIDs) {
        this.deviceIDs = deviceIDs;

        beaconManager = new BeaconManager(context);
        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> locations) {
                checkForNearestBeacon(locations);
            }
        });

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onNearestBeaconChanged(String deviceID);
    }

    public void startNearestBeaconUpdates() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }
        });
    }

    public void stopNearestBeaconUpdates() {
        beaconManager.stopLocationDiscovery();
    }

    public void destroy() {
        beaconManager.disconnect();
    }

    private void checkForNearestBeacon(List<EstimoteLocation> allEstimoteLocations) {
        List<EstimoteLocation> beaconsOfInterest = filterOutBeaconsByIDs(allEstimoteLocations, deviceIDs);
        EstimoteLocation nearestLocation = findNearestBeacon(beaconsOfInterest);
        if (nearestLocation != null) {
            String nearestBeaconID = nearestLocation.id.toHexString();
            if (!nearestBeaconID.equals(currentlyNearestDeviceID) || !firstEventSent) {
                updateNearestBeacon(nearestBeaconID);
            }
        } else if (currentlyNearestDeviceID != null || !firstEventSent) {
            updateNearestBeacon(null);
        }
    }

    private void updateNearestBeacon(String beaconID) {
        currentlyNearestDeviceID = beaconID;

        //Codigo MIO
        if (beaconID != null) {
            switch (beaconID) {
                case "356cd6fbaed86ed0":
                    beaconID = "356cd6fbaed86ed0a600b75fa4e34b06";
                    break;
                case "9355323ee0b9b9e3":
                    beaconID = "9355323ee0b9b9e38bf2c7e251d36e31";
                    break;
                case "26e092af158a3156":
                    beaconID = "26e092af158a3156567a19318bc6f935";
                    break;
            }
        }
        firstEventSent = true;
        if (listener != null) {
            listener.onNearestBeaconChanged(beaconID);
        }
    }

    private static List<EstimoteLocation> filterOutBeaconsByIDs(List<EstimoteLocation> estimoteLocations, List<String> deviceIDs) {
        List<EstimoteLocation> filteredBeacons = new ArrayList<>();
        for (EstimoteLocation estimoteLocation : estimoteLocations) {
            String beaconID = estimoteLocation.id.toHexString();
            if (deviceIDs.contains(beaconID)) {
                filteredBeacons.add(estimoteLocation);
            }
        }
        return filteredBeacons;
    }

    private static EstimoteLocation findNearestBeacon(List<EstimoteLocation> estimoteLocations) {
        EstimoteLocation nearestLocation = null;
        double nearestBeaconsDistance = -1;
        for (EstimoteLocation estimoteLocation : estimoteLocations) {
            //double distance = computeAccuracy(estimoteLocation);
            double distance = Math.abs(estimoteLocation.rssi);
            if (distance > -1 &&
                    (distance < nearestBeaconsDistance || nearestLocation == null)) {
                nearestLocation = estimoteLocation;
                nearestBeaconsDistance = distance;
            }
        }

        Log.d(TAG, "Nearest beacon: " + nearestLocation + ", distance: " + nearestBeaconsDistance);
        return nearestLocation;
    }
}
