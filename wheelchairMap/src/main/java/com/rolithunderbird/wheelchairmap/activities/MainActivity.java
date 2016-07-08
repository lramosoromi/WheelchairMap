package com.rolithunderbird.wheelchairmap.activities;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.broadcastReceiver.MyResponseReceiver;
import com.rolithunderbird.wheelchairmap.server.StorageTask;
import com.rolithunderbird.wheelchairmap.utils.Constants;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.activity_main_spinner_maps);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.activity_main_location_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Create the intent filter that will be used to call the broadcast reciever
        IntentFilter statusIntentFilter = new IntentFilter(Constants.BROADCAST_FILTER);
        MyResponseReceiver responseReceiver = new MyResponseReceiver();
        // Registers the MyResponseReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(
                responseReceiver, statusIntentFilter );
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String selected = parent.getItemAtPosition(pos).toString();
        String reutlingenLocation = Constants.AVAILABLE_LOCATIONS[0];
        if (selected.equals(reutlingenLocation)) {
            location = Constants.AVAILABLE_LOCATIONS[0];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }

    public void btnSelectMap(View view) {
        if (location == Constants.AVAILABLE_LOCATIONS[0]) {
            // Initialize the task that starts downloading the content necessary for this map
            StorageTask task = new StorageTask(this);
            task.execute();
        }
        else {
            Toast.makeText(this, "Please select a location", Toast.LENGTH_SHORT).show();
        }
    }
}
