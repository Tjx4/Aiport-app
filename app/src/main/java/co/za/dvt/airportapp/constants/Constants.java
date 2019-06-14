package co.za.dvt.airportapp.constants;

import co.za.dvt.airportapp.enums.Hosts;

public class Constants {
    public static final String PAYLOAD_KEY = "payload";
    public static final String ACTIVITY_TRANSITION = "acticity_transition";
    public static final String USER_MARKER_TAG = "u";
    public static final String LAYOUT = "layout";
    public static final String TITLE = "title";
    public static final String AIRPORT_NAME = "airport_name";
    public static final String AIRPORT_IATACODE = "airport_aitacode";
    public static final String DISTANCE_FROM_USER = "distance_from_user";
    //API
    public static final String CURRENTENVIRONMENT = Hosts.liveHost.getUrl();
    public static final String GET_AIRPORTS = "pi/public/nearby/";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String DISTANCE = "distance";
}