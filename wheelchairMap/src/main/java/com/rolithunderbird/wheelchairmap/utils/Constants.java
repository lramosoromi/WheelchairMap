package com.rolithunderbird.wheelchairmap.utils;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store all the necessary constants that will be used in all the app.
 * With this the only place to change code once some values changes should be this class.
 * Created by rolithunderbird on 07.06.16.
 */
public class Constants {

    //The image files created when downloading content from the storage
    //To be used inside this class
    private static List<File> imageFiles;
    private static List<LatLng> coordinates;

    //The broadcast filter used to by the broadcast receiver
    public static final String BROADCAST_FILTER = "com.rolithunderbird.wheelchairmap.BROADCAST";
    //Transparency maximum value of the GroundOverlayMap
    public static final int TRANSPARENCY_MAX = 100;
    //Request code for location permission request.
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    //Max touch distance to closest building so as to show info
    public static final int MAX_CLOSEST_DISTANCE = 20;

    /**
     * ======== String values for Firebase Storage ========
     */
    //Storage Reference path
    public static String storageReferencePath = "gs://wheelchairmap.appspot.com";
    //Database Reference path
    public static String databaseReferencePath = "https://wheelchairmap.firebaseio.com";
    //String in path of map file
    public static final String FILE_MAP_STRING = "mapa";
    //String in path of building image file
    public static final String FILE_BUILDING_IMAGE_STRING = "image";
    //String in path of blueprint file
    public static final String FILE_BLUEPRINT_STRING = "building";
    //String in path of icon file
    public static final String FILE_ICON_STRING = "icon";

    /**
     * ======== String values for Firebase Database ========
     */
    //String value of google maps starting point key
    public static final String MAP_START_KEY = "map_center";
    //String value of campus map position key
    public static final String MAP_POSITION_KEY = "campus_map_position";
    //<editor-fold desc="Building String Key">
    protected static final String REUTLINGEN_BUILDING_1_KEY = "building1_position";
    protected static final String REUTLINGEN_BUILDING_2_KEY = "building2_position";
    protected static final String REUTLINGEN_BUILDING_3_KEY = "building3_position";
    protected static final String REUTLINGEN_BUILDING_4_KEY = "building4_position";
    protected static final String REUTLINGEN_BUILDING_5_KEY = "building5_position";
    protected static final String REUTLINGEN_BUILDING_6_KEY = "building6_position";
    protected static final String REUTLINGEN_BUILDING_7_KEY = "building7_position";
    protected static final String REUTLINGEN_BUILDING_8_KEY = "building8_position";
    protected static final String REUTLINGEN_BUILDING_9_KEY = "building9_position";
    protected static final String REUTLINGEN_BUILDING_10_KEY = "building10_position";
    protected static final String REUTLINGEN_BUILDING_11_KEY = "building11_position";
    protected static final String REUTLINGEN_BUILDING_12_KEY = "building12_position";
    protected static final String REUTLINGEN_BUILDING_13_KEY = "building13_position";
    protected static final String REUTLINGEN_BUILDING_14_KEY = "building14_position";
    protected static final String REUTLINGEN_BUILDING_15_KEY = "building15_position";
    protected static final String REUTLINGEN_BUILDING_16_KEY = "building16_position";
    protected static final String REUTLINGEN_BUILDING_17_KEY = "building17_position";
    protected static final String REUTLINGEN_BUILDING_20_KEY = "building20_position";
    //</editor-fold>
    //Array of the different keys path in the database.
    public static String[] KEYS_PATH = {
            MAP_START_KEY, MAP_POSITION_KEY, REUTLINGEN_BUILDING_1_KEY, REUTLINGEN_BUILDING_2_KEY,
            REUTLINGEN_BUILDING_3_KEY, REUTLINGEN_BUILDING_4_KEY, REUTLINGEN_BUILDING_5_KEY,
            REUTLINGEN_BUILDING_6_KEY, REUTLINGEN_BUILDING_7_KEY, REUTLINGEN_BUILDING_8_KEY,
            REUTLINGEN_BUILDING_9_KEY, REUTLINGEN_BUILDING_10_KEY, REUTLINGEN_BUILDING_11_KEY,
            REUTLINGEN_BUILDING_12_KEY, REUTLINGEN_BUILDING_13_KEY, REUTLINGEN_BUILDING_14_KEY,
            REUTLINGEN_BUILDING_15_KEY, REUTLINGEN_BUILDING_16_KEY, REUTLINGEN_BUILDING_17_KEY,
            REUTLINGEN_BUILDING_20_KEY};

    //LatLng position where the google map activity starts
    public static final LatLng REUTLINGEN_CENTER = new LatLng(48.481905, 9.188492);
    public static final LatLng AUSTRAL_CENTER = new LatLng(-34.456582, -58.859102);

    //Bearing of the image to be added to the map
    public static final Integer REUTLINGEN_BEARING = -60;
    public static final Integer AUSTRAL_BEARING = 60;

    //Bearing of the image to be added to the map
    public static final Integer REUTLINGEN_CAMERA_ZOOM = 16;
    public static final Integer AUSTRAL_CAMERA_ZOOM = 15;

    // LatLng location where the file of Reutlingen campus goes on the google map.
    // This location points to the bottom left border.
    public static final LatLng REUTLINGEN_MAP = new LatLng(48.478679, 9.188791);
    public static final LatLng AUSTRAL_MAP = new LatLng(-34.456568, -58.859728);

    //Here are stored all the buildings LatLng coordinates that will be used in the app
    //<editor-fold desc="Building LatLng Coordinates">
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

    /**
     * ========= Files ========
     * All the image files used in the app
     * Its just the path because that is what we use to download them from the storage
     */
    //<editor-fold desc="Path of different files">
    //Path in the storage to get the image file
    public static final String BASIC_MAP_REUTLINGEN = "images/mapa_reutlingen.png";
    //Path in the storage to get the image file
    public static final String ROUTES_MAP_REUTLINGEN = "images/mapa_reutlingen_rutas.png";
    //Path in the storage to get the image file
    public static final String BUILDING_IMAGE = "images/building_image.png";
    //Path in the storage to get the icon file
    public static final String ICON_PLANE = "icons/icon_plane.png";
    //Path in the storage to get the icon file
    public static final String ICON_INCLINED = "icons/icon_inclined.png";
    //Path in the storage to get the icon file
    public static final String ICON_ELEVATOR = "icons/icon_elevator.png";
    //Path in the storage to get the icon file
    public static final String ICON_AUTOMATIC_DOOR = "icons/icon_automatic_door.png";
    //Path in the storage to get the icon file
    public static final String ICON_ASSISTANCE = "icons/icon_needs_assistance.png";
    //Path in the storage to get the icon file
    public static final String ICON_WC = "icons/icon_wc.png";
    //Path in the storage to get the icon file
    public static final String ICON_EXIT = "icons/icon_exit.png";
    //Path in the storage to get the blueprint file
    public static final String BUILDING9_UNDERGROUND_BLUEPRINT = "blueprints/building9_underground.png";
    //Path in the storage to get the blueprint file
    public static final String BUILDING9_MAIN_FLOOR_BLUEPRINT = "blueprints/building9_0floor.png";
    //Path in the storage to get the blueprint file
    public static final String BUILDING9_FIRST_FLOOR_BLUEPRINT = "blueprints/building9_1floor.png";
    //Path in the storage to get the blueprint file
    public static final String BUILDING9_SECOND_FLOOR_BLUEPRINT = "blueprints/building9_2floor.png";
    //Path in the storage to get the blueprint file
    public static final String BUILDING9_THIRD_FLOOR_BLUEPRINT = "blueprints/building9_3floor.png";
    //</editor-fold>

    //Path in the storage to get the image file
    public static final String BASIC_MAP_AUSTRAL = "images/mapa_austral.png";

    //Array of the different files path in the storage.
    public static String[] FILES_PATH_REUTLINGEN = {
            BASIC_MAP_REUTLINGEN, ROUTES_MAP_REUTLINGEN, BUILDING_IMAGE, ICON_PLANE, ICON_INCLINED,
            ICON_ELEVATOR, ICON_AUTOMATIC_DOOR, ICON_ASSISTANCE, ICON_WC, ICON_EXIT,
            BUILDING9_UNDERGROUND_BLUEPRINT, BUILDING9_MAIN_FLOOR_BLUEPRINT,
            BUILDING9_FIRST_FLOOR_BLUEPRINT, BUILDING9_SECOND_FLOOR_BLUEPRINT, BUILDING9_THIRD_FLOOR_BLUEPRINT};

    //Array of the different files path in the storage.
    public static String[] FILES_PATH_AUSTRAL = {
            BASIC_MAP_AUSTRAL, BUILDING_IMAGE, ICON_PLANE, ICON_INCLINED,
            ICON_ELEVATOR, ICON_AUTOMATIC_DOOR, ICON_ASSISTANCE, ICON_WC, ICON_EXIT};

    //Maps loaded in the storage for use
    public static final String HOSCHULE_REUTLINGEN = "Hoschule Reutlingen";
    public static final String UNIVERSIDAD_AUSTRAL = "Austral University";

    //Array of the available maps in the storage to use the app
    public static String[] AVAILABLE_LOCATIONS = { HOSCHULE_REUTLINGEN, UNIVERSIDAD_AUSTRAL };

    //Blueprints loaded in the storage for use
    public static final String NONE = "None";
    //Blueprints loaded in the storage for use
    public static final String REUTLINGEN_BUILDING_9_BLUEPRINT = "Building 9";
    //Array of the available blueprints in the storage to use the app
    public static String[] BUILDING_BLUEPRINT = { NONE, REUTLINGEN_BUILDING_9_BLUEPRINT };

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
        BUILDING_SELECTION, BUILDING_INFO, CONTACT_INFO, APP_INFO, BLUEPRINT_INFO
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

    /**
     * Public methods used to manage files and coordinates downloaded
     */

    public static void setImageFiles(List<File> files) {
        imageFiles = files;
    }

    public static List<File> getImageFiles() {
        return imageFiles;
    }

    public static void setCoordinates(List<String> coordinatesString) {
        List<LatLng> aux = new ArrayList<>();
        for (String string : coordinatesString) {
            float latitud = Float.parseFloat(string.split(",")[0].trim());
            float longitud = Float.parseFloat(string.split(",")[1].trim());
            aux.add(new LatLng(latitud, longitud));
        }
        coordinates = aux;
    }

    public static List<LatLng> getCoordinates() { return coordinates; }

    public static void deleteFiles() {
        if (imageFiles != null) {
            for (File file : imageFiles) {
                file.delete();
            }
            imageFiles.clear();
        }
    }
}
