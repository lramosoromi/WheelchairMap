package com.rolithunderbird.wheelchairmap;

import android.support.multidex.MultiDexApplication;
import com.estimote.coresdk.common.config.EstimoteSDK;
import com.rolithunderbird.wheelchairmap.beacons.BeaconNotificationsManager;
import com.estimote.coresdk.service.BeaconManager;
import com.rolithunderbird.wheelchairmap.beacons.ProximityContentManager;

import java.util.Arrays;

/**
 * Created by lucasramosoromi on 10/20/17.
 */
public class ApplicationClass extends MultiDexApplication {

    private BeaconManager beaconManager;
    private boolean beaconNotificationsEnabled = false;
    private ProximityContentManager proximityContentManager;


    public BeaconManager getBeaconManager() {
        return beaconManager;
    }

    public ProximityContentManager getProximityContentManager() { return proximityContentManager; }

    public void setBeaconManager(BeaconManager beaconManager) {
        this.beaconManager = beaconManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "wheelchairmap-app-49j", "67079a46432613011acaee69ed820694");

        // Uncomment to enable debug-level logging
        //EstimoteSDK.enableDebugLogging(true);

        beaconManager = new BeaconManager(getApplicationContext());

        //Initialize the beacon manager by connecting to the scanning service
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }
        });

        //Set the proximity content manager to get the nearest beacon of all available ones
        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        "356cd6fbaed86ed0",
                        "9355323ee0b9b9e3",
                        "26e092af158a3156"));

    }

    public void enableBeaconNotifications() {
        if (beaconNotificationsEnabled) { return; }

        BeaconNotificationsManager beaconNotificationsManager = new BeaconNotificationsManager(this);
        beaconNotificationsManager.addNotification(
                //"356cd6fbaed86ed0a600b75fa4e34b06",
                "356cd6fbaed86ed0",
                "Hello, world.",
                "Goodbye, world.");
        beaconNotificationsManager.startMonitoring();

        beaconNotificationsEnabled = true;
    }

    public boolean isBeaconNotificationsEnabled() {
        return beaconNotificationsEnabled;
    }

}