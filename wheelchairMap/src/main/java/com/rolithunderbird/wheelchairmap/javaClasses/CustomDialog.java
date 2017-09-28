package com.rolithunderbird.wheelchairmap.javaClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.activities.BlueprintActivity;
import com.rolithunderbird.wheelchairmap.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Class that creates a custom dialog
 * Created by rolithunderbird on 11.06.16.
 */
public class CustomDialog extends Dialog
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //Activity which called the class
    private Activity activity;
    //Typeof dialog
    private Constants.DIALOG_TYPE dialogType;
    //The location that was selected in the main page
    private String locationSelected;
    //Blueprint that will be shown (value from picklist)
    private String blueprint;
    //Building title that will be shown
    private String buildingTitle;
    //Building info that will be shown
    private String buildingInfo;
    //List of all the icons that will be shown
    private ArrayList<ImageView> iconsList;
    //List of all the icon files downloaded
    private ArrayList<File> iconFiles;
    //List of all the building image files downloaded
    private ArrayList<File> buildingImageFiles;
    //Array of all the building blueprint files downloaded
    private String[] buildingBlueprints;
    //Atributes to see if the icon should be visible or not
    private boolean iconPlaneVisibility;
    private boolean iconInclinedVisibility;
    private boolean iconElevatorVisibility;
    private boolean iconAutomaticDoorVisibility;
    private boolean iconWCVisibility;
    private boolean iconAssistanceVisibility;
    private boolean iconExitVisibility;


    /**
     * Constructor
     * @param a
     * @param dialog_type
     */
    public CustomDialog(Activity a, Constants.DIALOG_TYPE dialog_type, String locationSelected) {
        super(a);
        this.activity = a;
        this.dialogType = dialog_type;
        if (locationSelected != null) {
            this.locationSelected = locationSelected;
            if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[0]))
                buildingBlueprints = Constants.REUTLINGEN_BUILDING_BLUEPRINT;
            else
                buildingBlueprints = Constants.AUSTRAL_BUILDING_BLUEPRINT;
        }
        this.iconsList = new ArrayList<>();
        this.iconFiles = new ArrayList<>();
        this.buildingImageFiles = new ArrayList<>();

        //Get all the files downloaded and add them to its specific list according to its name
        List<File> files = Constants.getImageFiles();
        for (File file : files) {
            if (file.getName().contains(Constants.FILE_ICON_STRING))
                iconFiles.add(file);
            if (file.getName().contains(Constants.FILE_BUILDING_IMAGE_STRING))
                buildingImageFiles.add(file);
        }
    }

    /**
     * Setter
     * @param string the blueprint to set
     */
    public void setBlueprint(String string) {
        blueprint = string;
    }

    /**
     * Method called when Custom Dialog created
     * @param savedInstanceState state of the custom dialog
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //First get the dialog type and respond accordingly
        if (dialogType == Constants.DIALOG_TYPE.APP_INFO) {
            //If type app info, show its layout and set all image icons
            setContentView(R.layout.dialog_app_info_layout);

            //For each imageview set the image of the icon downloaded accordingly
            ImageView planeIcon = (ImageView) findViewById(R.id.dialog_app_info_icon_plane);
            planeIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(0).getPath()));
            ImageView inclinedIcon = (ImageView) findViewById(R.id.dialog_app_info_icon_inclined);
            inclinedIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(1).getPath()));
            ImageView elevatorIcon = (ImageView) findViewById(R.id.dialog_app_info_icon_elevator);
            elevatorIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(2).getPath()));
            ImageView automaticDoorIcon = (ImageView) findViewById(R.id.dialog_app_info_icon_automatic_door);
            automaticDoorIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(3).getPath()));
            ImageView assistanceIcon = (ImageView) findViewById(R.id.dialog_app_info_icon_needs_assistance);
            assistanceIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(4).getPath()));
            ImageView wcIcon = (ImageView) findViewById(R.id.dialog_app_info_icon_wc);
            wcIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(5).getPath()));
            ImageView exitIcon = (ImageView) findViewById(R.id.dialog_app_info_icon_exit);
            exitIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(6).getPath()));
        }
        else if (dialogType == Constants.DIALOG_TYPE.BUILDING_SELECTION) {
            //If type building selection, show its layout and set the picklist and button
            setContentView(R.layout.dialog_map_building_selection_layout);
            //Set button onclick method
            Button button = (Button) findViewById(R.id.dialog_building_selection_button);
            button.setOnClickListener(this);

            Spinner spinner = (Spinner) findViewById(R.id.dialog_building_selection_spinner_buildings);
            // Create an ArrayAdapter using the array of buildings and a default spinner layout
            Integer textArrayResId;
            if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[0]))
                textArrayResId = R.array.dialog_reutlingen_buildings_array;
            else
                textArrayResId = R.array.dialog_austral_buildings_array;

            ArrayAdapter adapter = ArrayAdapter.createFromResource(activity.getBaseContext(),
                    textArrayResId, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }
        else if (dialogType == Constants.DIALOG_TYPE.BUILDING_INFO){
            //If type building info, show its layout and set the icons, building image and button
            setContentView(R.layout.dialog_map_building_info_layout);

            //For each imageview set the image of the icon downloaded accordingly
            ImageView planeIcon = (ImageView) findViewById(R.id.dialog_building_info_icon_plane);
            planeIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(0).getPath()));
            iconsList.add(planeIcon);
            ImageView inclinedIcon = (ImageView) findViewById(R.id.dialog_building_info_icon_inclined);
            inclinedIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(1).getPath()));
            iconsList.add(inclinedIcon);
            ImageView elevatorIcon = (ImageView) findViewById(R.id.dialog_building_info_icon_elevator);
            elevatorIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(2).getPath()));
            iconsList.add(elevatorIcon);
            ImageView automaticDoorIcon = (ImageView) findViewById(R.id.dialog_building_info_icon_automatic_door);
            automaticDoorIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(3).getPath()));
            iconsList.add(automaticDoorIcon);
            ImageView assistanceIcon = (ImageView) findViewById(R.id.dialog_building_info_icon_needs_assistance);
            assistanceIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(4).getPath()));
            iconsList.add(assistanceIcon);
            ImageView wcIcon = (ImageView) findViewById(R.id.dialog_building_info_icon_wc);
            wcIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(5).getPath()));
            iconsList.add(wcIcon);
            ImageView exitIcon = (ImageView) findViewById(R.id.dialog_building_info_icon_exit);
            exitIcon.setImageBitmap(BitmapFactory.decodeFile(
                    iconFiles.get(6).getPath()));
            iconsList.add(exitIcon);

            //Set the title and message accordingly
            TextView textViewTitle = (TextView) findViewById(R.id.dialog_building_info_title);
            textViewTitle.setText(buildingTitle);

            TextView textViewMessage = (TextView) findViewById(R.id.dialog_building_info_message);
            textViewMessage.setText(buildingInfo);

            //Set the image of the building accordingly
            ImageView buildingImage = (ImageView) findViewById(R.id.dialog_building_info_image);
            buildingImage.setImageBitmap(
                    BitmapFactory.decodeFile(buildingImageFiles.get(0).getPath()));

            //Set the button onclick method
            Button button = (Button) findViewById(R.id.dialog_building_info_btn_select);
            button.setOnClickListener(this);

            //Check on the Visibility on the icons and remove them from the layout and list if necessary
            if (!iconPlaneVisibility) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_plane);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconInclinedVisibility) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_inclined);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconElevatorVisibility) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_elevator);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconAutomaticDoorVisibility) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_automatic_door);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconAssistanceVisibility) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_needs_assistance);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconWCVisibility) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_wc);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            if (!iconExitVisibility) {
                ImageView icon = (ImageView) findViewById(R.id.dialog_building_info_icon_exit);
                icon.setVisibility(View.GONE);
                iconsList.remove(icon);
            }
            //Once the icons are removed, reorder the layout so as to not leave spaces
            reorderIcons(iconsList);
        }
        else if (dialogType == Constants.DIALOG_TYPE.CONTACT_INFO){
            //If type contact info, show its layout
            setContentView(R.layout.dialog_map_contact_layout);
            TextView contactResponsibility = (TextView) findViewById (R.id.dialog_contact_responsibility);
            TextView contactMessage = (TextView) findViewById (R.id.dialog_contact_message);
            TextView contactPlace = (TextView) findViewById (R.id.dialog_contact_place);

            if (locationSelected.equals(Constants.AVAILABLE_LOCATIONS[0])) {
                contactResponsibility.setText(R.string.dialog_contact_reutlingen_responsibility);
                contactMessage.setText(R.string.dialog_contact_reutlingen_message);
                contactPlace.setText(R.string.dialog_contact_reutlingen_place);
            }
            else {
                contactResponsibility.setText(R.string.dialog_contact_austral_responsibility);
                contactMessage.setText(R.string.dialog_contact_austral_message);
                contactPlace.setText(R.string.dialog_contact_austral_place);
            }
        }
        else {
            //If type blueprint info, show its layout and set its title
            setContentView(R.layout.dialog_blueprint_info_layout);
            //Set dialog title accordingly
            TextView title = (TextView) findViewById(R.id.dialog_blueprint_title);
            title.setText(blueprint);
        }
    }

    /**
     * Method called when a button is clicked
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (dialogType == Constants.DIALOG_TYPE.BUILDING_SELECTION) {
            //Check if the blueprint picklist is equal to an available blueprint
            if (blueprint != null && blueprint.equals(buildingBlueprints[0])) {
                //If blueprint is not null, but selected None on picklist
                Toast.makeText(activity.getBaseContext(), R.string.dialog_building_selection_error_message,
                        Toast.LENGTH_SHORT).show();
            }
            else if (blueprint != null && Arrays.asList(buildingBlueprints).contains(blueprint)) {
                //If blueprint is available
                //Call Activity of the building blueprint
                Intent intent = new Intent(activity.getBaseContext(), BlueprintActivity.class);
                intent.putExtra("Building", blueprint);
                intent.putExtra("Location", locationSelected);
                activity.startActivity(intent);
                //Close the dialog
                dismiss();
            }
            else {
                //If blueprint is null, meaning not defined
                Toast.makeText(activity.getBaseContext(), R.string.dialog_building_selection_not_available_message,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Method called when spinner value changes
     * @param parent
     * @param view
     * @param pos
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        blueprint = null;
        if (pos != 0) {
            blueprint = buildingBlueprints[pos];
        }
        if (blueprint == null) {
            //If blueprint was not selected, set it to NULL value
            blueprint = buildingBlueprints[0];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }

    /**
     * Setter of building title
     * @param title
     */
    public void setBuildingTitle(String title) {
        //Set building number
        this.buildingTitle =
                activity.getResources().getString(R.string.dialog_building_info_title) + " " + title;
    }

    /**
     * Setter of the building info
     * @param info
     */
    public void setBuildingInfo(String info) {
        this.buildingInfo = info;
    }

    /**
     * Check which icon should be visible and which not depending on the building number
     * @param building number of the building
     */
    public void setIconsVisibility(int building) {
        iconPlaneVisibility = false;
        iconInclinedVisibility = false;
        iconElevatorVisibility = false;
        iconAutomaticDoorVisibility = false;
        iconAssistanceVisibility = false;
        iconWCVisibility = false;
        iconExitVisibility = false;

        //Compare value and iterate with Constant value of each building
        switch (building) {
            case 1 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_ONE_ICONS);
                blueprint = null;
                break;
            case 2 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_TWO_ICONS);
                blueprint = null;
                break;
            case 3 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_THREE_ICONS);
                blueprint = null;
                break;
            case 4 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_FOUR_ICONS);
                blueprint = null;
                break;
            case 5 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_FIVE_ICONS);
                blueprint = null;
                break;
            case 6 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_SIX_ICONS);
                blueprint = null;
                break;
            case 7 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_SEVEN_ICONS);
                blueprint = null;
                break;
            case 8 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_EIGHT_ICONS);
                blueprint = null;
                break;
            case 9 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_NINE_ICONS);
                blueprint = Constants.REUTLINGEN_BUILDING_BLUEPRINT[1];
                break;
            case 10 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_TEN_ICONS);
                blueprint = null;
                break;
            case 11 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_ELEVEN_ICONS);
                blueprint = null;
                break;
            case 12 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_TWELVE_ICONS);
                blueprint = null;
                break;
            case 13 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_THIRTEEN_ICONS);
                blueprint = null;
                break;
            case 14 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_FOURTEEN_ICONS);
                blueprint = null;
                break;
            case 15 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_FIFTEEN_ICONS);
                blueprint = null;
                break;
            case 16 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_SIXTEEN_ICONS);
                blueprint = null;
                break;
            case 17 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_SEVENTEEN_ICONS);
                blueprint = null;
                break;
            case 20 :
                iterateIconArray(Constants.REUTLINGEN_BUILDING_TWENTY_ICONS);
                blueprint = null;
                break;
            default:
                break;
        }
    }

    /**
     * Check which icon should be visible and which not depending on the building name
     * @param buildingName name of the building
     */
    public void setIconsVisibility(String buildingName) {
        iconPlaneVisibility = false;
        iconInclinedVisibility = false;
        iconElevatorVisibility = false;
        iconAutomaticDoorVisibility = false;
        iconAssistanceVisibility = false;
        iconWCVisibility = false;
        iconExitVisibility = false;

        //Compare value and iterate with Constant value of each building
        switch (buildingName) {
            case "Admin" :
                iterateIconArray(Constants.AUSTRAL_BUILDING_ADMIN_ICONS);
                blueprint = Constants.AUSTRAL_BUILDING_BLUEPRINT[1];
                break;
            case "A" :
                iterateIconArray(Constants.AUSTRAL_BUILDING_A_ICONS);
                blueprint = Constants.AUSTRAL_BUILDING_BLUEPRINT[2];
                break;
            case "B" :
                iterateIconArray(Constants.AUSTRAL_BUILDING_B_ICONS);
                blueprint = Constants.AUSTRAL_BUILDING_BLUEPRINT[3];
                break;
            default:
                break;
        }
    }

    /**
     * Method called to reorder the icons once all the necessary are eliminated
     * @param iconsList
     */
    private void reorderIcons(ArrayList<ImageView> iconsList) {
        //Start at column 0, row 0
        int column = 0;
        int row = 0;
        //Iterate thorugh list of icons
        for (ImageView icon : iconsList) {
            if (icon != null) {
                //If icon not null, set its new layout
                GridLayout grid = (GridLayout) findViewById(R.id.dialog_building_info_grid_layout);
                GridLayout.LayoutParams layout = new GridLayout.LayoutParams();
                //Check if column >= 3, then should start on new row
                if (column >= 3) {
                    column = 0;
                    row++;
                    layout.columnSpec = grid.spec(column);
                    layout.rowSpec = grid.spec(row);
                } else {
                    layout.columnSpec = grid.spec(column);
                    layout.rowSpec = grid.spec(row);
                }
                column++;
                layout.width = 50;
                layout.height = 50;
                icon.setLayoutParams(layout);
            }
            else
                break;
        }
    }

    /**
     * Iterate through all the icons of a specific icon array of a building and set its visibility values
     * @param iconArray
     */
    private void iterateIconArray(Constants.DEFAULT_ICONS[] iconArray) {
        for (Constants.DEFAULT_ICONS icon : iconArray) {
            if (icon == Constants.DEFAULT_ICONS.PLANE)
                iconPlaneVisibility = true;
            if (icon == Constants.DEFAULT_ICONS.INCLINED)
                iconInclinedVisibility = true;
            if (icon == Constants.DEFAULT_ICONS.ELEVATOR)
                iconElevatorVisibility = true;
            if (icon == Constants.DEFAULT_ICONS.AUTOMATIC_DOOR)
                iconAutomaticDoorVisibility = true;
            if (icon == Constants.DEFAULT_ICONS.ASSISTANCE)
                iconAssistanceVisibility = true;
            if (icon == Constants.DEFAULT_ICONS.WC)
                iconWCVisibility = true;
            if (icon == Constants.DEFAULT_ICONS.EXIT)
                iconExitVisibility = true;
        }
    }
}