package com.rolithunderbird.wheelchairmap.utils;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.List;

/**
 * This class is used to store all the necessary constants that will be used in all the app.
 * With this the only place to change code once some values changes should be this class.
 * Created by rolithunderbird on 07.06.16.
 */
public class Constants {

    //The image files created when downloading content from the database
    private static List<File> imageFiles;
    //The broadcast filter used to by the broadcast receiver
    public static final String BROADCAST_FILTER = "com.rolithunderbird.wheelchairmap.BROADCAST";
    //Transparency maximum value of the GroundOverlayMap
    public static final int TRANSPARENCY_MAX = 100;
    //LatLng position where the google map activity starts
    public static final LatLng REUTLINGEN_CENTER = new LatLng(48.481905, 9.188492);

    //Here is stored all the buildings LatLng coordinates that will be used in the app
    //<editor-fold desc="Building Locations">
    protected static final LatLng REUTLINGEN_BUILDING_1 = new LatLng(48.481291, 9.184959);
    protected static final LatLng REUTLINGEN_BUILDING_2 = new LatLng(48.482342, 9.185873);
    protected static final LatLng REUTLINGEN_BUILDING_3 = new LatLng(48.482191, 9.186583);
    protected static final LatLng REUTLINGEN_BUILDING_4 = new LatLng(48.481654, 9.187525);
    protected static final LatLng REUTLINGEN_BUILDING_5 = new LatLng(48.482699, 9.186263);
    protected static final LatLng REUTLINGEN_BUILDING_6 = new LatLng(48.482420, 9.187888);
    protected static final LatLng REUTLINGEN_BUILDING_7 = new LatLng(48.482216, 9.188627);
    protected static final LatLng REUTLINGEN_BUILDING_8 = new LatLng(48.483336, 9.186354);
    protected static final LatLng REUTLINGEN_BUILDING_9 = new LatLng(48.483066, 9.187489);
    protected static final LatLng REUTLINGEN_BUILDING_10 = new LatLng(48.482913, 9.188484);
    protected static final LatLng REUTLINGEN_BUILDING_11 = new LatLng(48.483711, 9.188055);
    protected static final LatLng REUTLINGEN_BUILDING_12 = new LatLng(48.483432, 9.188756);
    protected static final LatLng REUTLINGEN_BUILDING_13 = new LatLng(48.483961, 9.188726);
    protected static final LatLng REUTLINGEN_BUILDING_14 = new LatLng(48.483740, 9.189431);
    protected static final LatLng REUTLINGEN_BUILDING_15 = new LatLng(48.483145, 9.189419);
    protected static final LatLng REUTLINGEN_BUILDING_16 = new LatLng(48.482137, 9.189623);
    protected static final LatLng REUTLINGEN_BUILDING_17 = new LatLng(48.481666, 9.189250);
    protected static final LatLng REUTLINGEN_BUILDING_20 = new LatLng(48.481029, 9.186182);
    //</editor-fold>
    //Now we put all these values in an array to simplify its usage
    public static final LatLng[] BUILDINGS = {
        REUTLINGEN_BUILDING_1,
        REUTLINGEN_BUILDING_2,
        REUTLINGEN_BUILDING_3,
        REUTLINGEN_BUILDING_4,
        REUTLINGEN_BUILDING_5,
        REUTLINGEN_BUILDING_6,
        REUTLINGEN_BUILDING_7,
        REUTLINGEN_BUILDING_8,
        REUTLINGEN_BUILDING_9,
        REUTLINGEN_BUILDING_10,
        REUTLINGEN_BUILDING_11,
        REUTLINGEN_BUILDING_12,
        REUTLINGEN_BUILDING_13,
        REUTLINGEN_BUILDING_14,
        REUTLINGEN_BUILDING_15,
        REUTLINGEN_BUILDING_16,
        REUTLINGEN_BUILDING_17,
        REUTLINGEN_BUILDING_20,
    };

    // LatLng location where the image of Reutlingen goes on the google map.
    // This location points to the bottom left border.
    public static final LatLng REUTLINGEN_MAP = new LatLng(48.478679, 9.188791);
    //Path in the database to get the image file
    public static final String BASIC_MAP = "images/mapa_reutlingen.png";
    //Path in the database to get the image file
    public static final String ROUTES_MAP = "images/mapa_reutlingen_rutas.png";
    //Maps loaded in the database for use
    public static final String HOSCHULE_REUTLINGEN = "Hoschule Reutlingen";
    //Blueprints loaded in the database for use
    public static final String REUTLINGEN_BUILDING_9_BLUEPRINT = "Building 9";
    //Request code for location permission request.
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    //Array of the different maps database path.
    public static String[] FILES_PATH = {BASIC_MAP, ROUTES_MAP};

    //Array of the available maps in the database to use the app
    public static String[] AVAILABLE_LOCATIONS = { HOSCHULE_REUTLINGEN };

    //Array of the available blueprints in the database to use the app
    public static String[] BUILDING_BLUEPRINT = { REUTLINGEN_BUILDING_9_BLUEPRINT };

    /**
     * ======= Enums =========
     * These are created to simplify the usage of all the constant values
     */

    //Provider type of the Location (used for the GPS location)
    public enum PROVIDERTYPE {
        NULL, GPS_PROVIDER, NETWORK_PROVIDER
    }

    //Enum used so as to know which map is active
    public enum MAP_ACTIVE {
        BASIC_MAP, ROUTE_MAP
    }

    //Enum used so as to know which type of dialog should be shown
    public enum DIALOG_TYPE {
        BUILDING_SELECTION, BUILDING_INFO, CONTACT_INFO
    }

    //All the icons used for the building info dialog
    public enum DEFAULT_ICONS {
        PLANE,
        INCLINED,
        ELEVATOR,
        AUTOMATIC_DOOR,
        ASSISTANCE,
        WC,
        EXIT
    }

    //Here is stored all the buildings icons that will be used when calling
    // the info dialog of each one
    //<editor-fold desc="Building Icons">
    public static final DEFAULT_ICONS[] BUILDING_ONE_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] BUILDING_TWO_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR };
    public static final DEFAULT_ICONS[] BUILDING_THREE_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.AUTOMATIC_DOOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] BUILDING_FOUR_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.AUTOMATIC_DOOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] BUILDING_FIVE_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] BUILDING_SIX_ICONS = { DEFAULT_ICONS.INCLINED,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] BUILDING_SEVEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] BUILDING_EIGHT_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.AUTOMATIC_DOOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] BUILDING_NINE_ICONS = { DEFAULT_ICONS.INCLINED,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] BUILDING_TEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] BUILDING_ELEVEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.AUTOMATIC_DOOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] BUILDING_TWELVE_ICONS = {  };
    public static final DEFAULT_ICONS[] BUILDING_THIRTEEN_ICONS = {  };
    public static final DEFAULT_ICONS[] BUILDING_FOURTEEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] BUILDING_FIFTEEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ASSISTANCE };
    public static final DEFAULT_ICONS[] BUILDING_SIXTEEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.AUTOMATIC_DOOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] BUILDING_SEVENTEEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] BUILDING_TWENTY_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    //</editor-fold>

    public List<File> getImageFiles() {
        return imageFiles;
    }

    public static void setImageFiles(List<File> files) {
        imageFiles = files;
    }

    public void deleteFiles() {
        for (File file : imageFiles) {
            file.delete();
        }
    }
}
