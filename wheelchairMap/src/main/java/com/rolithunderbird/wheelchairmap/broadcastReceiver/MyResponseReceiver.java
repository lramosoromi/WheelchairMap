package com.rolithunderbird.wheelchairmap.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rolithunderbird.wheelchairmap.activities.MapsActivity;
import com.rolithunderbird.wheelchairmap.utils.Constants;

/**
 * Created by rolithunderbird on 03.07.16.
 */
// Broadcast receiver for receiving status updates from the IntentService
public class MyResponseReceiver extends BroadcastReceiver {

    private String[] filesPath = Constants.filesPath;


    // Called when the BroadcastReceiver gets an Intent it's registered to receive
    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         * You get notified here when your IntentService is done
         * obtaining data form the server!
         */
        Bundle bundle = intent.getExtras();
        String status = bundle.getString("Status");
        if (status!= null && status.equals("Success")) {
            Intent newIntent = new Intent(context.getApplicationContext(), MapsActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.getApplicationContext().startActivity(newIntent);
        }
    }
}