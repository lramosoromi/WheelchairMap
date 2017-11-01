package com.rolithunderbird.wheelchairmap.activities;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.EstimoteLocation;
import com.estimote.coresdk.service.BeaconManager;
import com.rolithunderbird.wheelchairmap.ApplicationClass;
import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.broadcastReceiver.MyResponseReceiver;
import com.rolithunderbird.wheelchairmap.database.StorageTask;
import com.rolithunderbird.wheelchairmap.javaClasses.CustomDialog;
import com.rolithunderbird.wheelchairmap.utils.Constants;
import java.util.List;

/**
 * Class that controls the view of the main page of the app
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //String of the selected location in the picklist
    private String location;
    // Strings of the available locations
    private String reutlingenLocation = Constants.AVAILABLE_LOCATIONS[0];
    private String australLocation = Constants.AVAILABLE_LOCATIONS[1];

    //Title of the alert dialog
    private String alertDialogTitle;
    //Message of the alert dialog
    private String alertDialogMessage;
    //Intent string type when alert dialog button for data settings pressed
    private String alertDialogDataSettings;
    //Intent string type when alert dialog button for wifi settings pressed
    private String alertDialogWifiSettings;
    //Alert dialog
    private static AlertDialog alert;
    //Phone is connected or not
    private Boolean isConnected;
    //Path of the files that will be downloaded for the specific location
    private String[] filesToDownload;

    //Beacon section
    private boolean notificationAlreadyShown = false;

    /**
     * Method called when the activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //First of all hide the action bar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        //Handling the scanning results
        /*beaconManager.setLocationListener(new BeaconManager.LocationListener() {
            @Override
            public void onLocationsFound(List<EstimoteLocation> beacons) {
                Log.d("LocationListener", "Nearby beacons: " + beacons);
                
                //Replace with an identifier of your own beacon
                String beaconId = "5ef51ea77db0b16100f3335dcc49dd0d";

                for (EstimoteLocation beacon : beacons) {
                    if (beacon.id.toString().equals(beaconId)
                            && RegionUtils.computeProximity(beacon) == Proximity.NEAR) {
                        showNotification("Hello world", "Looks like you're near a beacon.");
                    }
                }
            }
        });
*/
        //Set the spinner (picklist)
        Spinner spinner = (Spinner) findViewById(R.id.activity_main_spinner_maps);
        // Create an ArrayAdapter using the array of locations and a default spinner layout
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.activity_main_location_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //Add on item selected functionality
        spinner.setOnItemSelectedListener(this);

        // Create the intent filter that will be used to call the broadcast receiver
        IntentFilter statusIntentFilter = new IntentFilter(Constants.BROADCAST_FILTER);
        MyResponseReceiver responseReceiver = new MyResponseReceiver();
        // Registers the MyResponseReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(
                responseReceiver, statusIntentFilter );
    }

    /**
     * Method called whenever the spinner selection changes
     * @param parent
     * @param view
     * @param pos
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // Get the string of the selected location
        String selected = parent.getItemAtPosition(pos).toString();

        // Compare both strings
        if (selected.contains(reutlingenLocation)) {
            location = reutlingenLocation;
        }else if(selected.contains(australLocation)){
            location = australLocation;
        }else {
            location = null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }

    /**
     * Method called when the button selected is clicked
     * @param view
     */
    public void btnSelectMap(View view) {
        //Check if the selected location is not null and if it equals the available location
        if (location != null && location.equals(reutlingenLocation)) {
            //Get the path of the files
            filesToDownload = Constants.FILES_PATH_REUTLINGEN;
            //Check if there are downloaded files and if they are all the files to be downloaded
            if (Constants.getImageFiles() != null
                    && Constants.checkFilesToDownloadAreImageFiles(filesToDownload)) {
                //If the files were already created previously, just go to the map activity
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("Location", location);
                startActivity(intent);
            }else {
                //If the maps where not downloaded
                //Check phone connection
                if (!isConnected(getBaseContext()))
                    //If not connected show alert message
                    showDialog();
                else {
                    //If phone connected
                    downloadContent();
                }
            }
        }
        //Check if the selected location is not null and if it equals the available location
        else if (location != null && location.equals(australLocation)) {
            //Get the path of the files
            filesToDownload = Constants.FILES_PATH_AUSTRAL;
            //Check if there are downloaded files and if they are all the files to be downloaded
            if (Constants.getImageFiles() != null
                    && Constants.checkFilesToDownloadAreImageFiles(filesToDownload)) {
                //If the files were already created previously, just go to the map activity
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("Location", location);
                startActivity(intent);
            }else {
                //If the maps where not downloaded
                //Check phone connection
                if (!isConnected(getBaseContext()))
                    //If not connected show alert message
                    showDialog();
                else {
                    //If phone connected
                    downloadContent();
                }
            }
        } else {
            //If the selected location is null or not equal to an available location
            Toast.makeText(this, R.string.activity_main_selection_error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Helper method to do the notifying
     * @param title
     * @param message
     */
    public void showNotification(String title, String message) {
        if (notificationAlreadyShown) { return; }

        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
        notificationAlreadyShown = true;
    }

    /**
     * Method that checks the phone connectivity
     * @param context
     * @return
     */
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Get the active network the phone is using
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        //Check if the network is connected
        if (activeNetwork != null && activeNetwork.isConnected()) {
            isConnected = true;
            //If connected check which type of network is
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                //If wifi network, do nothing start download
                return true;
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                //If data network, then set alert for improve network connection, showing wifi settings
                alertDialogTitle = getString(R.string.alert_dialog_internet_improvement_title);
                alertDialogMessage = getString(R.string.alert_dialog_internet_improvement_message);
                alertDialogWifiSettings = Settings.ACTION_WIFI_SETTINGS;
                alertDialogDataSettings = null;
            }
        }else {
            isConnected = false;
            //If not connected, then set alert for connect pone, showing both settings
            alertDialogTitle = getString(R.string.alert_dialog_internet_title);
            alertDialogMessage = getString(R.string.alert_dialog_internet_message);
            alertDialogDataSettings = Settings.ACTION_DATA_ROAMING_SETTINGS;
            alertDialogWifiSettings = Settings.ACTION_WIFI_SETTINGS;
        }
        return false;
    }

    /**
     * Method called when needs to show the alert message
     */
    private void showDialog() {
        // show alert dialog if internet is not connected, or using data roaming
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
                        });
        //If phone is connected to internet then show continue button
        if (isConnected) {
            builder.setNegativeButton(
                    this.getBaseContext().getString(R.string.alert_dialog_internet_connected_btn_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            downloadContent();
                            alert.dismiss();
                        }
                    });
        }
        else {
            builder.setNegativeButton(
                    this.getBaseContext().getString(R.string.alert_dialog_internet_not_connected_btn_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            alert.dismiss();
                        }
                    });
        }
        //If data settings is not null, it means it has to be used, then create new button
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

    /**
     *
     */
    private void downloadContent() {
        //Initialize the task that starts downloading the content necessary for this map
        StorageTask task = new StorageTask(this, filesToDownload, location);
        task.execute();
    }

    /**
     * Method called when app is resumed. It goes through a predefined checklist and makes sure
     * everything is accounted for.
     */
    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    /**
     * Method called when app is destroyed. It deletes all downloaded files
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Disconnect from the scanning service
        ((ApplicationClass) this.getApplication()).getBeaconManager().disconnect();
        Constants.deleteFiles();
    }
}
