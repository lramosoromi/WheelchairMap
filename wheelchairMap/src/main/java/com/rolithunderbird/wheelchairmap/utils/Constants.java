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
    private static final String MAP_START_KEY = "map_center";
    //String value of campus map position key
    private static final String MAP_POSITION_KEY = "campus_map_position";
    //<editor-fold desc="Building String Key">
    private static final String REUTLINGEN_BUILDING_1_KEY = "building1_position";
    private static final String REUTLINGEN_BUILDING_2_KEY = "building2_position";
    private static final String REUTLINGEN_BUILDING_3_KEY = "building3_position";
    private static final String REUTLINGEN_BUILDING_4_KEY = "building4_position";
    private static final String REUTLINGEN_BUILDING_5_KEY = "building5_position";
    private static final String REUTLINGEN_BUILDING_6_KEY = "building6_position";
    private static final String REUTLINGEN_BUILDING_7_KEY = "building7_position";
    private static final String REUTLINGEN_BUILDING_8_KEY = "building8_position";
    private static final String REUTLINGEN_BUILDING_9_KEY = "building9_position";
    private static final String REUTLINGEN_BUILDING_10_KEY = "building10_position";
    private static final String REUTLINGEN_BUILDING_11_KEY = "building11_position";
    private static final String REUTLINGEN_BUILDING_12_KEY = "building12_position";
    private static final String REUTLINGEN_BUILDING_13_KEY = "building13_position";
    private static final String REUTLINGEN_BUILDING_14_KEY = "building14_position";
    private static final String REUTLINGEN_BUILDING_15_KEY = "building15_position";
    private static final String REUTLINGEN_BUILDING_16_KEY = "building16_position";
    private static final String REUTLINGEN_BUILDING_17_KEY = "building17_position";
    private static final String REUTLINGEN_BUILDING_20_KEY = "building20_position";
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
    public static final Integer AUSTRAL_CAMERA_ZOOM = 18;

    // LatLng location where the file of Reutlingen campus goes on the google map.
    // This location points to the bottom left border.
    public static final LatLng REUTLINGEN_MAP = new LatLng(48.478679, 9.188791);
    public static final LatLng AUSTRAL_MAP = new LatLng(-34.456512, -58.859934);

    //Size of the image to be shown on the map
    public static final Integer REUTLINGEN_MAP_WIDTH = 600;
    public static final Integer REUTLINGEN_MAP_HEIGHT = 465;
    public static final Integer AUSTRAL_MAP_WIDTH = 150;
    public static final Integer AUSTRAL_MAP_HEIGHT = 100;

    //Here are stored all the buildings LatLng coordinates that will be used in the app
    //<editor-fold desc="Reutlingen Building LatLng Coordinates">
    private static final LatLng REUTLINGEN_BUILDING_1 = new LatLng(48.481291, 9.184959);
    private static final LatLng REUTLINGEN_BUILDING_2 = new LatLng(48.482342, 9.185873);
    private static final LatLng REUTLINGEN_BUILDING_3 = new LatLng(48.482191, 9.186583);
    private static final LatLng REUTLINGEN_BUILDING_4 = new LatLng(48.481654, 9.187525);
    private static final LatLng REUTLINGEN_BUILDING_5 = new LatLng(48.482699, 9.186263);
    private static final LatLng REUTLINGEN_BUILDING_6 = new LatLng(48.482420, 9.187888);
    private static final LatLng REUTLINGEN_BUILDING_7 = new LatLng(48.482216, 9.188627);
    private static final LatLng REUTLINGEN_BUILDING_8 = new LatLng(48.483336, 9.186354);
    private static final LatLng REUTLINGEN_BUILDING_9 = new LatLng(48.483066, 9.187489);
    private static final LatLng REUTLINGEN_BUILDING_10 = new LatLng(48.482913, 9.188484);
    private static final LatLng REUTLINGEN_BUILDING_11 = new LatLng(48.483711, 9.188055);
    private static final LatLng REUTLINGEN_BUILDING_12 = new LatLng(48.483432, 9.188756);
    private static final LatLng REUTLINGEN_BUILDING_13 = new LatLng(48.483961, 9.188726);
    private static final LatLng REUTLINGEN_BUILDING_14 = new LatLng(48.483740, 9.189431);
    private static final LatLng REUTLINGEN_BUILDING_15 = new LatLng(48.483145, 9.189419);
    private static final LatLng REUTLINGEN_BUILDING_16 = new LatLng(48.482137, 9.189623);
    private static final LatLng REUTLINGEN_BUILDING_17 = new LatLng(48.481666, 9.189250);
    private static final LatLng REUTLINGEN_BUILDING_20 = new LatLng(48.481029, 9.186182);
    //</editor-fold>
    //<editor-fold desc="Austral Building LatLng Coordinates">
    private static final LatLng AUSTRAL_BUILDING_ADMIN = new LatLng(-34.456641, -58.858885);
    private static final LatLng AUSTRAL_BUILDING_A = new LatLng(-34.456557, -58.859406);
    private static final LatLng AUSTRAL_BUILDING_B = new LatLng(-34.456981, -58.859049);
    //</editor-fold>

    //Now we put all these values in an array to simplify its usage
    public static final LatLng[] REUTLINGEN_BUILDINGS = {
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

    public static final LatLng[] AUSTRAL_BUILDINGS = {
        AUSTRAL_BUILDING_ADMIN,
        AUSTRAL_BUILDING_A,
        AUSTRAL_BUILDING_B,
    };

    /**
     * ========= Files ========
     * All the image files used in the app
     * Its just the path because that is what we use to download them from the storage
     */
    //<editor-fold desc="Path of different files">
    //Path in the storage to get the image file
    private static final String BUILDING_IMAGE = "images/building_image.png";
    //Path in the storage to get the icon file
    private static final String ICON_PLANE = "icons/icon_plane.png";
    //Path in the storage to get the icon file
    private static final String ICON_INCLINED = "icons/icon_inclined.png";
    //Path in the storage to get the icon file
    private static final String ICON_ELEVATOR = "icons/icon_elevator.png";
    //Path in the storage to get the icon file
    private static final String ICON_AUTOMATIC_DOOR = "icons/icon_automatic_door.png";
    //Path in the storage to get the icon file
    private static final String ICON_ASSISTANCE = "icons/icon_needs_assistance.png";
    //Path in the storage to get the icon file
    private static final String ICON_WC = "icons/icon_wc.png";
    //Path in the storage to get the icon file
    private static final String ICON_EXIT = "icons/icon_exit.png";

    //<editor-fold desc="Path of reutlingen files">
    //Path in the storage to get the image file
    private static final String BASIC_MAP_REUTLINGEN = "images/mapa_reutlingen.png";
    //Path in the storage to get the image file
    private static final String ROUTES_MAP_REUTLINGEN = "images/mapa_reutlingen_rutas.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING9_UNDERGROUND_BLUEPRINT = "blueprints/building9_underground.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING9_MAIN_FLOOR_BLUEPRINT = "blueprints/building9_0floor.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING9_FIRST_FLOOR_BLUEPRINT = "blueprints/building9_1floor.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING9_SECOND_FLOOR_BLUEPRINT = "blueprints/building9_2floor.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING9_THIRD_FLOOR_BLUEPRINT = "blueprints/building9_3floor.png";
    //</editor-fold>
    //<editor-fold desc="Path of austral files">
    //Path in the storage to get the image file
    private static final String BASIC_MAP_AUSTRAL = "images/mapa_austral.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING_ADMIN_MAIN_FLOOR_BLUEPRINT = "blueprints/buildingAdmin_0floor.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING_ADMIN_FIRST_FLOOR_BLUEPRINT = "blueprints/buildingAdmin_1floor.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING_A_MAIN_FLOOR_BLUEPRINT = "blueprints/buildingA_0floor.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING_A_FIRST_FLOOR_BLUEPRINT = "blueprints/buildingA_1floor.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING_B_MAIN_FLOOR_BLUEPRINT = "blueprints/buildingB_0floor.png";
    //Path in the storage to get the blueprint file
    private static final String BUILDING_B_FIRST_FLOOR_BLUEPRINT = "blueprints/buildingB_1floor.png";
    //</editor-fold>
    //</editor-fold>


    //Array of the different files path in the storage.
    public static String[] FILES_PATH_REUTLINGEN = {
            BASIC_MAP_REUTLINGEN, ROUTES_MAP_REUTLINGEN, BUILDING_IMAGE, ICON_PLANE, ICON_INCLINED,
            ICON_ELEVATOR, ICON_AUTOMATIC_DOOR, ICON_ASSISTANCE, ICON_WC, ICON_EXIT,
            BUILDING9_UNDERGROUND_BLUEPRINT, BUILDING9_MAIN_FLOOR_BLUEPRINT,
            BUILDING9_FIRST_FLOOR_BLUEPRINT, BUILDING9_SECOND_FLOOR_BLUEPRINT, BUILDING9_THIRD_FLOOR_BLUEPRINT};

    //Array of the different files path in the storage.
    public static String[] FILES_PATH_AUSTRAL = {
            BASIC_MAP_AUSTRAL, BASIC_MAP_AUSTRAL, BUILDING_IMAGE, ICON_PLANE, ICON_INCLINED,
            ICON_ELEVATOR, ICON_AUTOMATIC_DOOR, ICON_ASSISTANCE, ICON_WC, ICON_EXIT,
            BUILDING_ADMIN_MAIN_FLOOR_BLUEPRINT, BUILDING_ADMIN_FIRST_FLOOR_BLUEPRINT,
            BUILDING_A_MAIN_FLOOR_BLUEPRINT, BUILDING_A_FIRST_FLOOR_BLUEPRINT,
            BUILDING_B_MAIN_FLOOR_BLUEPRINT, BUILDING_B_FIRST_FLOOR_BLUEPRINT};

    //Maps loaded in the storage for use
    private static final String HOSCHULE_REUTLINGEN = "Reutlingen";
    private static final String UNIVERSIDAD_AUSTRAL = "Austral";

    //Array of the available maps in the storage to use the app
    public static String[] AVAILABLE_LOCATIONS = { HOSCHULE_REUTLINGEN, UNIVERSIDAD_AUSTRAL };

    //Blueprints loaded in the storage for use
    private static final String NONE = "None";
    //Blueprints loaded in the storage for use
    //<editor-fold desc="Reutlingen Building Blueprints">
    private static final String REUTLINGEN_BUILDING_9_BLUEPRINT = "Building 9";
    //Array of the available blueprints in the storage to use the app
    public static String[] REUTLINGEN_BUILDING_BLUEPRINT = { NONE, REUTLINGEN_BUILDING_9_BLUEPRINT };
    //</editor-fold>
    //<editor-fold desc="Austral Building Blueprints">
    private static final String AUSTRAL_BUILDING_ADMIN_BLUEPRINT = "Building Admin";
    private static final String AUSTRAL_BUILDING_A_BLUEPRINT = "Building A";
    private static final String AUSTRAL_BUILDING_B_BLUEPRINT = "Building B";
    //Array of the available blueprints in the storage to use the app
    public static String[] AUSTRAL_BUILDING_BLUEPRINT = { NONE, AUSTRAL_BUILDING_ADMIN_BLUEPRINT,
            AUSTRAL_BUILDING_A_BLUEPRINT, AUSTRAL_BUILDING_B_BLUEPRINT };
    //</editor-fold>

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
    //<editor-fold desc="Reutlingen Building Icons">
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_ONE_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_TWO_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_THREE_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.AUTOMATIC_DOOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_FOUR_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.AUTOMATIC_DOOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_FIVE_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_SIX_ICONS = { DEFAULT_ICONS.INCLINED,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_SEVEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_EIGHT_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.AUTOMATIC_DOOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_NINE_ICONS = { DEFAULT_ICONS.INCLINED,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_TEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_ELEVEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.AUTOMATIC_DOOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_TWELVE_ICONS = {  };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_THIRTEEN_ICONS = {  };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_FOURTEEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_FIFTEEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ASSISTANCE };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_SIXTEEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.AUTOMATIC_DOOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_SEVENTEEN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC };
    public static final DEFAULT_ICONS[] REUTLINGEN_BUILDING_TWENTY_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    //</editor-fold>
    //<editor-fold desc="Austral Building Icons">
    public static final DEFAULT_ICONS[] AUSTRAL_BUILDING_ADMIN_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ASSISTANCE, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] AUSTRAL_BUILDING_A_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
    public static final DEFAULT_ICONS[] AUSTRAL_BUILDING_B_ICONS = { DEFAULT_ICONS.PLANE,
            DEFAULT_ICONS.ELEVATOR, DEFAULT_ICONS.WC, DEFAULT_ICONS.EXIT };
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

    public static Boolean checkFilesToDownloadAreImageFiles(String[] filesToDownload) {
        List<String> filesToDownloadName = new ArrayList<>();
        List<String> imageFilesName = new ArrayList<>();
        for (String fileToDownloadPath : filesToDownload) {
            //Eliminate parent path of file (e.g. parent/child.png)
            String fileNameWithEnding = fileToDownloadPath.split("/")[fileToDownloadPath.split("/").length - 1];
            //Eliminate extension of file (e.g. .png)
            String fileName = fileNameWithEnding.substring(0, fileNameWithEnding.length() - 4);
            filesToDownloadName.add(fileName);
        }
        for (File file : imageFiles) {
            imageFilesName.add(file.getName());
        }

        Boolean result = true;
        for (String fileToDownloadName : filesToDownloadName) {
            if (!imageFilesName.contains(fileToDownloadName)) {
                result = false;
                break;
            }
        }
        return result;
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
