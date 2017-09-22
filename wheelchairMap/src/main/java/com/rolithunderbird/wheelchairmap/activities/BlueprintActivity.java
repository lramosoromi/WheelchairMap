package com.rolithunderbird.wheelchairmap.activities;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.javaClasses.CustomDialog;
import com.rolithunderbird.wheelchairmap.javaClasses.TouchImageView;
import com.rolithunderbird.wheelchairmap.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls the view of the blueprint of a building
 */
public class BlueprintActivity extends AppCompatActivity {

    //Image of the floor you are looking
    private TouchImageView image;
    //String that has the name (number) of the building you are in
    private String buildingSelected;
    //List of the image files for each floor of the building
    private ArrayList<File> blueprintFiles;


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
        //Change the title of the action bar with the number of the building
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(buildingSelected);

        //Convert the building name to the format used as file name
        String buildingSelectedFileName = (buildingSelected.substring(0, 1).toLowerCase() + buildingSelected.substring(1)).replace(" ", "");
        //Get all the image files
        List<File> files = Constants.getImageFiles();
        for (File file : files) {
            //Check that the file name has the name of the building in it
            if (file.getName().contains(buildingSelectedFileName))
                blueprintFiles.add(file);
        }

        setContentView(R.layout.activity_blueprint);

        //Before showing set the image as the Main Floor
        image = (TouchImageView) findViewById(R.id.activity_blueprint_image_view);
        image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(1).getPath()));
    }

    /**
     * Method that creates a menu
     * Inflates the menu with the blueprint menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        switch (item.getItemId()) {
            case R.id.menu_blueprint_action_info:
                //Create a new custom dialog of the type blueprint info
                CustomDialog appInfoCustomDialog = new CustomDialog(this, Constants.DIALOG_TYPE.BLUEPRINT_INFO, null);
                appInfoCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                appInfoCustomDialog.setBlueprint(buildingSelected);
                appInfoCustomDialog.show();
                return true;
            case R.id.menu_blueprint_action_show_location:
                //Not available yet, show message
                Toast.makeText(this, "This feature is not available for the moment",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_blueprint_action_underground:
                //Change image to underground
                image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(0).getPath()));
                return true;
            case R.id.menu_blueprint_action_main_floor:
                //Change image to main floor
                image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(1).getPath()));
                return true;
            case R.id.menu_blueprint_action_first_floor:
                //Change image to first floor
                image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(2).getPath()));
                return true;
            case R.id.menu_blueprint_action_second_floor:
                //Change image to second floor
                image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(3).getPath()));
                return true;
            case R.id.menu_blueprint_action_third_floor:
                //Change image to third floor
                image.setImageBitmap(BitmapFactory.decodeFile(blueprintFiles.get(4).getPath()));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}