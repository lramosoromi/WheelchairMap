package com.rolithunderbird.wheelchairmap.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.broadcastReceiver.MyResponseReceiver;
import com.rolithunderbird.wheelchairmap.database.StorageTask;
import com.rolithunderbird.wheelchairmap.utils.Constants;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String location;
    private String alertDialogTitle;
    private String alertDialogMessage;
    private String alertDialogDataSettings;
    private String alertDialogWifiSettings;
    private static AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

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

        // Create the intent filter that will be used to call the broadcast receiver
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
        else {
            location = null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }

    public void btnSelectMap(View view) {
        if (location != null && location.equals(Constants.AVAILABLE_LOCATIONS[0])) {
            String[] filesToDownload = Constants.FILES_PATH;
            if (Constants.getImageFiles() != null
                    && Constants.getImageFiles().size() == filesToDownload.length) {
                //If the files were already created previously, just go to the map activity
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            }
            else {
                if (!isConnected(getBaseContext()))
                    showDialog();
                else {
                    // Initialize the task that starts downloading the content necessary for this map
                    StorageTask task = new StorageTask(this, filesToDownload, location);
                    task.execute();
                }
            }
        }
        else {
            Toast.makeText(this, "Please select a location", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
            else {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    alertDialogTitle = getString(R.string.alert_dialog_internet_improvement_title);
                    alertDialogMessage = getString(R.string.alert_dialog_internet_improvement_message);
                    alertDialogWifiSettings = Settings.ACTION_WIFI_SETTINGS;
                    alertDialogDataSettings = null;
                }
            }
        }
        else {
            alertDialogTitle = getString(R.string.alert_dialog_internet_title);
            alertDialogMessage = getString(R.string.alert_dialog_internet_message);
            alertDialogDataSettings = Settings.ACTION_DATA_ROAMING_SETTINGS;
            alertDialogWifiSettings = Settings.ACTION_WIFI_SETTINGS;
        }
        return false;
    }

    private void showDialog() {
        // show alert dialog if GPS is not connected
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(alertDialogMessage)
                .setTitle(alertDialogTitle)
                .setCancelable(false)
                .setPositiveButton(
                        this.getBaseContext().getString(R.string.alert_dialog_internet_btn_wifi_settings),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(alertDialogWifiSettings);
                                startActivity(intent);
                                alert.dismiss();
                            }
                        })
                .setNegativeButton(
                        this.getBaseContext().getString(R.string.alert_dialog_internet_btn_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alert.dismiss();
                            }
                        });
        if (alertDialogDataSettings != null) {
            builder.setNeutralButton(
                    this.getBaseContext().getString(R.string.alert_dialog_internet_btn_data_settings),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(alertDialogDataSettings);
                            startActivity(intent);
                            alert.dismiss();
                        }
                    });
        }

        alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.deleteFiles();
    }
}
