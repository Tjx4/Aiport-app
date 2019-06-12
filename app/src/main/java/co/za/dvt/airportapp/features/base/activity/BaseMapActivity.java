package co.za.dvt.airportapp.features.base.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.helpers.NotificationHelper;
import co.za.dvt.airportapp.helpers.PermissionsHelper;
import co.za.dvt.airportapp.helpers.UnitConverterHelper;
import co.za.dvt.airportapp.models.AirportModel;

public abstract class BaseMapActivity extends BaseAsyncActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    protected SupportMapFragment mapFragment;
    protected GoogleMap googleMap;
    protected LocationRequest locationRequest;
    protected GoogleApiClient googleApiClient;
    protected List<Marker> airportMarkers;
    protected Marker userMarker;
    protected LatLng lastCordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void checkLocationPermissionAndContinue() {
        if (PermissionsHelper.isAccesFimeLocationPermissionGranted(this)){
            checkGoogleApi();
        }
        else {
            PermissionsHelper.requestAccesFimeLocationPermission(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int grantResult = grantResults[i];

            if(permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    checkLocationPermissionAndContinue();
                }
                else{
                    NotificationHelper.showErrorDialog(this, getResources().getString(R.string.error_dialog_title), getResources().getString(R.string.permission_denied_message), getResources().getString(R.string.ok));
                }
            }

        }
    }

    protected boolean isMovedFiveMeters(LatLng userCordinates){
        boolean isSameLocation = (lastCordinates == null)? true : lastCordinates.latitude == userCordinates.latitude && lastCordinates.longitude == userCordinates.longitude ;
        return lastCordinates == null || !isSameLocation;
    }

    protected void checkGoogleApi() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAv = api.isGooglePlayServicesAvailable(this);

        if (isAv == ConnectionResult.SUCCESS) {
            initMap();
        } else if (api.isUserResolvableError(isAv)) {
            NotificationHelper.showLongToast(this, getResources().getString(R.string.api_user_error));
        } else {
            NotificationHelper.showLongToast(this, getResources().getString(R.string.api_error));
        }
    }

    protected abstract void initMap();

    @Override
    public void onLocationChanged(Location location) {
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        buildGoogleApiClient();
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        moveLocationButtonToBottomRight();
    }

    private void moveInToLocation(LatLng ll, int zoom) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        googleMap.moveCamera(cameraUpdate);
    }

    private void zoomInToLocation(LatLng ll, int zoom, boolean animate) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        googleMap.animateCamera(cameraUpdate);
    }

    protected void goToLocationZoomNoAnimation(LatLng ll, int zoom) {
        moveInToLocation(ll, zoom);
    }

    protected void goToLocationZoomAnimated(LatLng ll, int zoom) {
        zoomInToLocation(ll, zoom, true);
    }
    protected void buildGoogleApiClient(){

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (PermissionsHelper.isAccesFimeLocationPermissionGranted(this)){
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        onLocationChanged(location);
                    }
                }
            };

            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {
                                LatLng userCordinates = new LatLng(location.getLatitude(), location.getLongitude());
                                plotUserMarker(userCordinates, getString(R.string.you), getString(R.string.user_location_message));
                                goToLocationZoomNoAnimation(userCordinates, 16);
                            }
                        }
                    });

        }
        else {
            checkLocationPermissionAndContinue();
        }
    }

    protected void moveLocationButtonToBottomRight() {
        View mapView = mapFragment.getView();

        if (mapView != null) {
            View childView = mapView.findViewById(Integer.parseInt("1"));

            if(childView != null){
                View locationButton = ((View) childView.getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)locationButton.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                int bottomMarginDp = (int) UnitConverterHelper.pixelToDp(200, this);
                int marginInDp = (int) UnitConverterHelper.pixelToDp(20, this);
                layoutParams.setMargins(marginInDp, marginInDp, marginInDp, bottomMarginDp);
                locationButton.setLayoutParams(layoutParams);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
//
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
    }

    protected void moveUserMarker(LatLng latLng) {
        userMarker.setPosition(latLng);
    }

    protected Marker getMarker(LatLng latLng, String title, String snippet, String tag) {
        MarkerOptions markerOptions = new MarkerOptions()
                .title(title)
                .flat(true)
                .anchor(0.5f, 0.5f)
                .rotation(0)
                .snippet(snippet)
                .position(latLng);

        Marker airportMarker = googleMap.addMarker(markerOptions);
        airportMarker.setTag(tag);
        return airportMarker;
    }

    protected void plotUserMarker(LatLng latLng, String title, String snippet) {
        Marker userMarker = getMarker(latLng, title, snippet, Constants.USER_MARKER_TAG);
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.client_marker);
        userMarker.setIcon(markerIcon);
        this.userMarker = userMarker;
    }

    protected void plotAirportMarker(LatLng latLng, String title, String snippet, String tag) {
        Marker airportMarker = getMarker(latLng, title, snippet, tag);
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin);
        airportMarker.setIcon(markerIcon);
        airportMarkers.add(airportMarker);
    }

    protected void plotAirportMarkers(List<AirportModel> airports) {
        airportMarkers.clear();
        for(AirportModel airport : airports){
            String iataCode = airport.getIataCode();
            LatLng userCordinates = new LatLng(airport.getLatitude(), airport.getLongitude());
            String airportName =  airport.getName();
            String distanceFromUser = "0km from you";
            plotAirportMarker(userCordinates, airportName, distanceFromUser, iataCode);
        }
    }

    protected void listenForMarkerClicks() {
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String selectedMarkerTag = marker.getTag().toString();

                if(selectedMarkerTag.equals(Constants.USER_MARKER_TAG)){
                    return false;
                }

                try{
                    String airportKey = marker.getTag().toString();
                    for(Marker airportMarker : airportMarkers){
                        String currentMarkerTag = airportMarker.getTag().toString();

                        if(airportKey.equals(currentMarkerTag)){
                            // Go to flight schedule for  airportMarker
                        }
                    }
                }
                catch (Exception e){
                    Log.e("MARKER_CLICK_ERROR", "Marker click error: "+e);
                }

                return false;
            }
        });

    }


}
