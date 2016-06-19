package com.rolithunderbird.wheelchairmap.utils;

import com.google.android.gms.maps.model.LatLng;
import com.rolithunderbird.wheelchairmap.R;
import com.rolithunderbird.wheelchairmap.activities.MapsActivity;

/**
 * Created by rolithunderbird on 07.06.16.
 */
public class Constants {

    //Transparency of the GroundOverlayMap
    public static final int TRANSPARENCY_MAX = 100;

    //Posicion donde va a arrancar la aplicacion de google maps
    public static final LatLng REUTLINGEN_CENTER = new LatLng(48.481905, 9.188492);

    //Aca van todas las Locations de los distintos edificios del mapa
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

    //Location donde va el mapa. Esta location es el borde inferior izquierdo
    public static final LatLng REUTLINGEN_MAP = new LatLng(48.478679, 9.188791);

    public static final int BASIC_MAP = R.drawable.mapa_reutlingen;

    public static final int ROUTE_MAP = R.drawable.mapa_reutlingen_rutas;

    /**
     * Request code for location permission request.
     *
     * @see MapsActivity.onRequestPermissionsResult(int, String[], int[])
     */
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    //Provider type of the Location
    public enum PROVIDERTYPE {
        NULL, GPS_PROVIDER, NETWORK_PROVIDER
    }

    //  What map is active
    public enum MAP_ACTIVE {
        BASIC_MAP, ROUTE_MAP
    }

    public enum DIALOG_TYPE {
        BUILDING_SELECTION, BUILDING_INFO, CONTACT_INFO
    }

    public enum DEFAULT_ICONS {
        PLANE,
        INCLINED,
        ELEVATOR,
        AUTOMATIC_DOOR,
        ASSISTANCE,
        WC,
        EXIT
    }

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
}
