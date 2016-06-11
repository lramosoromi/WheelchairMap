package com.rolithunderbird.wheelchairmap;

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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener, OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;

    //Esto estaba de antes. Es una lista con todos los mapas. Lo puedo usar para cuando tenga el
    // mapa con las flechas
    private final List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();

    private GroundOverlay mGroundOverlayReutlingen;

    private Constants.MAP_ACTIVE activeMap;

    private SeekBar mTransparencyBar;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    LocationManagerCheck locationManagerCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mTransparencyBar = (SeekBar) findViewById(R.id.transparencySeekBar);
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
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_routes:
                if (activeMap == Constants.MAP_ACTIVE.BASIC_MAP) {
                    mGroundOverlayReutlingen.setImage(mImages.get(1));
                    activeMap = Constants.MAP_ACTIVE.ROUTE_MAP;
                }
                else if (activeMap == Constants.MAP_ACTIVE.ROUTE_MAP) {
                    mGroundOverlayReutlingen.setImage(mImages.get(0));
                    activeMap = Constants.MAP_ACTIVE.BASIC_MAP;
                }
                return true;
            case R.id.action_select_building:
                CustomDialog customDialog=new CustomDialog(this);
                customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialog.show();
                return true;
            case R.id.action_toggle_transparency:
                // Toggle transparency value between 0.0f and 0.5f. Initial default value is 0.0f.
                mGroundOverlayReutlingen.setTransparency(0.5f - mGroundOverlayReutlingen.getTransparency());
                return true;
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
                //Primero me fijo que el mapa este seteado para que pueda cliquear
                //ESTA AL REVES XQ USO EL NEGADO DE isClickable()
                if (!mGroundOverlayReutlingen.isClickable()) {
                    selectClosestBuilding(latLng);
                }
            }
        });

        //Esto es para cambiar donde arranca el mapa
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition(Constants.REUTLINGEN_CENTER, 16, 0, 0)));

        //Aca agrego los mapas a la lista de mapas
        mImages.clear();
        mImages.add(BitmapDescriptorFactory.fromResource(Constants.BASIC_MAP));
        mImages.add(BitmapDescriptorFactory.fromResource(Constants.ROUTE_MAP));

        //Pongo la imagen del mapa sobre google maps
        mGroundOverlayReutlingen = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(mImages.get(0)).anchor(0, 1)
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
/*
            if (locationManagerCheck.getProviderType() == Constants.PROVIDERTYPE.NETWORK_PROVIDER)
                location = locationManagerCheck.getLocationManager()
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            else if (locationManagerCheck.getProviderType() == Constants.PROVIDERTYPE.GPS_PROVIDER)
                location = locationManagerCheck.getLocationManager()
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
*/
        }else{
            locationManagerCheck.createLocationServiceError(MapsActivity.this, true);
        }


        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
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

        String building = "";
        //Este switch esta para poder decir cual es el edificio dependiendo de el id
        // en la lista
        switch (indexOfClosestBuilding) {
            case 0:
                building = "Building 1";
                break;
            case 1:
                building = "Building 2";
                break;
            case 2:
                building = "Building 3";
                break;
            case 3:
                building = "Building 4";
                break;
            case 4:
                building = "Building 5";
                break;
            case 5:
                building = "Building 6";
                break;
            case 6:
                building = "Building 7";
                break;
            case 7:
                building = "Building 8";
                break;
            case 8:
                building = "Building 9";
                break;
            case 9:
                building = "Building 10";
                break;
            case 10:
                building = "Building 11";
                break;
            case 11:
                building = "Building 12";
                break;
            case 12:
                building = "Building 13";
                break;
            case 13:
                building = "Building 14";
                break;
            case 14:
                building = "Building 15";
                break;
            case 15:
                building = "Building 16";
                break;
            case 16:
                building = "Building 17";
                break;
            case 17:
                building = "Building 20";
                break;
        }
        Toast.makeText(getApplicationContext(), "You just picked: " + building,
                Toast.LENGTH_LONG).show();
    }
}