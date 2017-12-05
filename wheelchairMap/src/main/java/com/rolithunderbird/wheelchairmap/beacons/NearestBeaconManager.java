package com.rolithunderbird.wheelchairmap.beacons;

import android.content.Context;
import android.util.Log;

import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.List;

class NearestBeaconManager {

    private static final String TAG = "NearestBeaconManager";

    private List<String> deviceIDs;

    private Listener listener;

    private String currentlyNearestDeviceID;
    private boolean firstEventSent = false;

    private BeaconManager beaconManager;

    NearestBeaconManager(Context context, List<String> deviceIDs) {
        this.deviceIDs = deviceIDs;

        beaconManager = new BeaconManager(context);
        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> locations) {
                checkForNearestBeacon(locations);
            }
        });

    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onNearestBeaconChanged(String deviceID);
    }

    void startNearestBeaconUpdates() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }
        });
    }

    void stopNearestBeaconUpdates() {
        beaconManager.stopLocationDiscovery();
    }

    void destroy() {
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
