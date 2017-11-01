package com.rolithunderbird.wheelchairmap;

import android.support.multidex.MultiDexApplication;
import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.service.BeaconManager;

/**
 * Created by lucasramosoromi on 10/20/17.
 */
public class ApplicationClass extends MultiDexApplication {

    private BeaconManager beaconManager;


    public BeaconManager getBeaconManager() {
        return beaconManager;
    }

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
    }
}