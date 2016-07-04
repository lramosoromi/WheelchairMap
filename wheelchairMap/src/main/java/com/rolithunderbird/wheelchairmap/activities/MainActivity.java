package com.rolithunderbird.wheelchairmap.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.broadcastReceiver.MyResponseReceiver;
import com.rolithunderbird.wheelchairmap.server.StorageService;
import com.rolithunderbird.wheelchairmap.utils.Constants;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter statusIntentFilter = new IntentFilter("com.rolithunderbird.wheelchairmap.BROADCAST");
        MyResponseReceiver responseReceiver = new MyResponseReceiver();
        // Registers the MyResponseReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(
                responseReceiver, statusIntentFilter );

        // First I initialize the service so as to start downloading the images
        Intent storageService = new Intent(this, StorageService.class);
        startService(storageService);
    }
}
