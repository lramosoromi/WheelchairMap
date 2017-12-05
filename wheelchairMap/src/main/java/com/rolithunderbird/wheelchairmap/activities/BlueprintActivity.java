package com.rolithunderbird.wheelchairmap.activities;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.rolithunderbird.wheelchairmap.ApplicationClass;
import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.beacons.ProximityContentManager;
import com.rolithunderbird.wheelchairmap.javaClasses.MyImageView;
import com.rolithunderbird.wheelchairmap.javaClasses.CustomDialog;
import com.rolithunderbird.wheelchairmap.utils.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that controls the view of the blueprint of a building
 */
public class BlueprintActivity extends AppCompatActivity {

    //Tag for logging
    private static final String TAG = "Blueprint Activity";

    //Image of the floor you are looking
    private MyImageView blueprintImage;
    //String that has the name (number) of the building you are in
    private String buildingSelected;
    //The location that was selected in the main page
    private String locationSelected;
    //The floor of the building
    private Integer floor;
    //List of the image files for each floor of the building
    private ArrayList<File> blueprintFiles;
    //Boolean to check if location is shown or not
    private Boolean locationShown = false;
    //Boolean to check if path is shown or not
    private Boolean pathShown = false;

    //Beacon section


    /**
     * Method called when the activity is created
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.blueprintFiles = new ArrayList<>();

        //Get the building that is going to be showed as an extra from the intent
        Bundle bundle = getIntent().getExtras();
        buildingSelected = bundle.getString("Building");
        locationSelected = bundle.getString("Location");
        //Change the title of the action bar with the number of the building
        if (getSupportActionBar() != null) {
            String[] blueprints;
            String[] blueprintsName;
            if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[0])) {
                blueprints = Constants.REUTLINGEN_BUILDING_BLUEPRINT;
                blueprintsName = getResources().getStringArray(R.array.dialog_reutlingen_buildings_array);
            }
            else {
                blueprints = Constants.AUSTRAL_BUILDING_BLUEPRINT;
                blueprintsName = getResources().getStringArray(R.array.dialog_austral_buildings_array);
            }

            Integer index;
            for (index = 0; index < blueprints.length; index++) {
                if (buildingSelected.equals(blueprints[index]))
                    break;
            }

            getSupportActionBar().setTitle(blueprintsName[index]);
        }

        //Convert the building name to the format used as file name
        String buildingSelectedFileName = (buildingSelected.substring(0, 1).toLowerCase() + buildingSelected.substring(1)).replace(" ", "");
        //Get all the image files
        List<File> files = Constants.getImageFiles();
        for (File file : files) {
            //Check that the file name has the name of the building in it
            if (file.getName().contains((buildingSelectedFileName + '_')))
                blueprintFiles.add(file);
        }

        setContentView(R.layout.activity_blueprint);

        //Before showing set the image as the Main Floor
        //Depending on the location selected, which file is the mail floor
        Integer mainFloorIndex;
        if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[0]))
            mainFloorIndex = 1;
        else
            mainFloorIndex = 0;

        blueprintImage = (MyImageView) findViewById(R.id.imageView);
        blueprintImage.setImage(ImageSource.bitmap(BitmapFactory.decodeFile(blueprintFiles.get(mainFloorIndex).getPath())));
    }

    /**
     * Method that creates a menu
     * Inflates the menu with the blueprint menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[0])) {
            menu.add(R.id.menu_blueprint_group_floors, 0, 4, R.string.menu_blueprint_action_underground);
            menu.add(R.id.menu_blueprint_group_floors, 1, 5, R.string.menu_blueprint_action_main_floor);
            menu.add(R.id.menu_blueprint_group_floors, 2, 6, R.string.menu_blueprint_action_first_floor);
            menu.add(R.id.menu_blueprint_group_floors, 3, 7, R.string.menu_blueprint_action_second_floor);
            menu.add(R.id.menu_blueprint_group_floors, 4, 8, R.string.menu_blueprint_action_third_floor);
        }
        else {
            menu.add(R.id.menu_blueprint_group_floors, 0, 4, R.string.menu_blueprint_action_main_floor);
            menu.add(R.id.menu_blueprint_group_floors, 1, 5, R.string.menu_blueprint_action_first_floor);
        }

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_blueprint, menu);
        return true;
    }

    /**
     * Method that checks the menu item pressed and respond accordingly
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_blueprint_action_info) {
            //Create a new custom dialog of the type blueprint info
            CustomDialog appInfoCustomDialog = new CustomDialog(this, Constants.DIALOG_TYPE.BLUEPRINT_INFO, null);
            appInfoCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            appInfoCustomDialog.setBlueprint(buildingSelected);
            appInfoCustomDialog.show();
            return true;
        }
        else if (item.getItemId() == R.id.menu_blueprint_action_show_location) {
            if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[1])) {
                if (buildingSelected.equals(Constants.AUSTRAL_BUILDING_A_BLUEPRINT)) {
                    if (!locationShown) {
                        PointF locationOnMap = getLocationOnTheMap();
                        if (locationOnMap != null) {
                            blueprintImage.setLocationOnMap(locationOnMap);
                            blueprintImage.setPaintLocation(true);
                            //Change image to floor user is in
                            blueprintImage.setImage(ImageSource.bitmap(BitmapFactory.decodeFile(blueprintFiles.get(floor).getPath())));
                            blueprintImage.invalidate();
                            locationShown = true;
                        } else {
                            //Beacons not working
                            Toast.makeText(this, "The beacons are not responding",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        blueprintImage.setPaintLocation(false);
                        blueprintImage.invalidate();
                        locationShown = false;
                    }
                } else {
                    //Not available yet, show message
                    Toast.makeText(this, "This feature is not available for this building",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                //Not available yet, show message
                Toast.makeText(this, "This feature is not available for this location",
                        Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        else if (item.getItemId() == R.id.menu_blueprint_action_show_path) {
            if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[1])) {
                if (buildingSelected.equals(Constants.AUSTRAL_BUILDING_A_BLUEPRINT)) {
                    if (!pathShown) {
                        PointF pathFromMap = getPathFromMap();
                        if (pathFromMap != null) {
                            blueprintImage.setPathFromMap(pathFromMap);
                            blueprintImage.setPaintPath(true);
                            //Change image to floor user is in
                            blueprintImage.setImage(ImageSource.bitmap(BitmapFactory.decodeFile(blueprintFiles.get(floor).getPath())));
                            blueprintImage.invalidate();
                            pathShown = true;
                        } else {
                            //Beacons not working
                            Toast.makeText(this, "The beacons are not responding",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        blueprintImage.setPaintPath(false);
                        blueprintImage.invalidate();
                        pathShown = false;
                    }
                } else {
                    //Not available yet, show message
                    Toast.makeText(this, "This feature is not available for this building",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                //Not available yet, show message
                Toast.makeText(this, "This feature is not available for this location",
                        Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        else {
            blueprintImage.setPaintLocation(false);
            blueprintImage.setPaintPath(false);
            blueprintImage.invalidate();
            locationShown = false;
            pathShown = false;

            //Change image to floor selected
            blueprintImage.setImage(ImageSource.bitmap(BitmapFactory.decodeFile(blueprintFiles.get(item.getItemId()).getPath())));
            return true;
        }
    }

    public PointF getLocationOnTheMap() {
        PointF locationOnMap = ((ApplicationClass)getApplication()).getProximityContentManager().getLocationToShow();
        if (locationOnMap != null) {
            floor = 0;
            for (PointF pointOnMap : Constants.AUSTRAL_POSITIONS_LEVEL_1) {
                if (locationOnMap.equals(pointOnMap)) {
                    floor = 1;
                    break;
                }
            }
        }
        return locationOnMap;
    }

    public PointF getPathFromMap() {
        PointF locationOnMap = ((ApplicationClass)getApplication()).getProximityContentManager().getLocationToShow();
        PointF pathOfMap = null;
        if (locationOnMap != null) {
            floor = 0;
            for (PointF pointOnMap : Constants.AUSTRAL_POSITIONS_LEVEL_1) {
                if (locationOnMap.equals(pointOnMap)) {
                    floor = 1;
                    break;
                }
            }
            if (floor == 0)
                pathOfMap = Constants.AUSTRAL_BUILDING_A_PATH_0;
            else
                pathOfMap = Constants.AUSTRAL_BUILDING_A_PATH_1;
        }
        return pathOfMap;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            ((ApplicationClass)getApplication()).getProximityContentManager().startContentUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        ((ApplicationClass)getApplication()).getProximityContentManager().stopContentUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ApplicationClass)getApplication()).getProximityContentManager().destroy();
    }
}