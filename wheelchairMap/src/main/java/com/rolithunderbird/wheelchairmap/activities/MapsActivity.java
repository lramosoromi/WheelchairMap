package com.rolithunderbird.wheelchairmap.activities;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.rolithunderbird.wheelchairmap.utils.Constants;
import com.rolithunderbird.wheelchairmap.javaClasses.CustomDialog;
import com.rolithunderbird.wheelchairmap.utils.LocationManagerCheck;
import com.rolithunderbird.wheelchairmap.utils.PermissionUtils;
import com.rolithunderbird.wheelchairmap.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls the view of the map page
 */
public class MapsActivity extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener, OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    //The location that was selected in the main page
    private String locationSelected;
    //Image of campus map on top of google map
    private GroundOverlay mGroundOverlay;
    //Show which is the active map at the moment (basic map or routes map)
    private Constants.MAP_ACTIVE activeMap;
    //List of only the map image files
    private ArrayList<File> mapFiles;
    //Seekbar used to change transparency of groundoverlaymap
    private SeekBar mTransparencyBar;
    //Flag indicating whether a requested permission has been denied after returning in
    private boolean mPermissionDenied = false;
    //Location checker
    private LocationManagerCheck locationManagerCheck;


    /**
     * Method called when the activity is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Get the location that is going to be showed on the map
        Bundle bundle = getIntent().getExtras();
        locationSelected = bundle.getString("Location");

        super.onCreate(savedInstanceState);
        this.mapFiles = new ArrayList<>();
        setContentView(R.layout.activity_maps);

        //Get all the files downloaded but only add the ones concerning the map to the map files list
        List<File> files = Constants.getImageFiles();
        for (File file : files) {
            if (file.getName().contains(Constants.FILE_MAP_STRING))
                mapFiles.add(file);
        }

        //Initialize seekbar parameters
        mTransparencyBar = (SeekBar) findViewById(R.id.activity_map_transparency_seekBar);
        assert mTransparencyBar != null;
        mTransparencyBar.setMax(Constants.TRANSPARENCY_MAX);
        mTransparencyBar.setProgress(0);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Method that creates a menu
     * Inflates the menu with the blueprint menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return true;
    }

    /**
     * Method that checks the menu item pressed and respond accordingly
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_map_action_info:
                //Create a new custom dialog of the type app info
                CustomDialog appInfoCustomDialog = new CustomDialog(this, Constants.DIALOG_TYPE.APP_INFO, locationSelected);
                appInfoCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                appInfoCustomDialog.show();
                return true;
            case R.id.menu_map_action_show_routes:
                //Change the map file from basic to routes accordingly
                if (activeMap == Constants.MAP_ACTIVE.BASIC_MAP) {
                    mGroundOverlay.setImage(
                            BitmapDescriptorFactory.fromPath(mapFiles.get(1).getPath()));
                    activeMap = Constants.MAP_ACTIVE.ROUTE_MAP;
                }
                else if (activeMap == Constants.MAP_ACTIVE.ROUTE_MAP) {
                    mGroundOverlay.setImage(
                            BitmapDescriptorFactory.fromPath(mapFiles.get(0).getPath()));
                    activeMap = Constants.MAP_ACTIVE.BASIC_MAP;
                }
                return true;
            case R.id.menu_map_action_select_building:
                //Create a new custom dialog of the type building selection
                CustomDialog buildingSelectionCustomDialog = new CustomDialog(
                        this, Constants.DIALOG_TYPE.BUILDING_SELECTION, locationSelected);
                buildingSelectionCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                buildingSelectionCustomDialog.show();
                return true;
            case R.id.menu_map_action_contact :
                //Create a new custom dialog of the type contact info
                CustomDialog contactInfoCustomDialog = new CustomDialog(this, Constants.DIALOG_TYPE.CONTACT_INFO, locationSelected);
                contactInfoCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                contactInfoCustomDialog.show();
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        //LatLng of the location selected by the user
        LatLng cameraPosition = new LatLng(0, 0);
        LatLng mGroundOverlayPosition = new LatLng(0, 0);
        Integer cameraZoom = 0;
        Integer imageBearing = 0;
        Integer imageWidth = 0;
        Integer imageHeight = 0;
        if(locationSelected.equals(Constants.AVAILABLE_LOCATIONS[0])) {
            cameraPosition = Constants.REUTLINGEN_CENTER;
            cameraZoom = Constants.REUTLINGEN_CAMERA_ZOOM;
            imageBearing = Constants.REUTLINGEN_BEARING;
            mGroundOverlayPosition = Constants.REUTLINGEN_MAP;
            imageWidth = Constants.REUTLINGEN_MAP_WIDTH;
            imageHeight = Constants.REUTLINGEN_MAP_HEIGHT;
        }else if(locationSelected.equals(Constants.AVAILABLE_LOCATIONS[1])) {
            cameraPosition = Constants.AUSTRAL_CENTER;
            mGroundOverlayPosition = Constants.AUSTRAL_MAP;
            cameraZoom = Constants.AUSTRAL_CAMERA_ZOOM;
            imageBearing = Constants.AUSTRAL_BEARING;
            imageWidth = Constants.AUSTRAL_MAP_WIDTH;
            imageHeight = Constants.AUSTRAL_MAP_HEIGHT;
        }

        //Set the function called whenever someone touches over the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selectClosestBuilding(latLng);
            }
        });

        //Move the google map starting point to the center of the campus
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(cameraPosition, cameraZoom, 0, 0)));

        //Initialize the ground overlay map with all its parameters
        mGroundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromPath(
                        mapFiles.get(0).getPath())).anchor(0, 1)
                .bearing(imageBearing)
                .position(mGroundOverlayPosition, imageWidth, imageHeight));
        activeMap = Constants.MAP_ACTIVE.BASIC_MAP;

        //Set seekbar listener behaviours
        mTransparencyBar.setOnSeekBarChangeListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        mMap.setContentDescription("Google Map with ground overlay.");

        // Enable my location button click on the map
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    /**
     * Method called while the seekbar is changing its value
     * @param seekBar
     * @param progress
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mGroundOverlay != null)
            //Change the transparency of the ground overlay map
            mGroundOverlay.setTransparency((float) progress / (float) Constants.TRANSPARENCY_MAX);
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        //  Initialize the locationManagerCheck
        locationManagerCheck = new LocationManagerCheck(this);

        //Check permissions necessary to retrieve my location
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, Constants.LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {

        //Check which type of location im using (GPS or wifi) and responds accordingly
        locationManagerCheck.updateLocationManagerCheck();

        //Check if the location is enabled
        if(locationManagerCheck.isLocationServiceAvailable()){
            //Check if location is not GPS
            if (locationManagerCheck.getProviderType() != Constants.PROVIDERTYPE.GPS_PROVIDER) {
                //If location not GPS, then show dialog error
                locationManagerCheck.createLocationServiceError(MapsActivity.this, false);
            }
        }else{
            //If location not enabled, then show dialog error
            locationManagerCheck.createLocationServiceError(MapsActivity.this, true);
        }
        return false;
    }

    /**
     * Method called to check result of permissions.
     * Created by google
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    /**
     * Method called when resuming fragments.
     * Created by google
     */
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    /**
     * Method used to select the closest building in the map to the Location clicked by the user
     */
    private void selectClosestBuilding(LatLng clickedLatLng) {
        //First check the location selected and retrieve a list of the buildings from that location
        LatLng[] buildingsList = new LatLng[0];
        if(locationSelected.equals(Constants.AVAILABLE_LOCATIONS[0]))
            buildingsList = Constants.REUTLINGEN_BUILDINGS;
        else if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[1]))
            buildingsList = Constants.AUSTRAL_BUILDINGS;

        //Then create location of the placed clicked
        Location clickedLocation = new Location("");
        clickedLocation.setLatitude(clickedLatLng.latitude);
        clickedLocation.setLongitude(clickedLatLng.longitude);

        //After create location of the first building
        Location buildingLocation = new Location("");
        LatLng firstBuilding = buildingsList[0];
        buildingLocation.setLatitude(firstBuilding.latitude);
        buildingLocation.setLongitude(firstBuilding.longitude);

        //Calculate the distance between my clickedLocation and the buildingLocation
        float closestDistance = buildingLocation.distanceTo(clickedLocation);

        //Iterate through all the buildings
        int indexOfClosestBuilding = 0;
        for(int j = 1; j < buildingsList.length; j++){
            buildingLocation.setLatitude(buildingsList[j].latitude);
            buildingLocation.setLongitude(buildingsList[j].longitude);

            //Calculate the new distance between clickedLocation and next building
            float newDistance = buildingLocation.distanceTo(clickedLocation);
            if(newDistance < closestDistance){
                //If distance lower than closest distance, then update new value
                indexOfClosestBuilding = j;
                closestDistance = newDistance;
            }
        }
        //If the closest building to my click is less than a constant value
        //This is so as to not show a popup when someone clicks in the middle of nowhere
        if (closestDistance > Constants.MAX_CLOSEST_DISTANCE)
            indexOfClosestBuilding = -1;

        //Call private method to select the closest building depending on the buildings list and the
        // index of the closest one.
        getClosestBuildingFromIndex(indexOfClosestBuilding);

    }

    /**
     * Method that searches on the buildings list and chooses one depending on the index passed
     * as parameter
     * @param index
     */
    private void getClosestBuildingFromIndex(Integer index) {
        //This switch is to decide which is the building closest to the touch using the buildings array
        // and creates a popup of that building
        if(locationSelected.equals(Constants.AVAILABLE_LOCATIONS[0])) {
            switch (index) {
                case 0:
                    createBuildingInfoPopup("1", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_one));
                    break;
                case 1:
                    createBuildingInfoPopup("2", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_two));
                    break;
                case 2:
                    createBuildingInfoPopup("3", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_three));
                    break;
                case 3:
                    createBuildingInfoPopup("4", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_four));
                    break;
                case 4:
                    createBuildingInfoPopup("5", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_five));
                    break;
                case 5:
                    createBuildingInfoPopup("6", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_six));
                    break;
                case 6:
                    createBuildingInfoPopup("7", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_seven));
                    break;
                case 7:
                    createBuildingInfoPopup("8", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_eight));
                    break;
                case 8:
                    createBuildingInfoPopup("9", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_nine));
                    break;
                case 9:
                    createBuildingInfoPopup("10", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_ten));
                    break;
                case 10:
                    createBuildingInfoPopup("11", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_eleven));
                    break;
                case 11:
                    createBuildingInfoPopup("12", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_twelve));
                    break;
                case 12:
                    createBuildingInfoPopup("13", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_thirteen));
                    break;
                case 13:
                    createBuildingInfoPopup("14", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_fourteen));
                    break;
                case 14:
                    createBuildingInfoPopup("15", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_fifteen));
                    break;
                case 15:
                    createBuildingInfoPopup("16", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_sixteen));
                    break;
                case 16:
                    createBuildingInfoPopup("17", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_seventeen));
                    break;
                case 17:
                    createBuildingInfoPopup("20", getResources()
                            .getString(R.string.dialog_reutlingen_building_info_description_twenty));
                    break;
                default:
                    break;
            }
        } else if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[1])) {
            switch (index) {
                case 0:
                    createBuildingInfoPopup("Admin", getResources()
                            .getString(R.string.dialog_austral_building_info_description_admin));
                    break;
                case 1:
                    createBuildingInfoPopup("A", getResources()
                            .getString(R.string.dialog_austral_building_info_description_a));
                    break;
                case 2:
                    createBuildingInfoPopup("B", getResources()
                            .getString(R.string.dialog_austral_building_info_description_b));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method that creates a custom dialog with the information of the building
     * @param title
     * @param info
     */
    private void createBuildingInfoPopup(String title, String info) {
        //Create a new custom dialog of the type building info
        CustomDialog customDialog = new CustomDialog(this, Constants.DIALOG_TYPE.BUILDING_INFO, locationSelected);
        if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[0]))
            customDialog.setIconsVisibility(Integer.parseInt(title));
        else if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[1]))
            customDialog.setIconsVisibility(title);

        customDialog.setBuildingTitle(title);
        customDialog.setBuildingInfo(info);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }
}