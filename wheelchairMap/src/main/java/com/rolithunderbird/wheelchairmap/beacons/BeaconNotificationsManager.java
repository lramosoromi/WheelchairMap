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
import com.rolithunderbird.wheelchairmap.activities.MainActivity;


public class BeaconNotificationsManager {

    private static final String TAG = "BeaconNotifications";

    private BeaconManager beaconManager;
    private EstimoteMonitoring estimoteMonitoring;

    private String enterMessages;
    private String exitMessages;
    private String deviceId;

    private Context context;

    private int notificationID = 0;

    //Codigo Mio.   Copia de la clase de Estimote, con cambios para que funcione
    private Boolean isEntered = Boolean.valueOf(false);
    private Boolean isEnteredForFirstTime = Boolean.valueOf(false);


    public BeaconNotificationsManager(Context context) {
        this.context = context;
        beaconManager = new BeaconManager(context);
        estimoteMonitoring = new EstimoteMonitoring();
        estimoteMonitoring.setEstimoteMonitoringListener(new EstimoteMonitoringListener() {
            @Override
            public void onEnteredRegion() {
                Log.d(TAG, "onEnteredRegion");
                String message = enterMessages;
                if (message != null) {
                    showNotification(message);
                }
            }

            @Override
            public void onExitedRegion() {
                Log.d(TAG, "onExitedRegion");
                String message = exitMessages;
                if (message != null) {
                    showNotification(message);
                }
            }
        });

        beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> locations) {
                for (EstimoteLocation location : locations) {
                    if (location.id.toHexString().equals(deviceId)) {
                        startEstimoteMonitoring(new EstimoteMonitoringPacket("356cd6fbaed86ed0a600b75fa4e34b06", location.rssi, location.txPower, location.channel, location.timestamp));
                    }
                }
            }
        });
    }

    public void addNotification(String deviceId, String enterMessage, String exitMessage) {
        this.deviceId = deviceId;
        enterMessages = enterMessage;
        exitMessages = exitMessage;
    }

    public void startMonitoring() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startLocationDiscovery();
            }
        });
    }

    private void showNotification(String message) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Beacon Notifications")
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, builder.build());
    }

    //Codigo Mio.   Copia de la clase de Estimote con cambios mios para que funcione
    public void startEstimoteMonitoring(EstimoteMonitoringPacket estimoteMonitoringPacket) {
        Double distance = Double.valueOf(Fspl.fspl(Integer.valueOf(estimoteMonitoringPacket.rssi), Integer.valueOf(estimoteMonitoringPacket.txPower)));
        if(distance.doubleValue() >= 0.0009D && !this.isEntered.booleanValue()) {
            this.isEntered = Boolean.valueOf(true);
            this.isEnteredForFirstTime = Boolean.valueOf(true);

            String message = enterMessages;
            if (message != null) {
                showNotification(message);
            }
        } else if(distance.doubleValue() <= 0.0008D && this.isEntered.booleanValue()) {
            this.isEntered = Boolean.valueOf(false);
            this.isEnteredForFirstTime = Boolean.valueOf(true);

            String message = exitMessages;
            if (message != null) {
                showNotification(message);
            }
        } else if(0.0008D < distance.doubleValue() && distance.doubleValue() < 0.0009D && !this.isEnteredForFirstTime.booleanValue()) {
            this.isEnteredForFirstTime = Boolean.valueOf(true);

            String message = enterMessages;
            if (message != null) {
                showNotification(message);
            }
        }
    }
}
