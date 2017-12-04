package com.rolithunderbird.wheelchairmap.beacons;

public interface BeaconContentFactory {

    void getContent(String deviceID, Callback callback);

    interface Callback {
        void onContentReady(Object content);
    }
}
