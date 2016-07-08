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
import com.rolithunderbird.wheelchairmap.javaClases.CustomDialog;
import com.rolithunderbird.wheelchairmap.utils.LocationManagerCheck;
import com.rolithunderbird.wheelchairmap.utils.PermissionUtils;
import com.rolithunderbird.wheelchairmap.R;

import java.io.File;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener, OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;

    private GroundOverlay mGroundOverlayReutlingen;

    private Constants.MAP_ACTIVE activeMap;

    private SeekBar mTransparencyBar;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    LocationManagerCheck locationManagerCheck;

    private Constants CONSTANTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        CONSTANTS = new Constants();

        mTransparencyBar = (SeekBar) findViewById(R.id.activity_map_transparency_seekBar);
        assert mTransparencyBar != null;
        mTransparencyBar.setMax(Constants.TRANSPARENCY_MAX);
        mTransparencyBar.setProgress(0);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Testing servlet Backend
        //new ServletPostAsyncTask().execute(new Pair<Context, String>(this, "Lucas"));
    }

    /**
     * Method that creates a menu
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_map_action_show_routes:
                if (activeMap == Constants.MAP_ACTIVE.BASIC_MAP) {
                    mGroundOverlayReutlingen.setImage(
                            BitmapDescriptorFactory.fromPath(CONSTANTS.getImageFiles().get(1).getPath()));
                    activeMap = Constants.MAP_ACTIVE.ROUTE_MAP;
                }
                else if (activeMap == Constants.MAP_ACTIVE.ROUTE_MAP) {
                    mGroundOverlayReutlingen.setImage(
                            BitmapDescriptorFactory.fromPath(CONSTANTS.getImageFiles().get(0).getPath()));
                    activeMap = Constants.MAP_ACTIVE.BASIC_MAP;
                }
                return true;
            case R.id.menu_map_action_select_building:
                CustomDialog customDialog = new CustomDialog(this, Constants.DIALOG_TYPE.BUILDING_SELECTION);
                customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialog.show();
                return true;
            case R.id.menu_map_action_toggle_transparency:
                // Toggle transparency value between 0.0f and 0.5f. Initial default value is 0.0f.
                mGroundOverlayReutlingen.setTransparency(0.5f - mGroundOverlayReutlingen.getTransparency());
                return true;
            case R.id.menu_map_action_contact :
                CustomDialog customDialog1 = new CustomDialog(this, Constants.DIALOG_TYPE.CONTACT_INFO);
                customDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialog1.show();
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

        //Seteo la funcion a realizar cuando toco sobre el mapa
        // FALTARIA FIJARME QUE SI TOCO AFUERA DEL PDF NO DEBERIA DEVOLVER NADA
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selectClosestBuilding(latLng);
            }
        });

        //Esto es para cambiar donde arranca el mapa
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(Constants.REUTLINGEN_CENTER, 16, 0, 0)));

        //Pongo la imagen del mapa sobre google maps
        List<File> files = CONSTANTS.getImageFiles();
        mGroundOverlayReutlingen = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromPath(
                        files.get(0).getPath())).anchor(0, 1)
                .bearing(-60)
                .position(Constants.REUTLINGEN_MAP, 600, 465));
        activeMap = Constants.MAP_ACTIVE.BASIC_MAP;

        mTransparencyBar.setOnSeekBarChangeListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.
        mMap.setContentDescription("Google Map with ground overlay.");

        // AGREGADO POR MI para poder ver mi location en el mapa
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mGroundOverlayReutlingen != null)
            mGroundOverlayReutlingen.setTransparency((float) progress / (float) Constants.TRANSPARENCY_MAX);
    }

    //A PARTIR DE ACA ES LO NECESARIO PARA PODER VER MI LOCATION EN EL MAPA

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        //  Initialize the locationManagerCheck
        locationManagerCheck = new LocationManagerCheck(this);

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

        //Popup que me dice de habilitar el GPS
        locationManagerCheck.updateLocationManagerCheck();

        if(locationManagerCheck.isLocationServiceAvailable()){
            if (locationManagerCheck.getProviderType() != Constants.PROVIDERTYPE.GPS_PROVIDER) {
                locationManagerCheck.createLocationServiceError(MapsActivity.this, false);
            }
        }else{
            locationManagerCheck.createLocationServiceError(MapsActivity.this, true);
        }
        return false;
    }

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

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CONSTANTS.deleteFiles();
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
        //ELijo el edificio mas cercano al punto que toque en el mapa.
        //Para eso tengo que crear una Location con cada edificio y una con lo que toque
        //y dsps comparar
        Location clickedLocation = new Location("");
        clickedLocation.setLatitude(clickedLatLng.latitude);
        clickedLocation.setLongitude(clickedLatLng.longitude);

        Location buildingLocation = new Location("");
        LatLng firstBuilding = Constants.BUILDINGS[0];
        buildingLocation.setLatitude(firstBuilding.latitude);
        buildingLocation.setLongitude(firstBuilding.longitude);

        //Calculate the distance between my clickedLocation and the buildingLocation
        float closestDistance = buildingLocation.distanceTo(clickedLocation);

        //Iterate through all the buildings
        int indexOfClosestBuilding = 0;
        for(int j = 1; j < Constants.BUILDINGS.length; j++){
            buildingLocation.setLatitude(Constants.BUILDINGS[j].latitude);
            buildingLocation.setLongitude(Constants.BUILDINGS[j].longitude);

            //Calculate the new distance between clickedLocation and next building
            float newDistance = buildingLocation.distanceTo(clickedLocation);
            if(newDistance < closestDistance){
                indexOfClosestBuilding = j;
                closestDistance = newDistance;
            }
        }
        if (closestDistance > 20)
            indexOfClosestBuilding = -1;
        //Este switch esta para poder decir cual es el edificio dependiendo de el id
        // en la lista
        switch (indexOfClosestBuilding) {
            case 0:
                createBuildingInfoPopup("1", getResources()
                        .getString(R.string.dialog_building_info_description_one));
                break;
            case 1:
                createBuildingInfoPopup("2", getResources()
                        .getString(R.string.dialog_building_info_description_two));
                break;
            case 2:
                createBuildingInfoPopup("3", getResources()
                        .getString(R.string.dialog_building_info_description_three));
                break;
            case 3:
                createBuildingInfoPopup("4", getResources()
                        .getString(R.string.dialog_building_info_description_four));
                break;
            case 4:
                createBuildingInfoPopup("5", getResources()
                        .getString(R.string.dialog_building_info_description_five));
                break;
            case 5:
                createBuildingInfoPopup("6", getResources()
                        .getString(R.string.dialog_building_info_description_six));
                break;
            case 6:
                createBuildingInfoPopup("7", getResources()
                        .getString(R.string.dialog_building_info_description_seven));
                break;
            case 7:
                createBuildingInfoPopup("8", getResources()
                        .getString(R.string.dialog_building_info_description_eight));
                break;
            case 8:
                createBuildingInfoPopup("9", getResources()
                        .getString(R.string.dialog_building_info_description_nine));
                break;
            case 9:
                createBuildingInfoPopup("10", getResources()
                        .getString(R.string.dialog_building_info_description_ten));
                break;
            case 10:
                createBuildingInfoPopup("11", getResources()
                        .getString(R.string.dialog_building_info_description_eleven));
                break;
            case 11:
                createBuildingInfoPopup("12", getResources()
                        .getString(R.string.dialog_building_info_description_twelve));
                break;
            case 12:
                createBuildingInfoPopup("13", getResources()
                        .getString(R.string.dialog_building_info_description_thirteen));
                break;
            case 13:
                createBuildingInfoPopup("14", getResources()
                        .getString(R.string.dialog_building_info_description_fourteen));
                break;
            case 14:
                createBuildingInfoPopup("15", getResources()
                        .getString(R.string.dialog_building_info_description_fifteen));
                break;
            case 15:
                createBuildingInfoPopup("16", getResources()
                        .getString(R.string.dialog_building_info_description_sixteen));
                break;
            case 16:
                createBuildingInfoPopup("17", getResources()
                        .getString(R.string.dialog_building_info_description_seventeen));
                break;
            case 17:
                createBuildingInfoPopup("20", getResources()
                        .getString(R.string.dialog_building_info_description_twenty));
                break;
            default:
                break;
        }
    }

    private void createBuildingInfoPopup(String title, String info) {
        CustomDialog customDialog = new CustomDialog(this, Constants.DIALOG_TYPE.BUILDING_INFO);
        customDialog.setIconsVisibility(Integer.parseInt(title));
        customDialog.setBuildingTitle(title);
        customDialog.setBuildingInfo(info);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }
}