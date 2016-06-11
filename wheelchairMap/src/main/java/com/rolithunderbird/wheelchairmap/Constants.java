package com.rolithunderbird.wheelchairmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rolithunderbird on 07.06.16.
 */
public class Constants {

    //Transparency of the GroundOverlayMap
    protected static final int TRANSPARENCY_MAX = 100;

    //Posicion donde va a arrancar la aplicacion de google maps
    protected static final LatLng REUTLINGEN_CENTER = new LatLng(48.481905, 9.188492);

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
    protected static final LatLng[] BUILDINGS = {
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
    protected static final LatLng REUTLINGEN_MAP = new LatLng(48.478679, 9.188791);

    protected static final int BASIC_MAP = R.drawable.mapa_reutlingen;

    protected static final int ROUTE_MAP = R.drawable.mapa_reutlingen_rutas;

    /**
     * Request code for location permission request.
     *
     * @see MapsActivity.onRequestPermissionsResult(int, String[], int[])
     */
    protected static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    //Provider type of the Location
    protected enum PROVIDERTYPE {
        NULL, GPS_PROVIDER, NETWORK_PROVIDER
    }

    //  What map is active
    protected enum MAP_ACTIVE {
        BASIC_MAP, ROUTE_MAP
    }
}
