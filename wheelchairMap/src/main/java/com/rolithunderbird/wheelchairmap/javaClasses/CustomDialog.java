package com.rolithunderbird.wheelchairmap.javaClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import java.util.List;

/**
 * Created by rolithunderbird on 11.06.16.
 */
public class CustomDialog extends Dialog
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Activity activity;
    private Constants.DIALOG_TYPE dialogType;
    private String blueprint;
    private String buildingTitle;
    private String buildingInfo;
    private ArrayList<ImageView> iconsList;
    private ArrayList<File> iconFiles;
    private ArrayList<File> buildingImageFiles;
    //Atributes to see if the icon should be visible or not
    private boolean iconPlaneVisibility;
    private boolean iconInclinedVisibility;
    private boolean iconElevatorVisibility;
    private boolean iconAutomaticDoorVisibility;
    private boolean iconWCVisibility;
    private boolean iconAssistanceVisibility;
    private boolean iconExitVisibility;


    public CustomDialog(Activity a, Constants.DIALOG_TYPE dialog_type) {
        super(a);
        this.activity = a;
        this.dialogType = dialog_type;
        this.iconsList = new ArrayList<>();
        this.iconFiles = new ArrayList<>();
        this.buildingImageFiles = new ArrayList<>();
        List<File> files = Constants.getImageFiles();
        for (File file : files) {
            if (file.getName().contains(Constants.FILE_ICON_STRING))
                iconFiles.add(file);
            if (file.getName().contains(Constants.FILE_BUILDING_IMAGE_STRING))
                buildingImageFiles.add(file);
        }
    }

    public void setBlueprint(String string) {
        blueprint = string;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (dialogType == Constants.DIALOG_TYPE.APP_INFO) {
            setContentView(R.layout.dialog_app_info_layout);

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
            setContentView(R.layout.dialog_map_buildin_selection_layout);
            Button button = (Button) findViewById(R.id.dialog_building_selection_button);
            button.setOnClickListener(this);

            Spinner spinner = (Spinner) findViewById(R.id.dialog_building_selection_spinner_buildings);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter adapter = ArrayAdapter.createFromResource(activity.getBaseContext(),
                    R.array.dialog_buildings_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }
        else if (dialogType == Constants.DIALOG_TYPE.BUILDING_INFO){
            //First set content view and all the src for the views
            setContentView(R.layout.dialog_map_building_info_layout);
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

            TextView textViewTitle = (TextView) findViewById(R.id.dialog_building_info_title);
            textViewTitle.setText(buildingTitle);

            TextView textViewMessage = (TextView) findViewById(R.id.dialog_building_info_message);
            textViewMessage.setText(buildingInfo);

            ImageView buildingImage = (ImageView) findViewById(R.id.dialog_building_info_image);
            buildingImage.setImageBitmap(
                    BitmapFactory.decodeFile(buildingImageFiles.get(0).getPath()));

            Button button = (Button) findViewById(R.id.dialog_building_info_btn_select);
            button.setOnClickListener(this);

            //Check on the Visibility on the icons and remove them from the layout if necessary
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
            setContentView(R.layout.dialog_map_contact_layout);
        }
        else {
            setContentView(R.layout.dialog_blueprint_info_layout);
            TextView title = (TextView) findViewById(R.id.dialog_blueprint_title);
            title.setText(blueprint);
        }
    }

    @Override
    public void onClick(View v) {
        if (blueprint != null && blueprint.equals(Constants.BUILDING_BLUEPRINT[0])) {
            Toast.makeText(activity.getBaseContext(), "Please select a building",
                    Toast.LENGTH_SHORT).show();
        }
        else if (blueprint != null && blueprint.equals(Constants.BUILDING_BLUEPRINT[1])) {
            //Call Activity of the building blueprint
            Intent intent = new Intent(activity.getBaseContext(), BlueprintActivity.class);
            intent.putExtra("Building", blueprint);
            activity.startActivity(intent);
            //Close the dialog
            dismiss();
        }
        else {
            Toast.makeText(activity.getBaseContext(), "This building blueprint is currently not available",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String selected = parent.getItemAtPosition(pos).toString();
        if (selected.equals(Constants.BUILDING_BLUEPRINT[1])) {
            blueprint = Constants.BUILDING_BLUEPRINT[1];
        }
        else {
            blueprint = Constants.BUILDING_BLUEPRINT[0];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }

    public void setBuildingTitle(String title) {
        this.buildingTitle =
                activity.getResources().getString(R.string.dialog_building_info_title) + " " + title;
    }

    public void setBuildingInfo(String info) {
        this.buildingInfo = info;
    }

    public void setIconsVisibility(int building) {
        iconPlaneVisibility = false;
        iconInclinedVisibility = false;
        iconElevatorVisibility = false;
        iconAutomaticDoorVisibility = false;
        iconAssistanceVisibility = false;
        iconWCVisibility = false;
        iconExitVisibility = false;

        switch (building) {
            case 1 :
                iterateIconArray(Constants.BUILDING_ONE_ICONS);
                blueprint = null;
                break;
            case 2 :
                iterateIconArray(Constants.BUILDING_TWO_ICONS);
                blueprint = null;
                break;
            case 3 :
                iterateIconArray(Constants.BUILDING_THREE_ICONS);
                blueprint = null;
                break;
            case 4 :
                iterateIconArray(Constants.BUILDING_FOUR_ICONS);
                blueprint = null;
                break;
            case 5 :
                iterateIconArray(Constants.BUILDING_FIVE_ICONS);
                blueprint = null;
                break;
            case 6 :
                iterateIconArray(Constants.BUILDING_SIX_ICONS);
                blueprint = null;
                break;
            case 7 :
                iterateIconArray(Constants.BUILDING_SEVEN_ICONS);
                blueprint = null;
                break;
            case 8 :
                iterateIconArray(Constants.BUILDING_EIGHT_ICONS);
                blueprint = null;
                break;
            case 9 :
                iterateIconArray(Constants.BUILDING_NINE_ICONS);
                blueprint = Constants.BUILDING_BLUEPRINT[1];
                break;
            case 10 :
                iterateIconArray(Constants.BUILDING_TEN_ICONS);
                blueprint = null;
                break;
            case 11 :
                iterateIconArray(Constants.BUILDING_ELEVEN_ICONS);
                blueprint = null;
                break;
            case 12 :
                iterateIconArray(Constants.BUILDING_TWELVE_ICONS);
                blueprint = null;
                break;
            case 13 :
                iterateIconArray(Constants.BUILDING_THIRTEEN_ICONS);
                blueprint = null;
                break;
            case 14 :
                iterateIconArray(Constants.BUILDING_FOURTEEN_ICONS);
                blueprint = null;
                break;
            case 15 :
                iterateIconArray(Constants.BUILDING_FIFTEEN_ICONS);
                blueprint = null;
                break;
            case 16 :
                iterateIconArray(Constants.BUILDING_SIXTEEN_ICONS);
                blueprint = null;
                break;
            case 17 :
                iterateIconArray(Constants.BUILDING_SEVENTEEN_ICONS);
                blueprint = null;
                break;
            case 20 :
                iterateIconArray(Constants.BUILDING_TWENTY_ICONS);
                blueprint = null;
                break;
            default:
                break;
        }
    }

    private void reorderIcons(ArrayList<ImageView> iconsList) {
        int column = 0;
        int row = 0;
        for (ImageView icon : iconsList) {
            if (icon != null) {
                GridLayout grid = (GridLayout) findViewById(R.id.dialog_building_info_grid_layout);
                GridLayout.LayoutParams layout = new GridLayout.LayoutParams();
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