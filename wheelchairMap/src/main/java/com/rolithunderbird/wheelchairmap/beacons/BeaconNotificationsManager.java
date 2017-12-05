package com.rolithunderbird.wheelchairmap.beacons;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;
import java.util.List;
import com.estimote.monitoring.EstimoteMonitoring;
import com.estimote.monitoring.EstimoteMonitoringListener;
import com.estimote.monitoring.EstimoteMonitoringPacket;
import com.estimote.monitoring.Fspl;
import com.rolithunderbird.wheelchairmap.ApplicationClass;
import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.activities.BlueprintActivity;
import com.rolithunderbird.wheelchairmap.activities.MainActivity;
import com.rolithunderbird.wheelchairmap.activities.MapsActivity;
import com.rolithunderbird.wheelchairmap.utils.Constants;


public class BeaconNotificationsManager {

    private static final String TAG = "BeaconNotifications";

    private BeaconManager beaconManager;
    private EstimoteMonitoring estimoteMonitoring;

    private String deviceId;

    private Context context;

    private int notificationID = 0;

    private Boolean isEntered = Boolean.FALSE;
    private Boolean isEnteredForFirstTime = Boolean.FALSE;


    public BeaconNotificationsManager(Context context) {
        this.context = context;
        beaconManager = new BeaconManager(context);
        estimoteMonitoring = new EstimoteMonitoring();
        estimoteMonitoring.setEstimoteMonitoringListener(new EstimoteMonitoringListener() {
            @Override
            public void onEnteredRegion() {
                Log.d(TAG, "onEnteredRegion");
                showNotification(true);
            }

            @Override
            public void onExitedRegion() {
                Log.d(TAG, "onExitedRegion");
                showNotification(false);
            }
        });

        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> locations) {
                for (EstimoteLocation location : locations) {
                    if (location.id.toHexString().equals(deviceId)) {
                        startEstimoteMonitoring(new EstimoteMonitoringPacket("26e092af158a3156567a19318bc6f935", location.rssi, location.txPower, location.channel, location.timestamp));
                    }
                }
            }
        });
    }

    public void addNotification(String deviceId) {
        this.deviceId = deviceId;
    }

    public void startMonitoring() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }
        });
    }

    private void showNotification(Boolean enter) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_launcher)
                .setContentTitle("WheelchairMap Notifications")
                .setContentText("Beacon detected")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, builder.build());
    }

    //Modified monitoring method so as to show easily
    private void startEstimoteMonitoring(EstimoteMonitoringPacket estimoteMonitoringPacket) {
        Double distance = Fspl.fspl(estimoteMonitoringPacket.rssi, estimoteMonitoringPacket.txPower);
        if(distance >= 0.0009D && !this.isEntered) {
            this.isEntered = Boolean.TRUE;
            this.isEnteredForFirstTime = Boolean.TRUE;

            showNotification(true);
        } else if(distance <= 0.0008D && this.isEntered) {
            this.isEntered = Boolean.FALSE;
            this.isEnteredForFirstTime = Boolean.TRUE;

            //showNotification(false);
        } else if(0.0008D < distance && distance < 0.0009D && !this.isEnteredForFirstTime) {
            this.isEnteredForFirstTime = Boolean.TRUE;

            showNotification(true);
        }
    }
}
