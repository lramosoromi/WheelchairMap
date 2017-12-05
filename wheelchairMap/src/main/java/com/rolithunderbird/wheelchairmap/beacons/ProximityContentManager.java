package com.rolithunderbird.wheelchairmap.beacons;

import android.content.Context;
import android.graphics.PointF;

import com.rolithunderbird.wheelchairmap.utils.Constants;

import java.util.List;

public class ProximityContentManager {

    private NearestBeaconManager nearestBeaconManager;
    //Added variables for App
    private PointF locationToShow;


    public ProximityContentManager(Context context,
                                   List<String> deviceIDs) {

        nearestBeaconManager = new NearestBeaconManager(context, deviceIDs);
        nearestBeaconManager.setListener(new NearestBeaconManager.Listener() {
            @Override
            public void onNearestBeaconChanged(String deviceID) {
                if (deviceID != null) {
                    switch (deviceID) {
                        case "356cd6fbaed86ed0":
                            locationToShow = Constants.AUSTRAL_BUILDING_A_ROOM_1;
                            break;
                        case "9355323ee0b9b9e3":
                            locationToShow = Constants.AUSTRAL_BUILDING_A_ROOM_8;
                            break;
                        case "26e092af158a3156":
                            locationToShow = Constants.AUSTRAL_BUILDING_A_AUDITORIUM;
                            break;
                    }
                } else {
                    locationToShow = null;
                }
            }
        });

    }
    public PointF getLocationToShow() {
        return this.locationToShow;
    }

    public void startContentUpdates() {
        nearestBeaconManager.startNearestBeaconUpdates();
    }

    public void stopContentUpdates() {
        nearestBeaconManager.stopNearestBeaconUpdates();
    }

    public void destroy() {
        nearestBeaconManager.destroy();
    }
}
