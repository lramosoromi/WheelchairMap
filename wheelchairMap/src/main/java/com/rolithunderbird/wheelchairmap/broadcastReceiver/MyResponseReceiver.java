package com.rolithunderbird.wheelchairmap.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rolithunderbird.wheelchairmap.activities.MapsActivity;
import com.rolithunderbird.wheelchairmap.utils.Constants;

/**
 * This class is used to receive the broadcast that the downloaded content from the database
 * was successful and call the map activity once the files are ready
 * Created by rolithunderbird on 03.07.16.
 */
public class MyResponseReceiver extends BroadcastReceiver {

    /**
     *  Called when the BroadcastReceiver gets an Intent it's registered to receive
     *
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         * You get notified here when your StorageTask is done
         * obtaining data form the server!
         */
        Bundle bundle = intent.getExtras();
        String status = bundle.getString("Status");
        //Analyze the status of the broadcast
        if (status!= null && status.equals("Success")) {
            //Create the new intent for the map activity
            Intent newIntent = new Intent(context.getApplicationContext(), MapsActivity.class);
            //Add necessary flags (Because I don't know why the intent wasn't called
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //Start the new activity
            context.getApplicationContext().startActivity(newIntent);
        }
    }
}