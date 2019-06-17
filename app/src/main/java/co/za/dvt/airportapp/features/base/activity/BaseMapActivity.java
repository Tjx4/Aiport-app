package co.za.dvt.airportapp.features.base.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
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
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.List;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.helpers.NotificationHelper;
import co.za.dvt.airportapp.helpers.PermissionsHelper;

public abstract class BaseMapActivity extends BaseparentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    protected SupportMapFragment mapFragment;
    protected GoogleMap googleMap;
    protected LocationRequest locationRequest;
    protected GoogleApiClient googleApiClient;
    protected List<Marker> airportMarkers;
    protected Marker userMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWakeLock();
    }

    protected abstract void onGpsOff();
    protected abstract void onPermissionDenied();

    protected void setWakeLock() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
                    checkGoogleApi();
                }
                else {
                    onPermissionDenied();
                }
            }
        }
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

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        buildGoogleApiClient();
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        moveLocationButtonToBottomRight();

    }

    protected void moveLocationButtonToBottomRight() {
        View mapView = mapFragment.getView();

        if (mapView != null) {
            View childView = mapView.findViewById(Integer.parseInt("1"));

            if(childView != null){
                View locationButton = ((View) childView.getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)locationButton.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

                int bottomMargin =  (int) getResources().getDimension(R.dimen.xlarge_view_margin);
                int rightMargin = (int) getResources().getDimension(R.dimen.large_view_margin);
                layoutParams.setMargins(0, 0, rightMargin, bottomMargin);
                locationButton.setLayoutParams(layoutParams);
            }
        }
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
        locationRequest.setInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (PermissionsHelper.isAccesFimeLocationPermissionGranted(this)){
            final LocationCallback locationCallback = new LocationCallback() {
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

            final FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        onRequestListenerSuccess(location);
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }

            });

        }
        else {
            checkLocationPermissionAndContinue();
        }
    }

    protected abstract void onRequestListenerSuccess(Location location);

    @Override
    public void onConnectionSuspended(int i) {
        NotificationHelper.showShortToast(this, getResources().getString(R.string.connection_suspended_message));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        NotificationHelper.showShortToast(this, getResources().getString(R.string.api_user_error));
    }

    protected void moveUserMarker(LatLng latLng) {
        moveMarker(latLng, userMarker);
    }

    private void moveMarker(LatLng toLatLong, Marker currentMarker) {
        animateMarkerMovement(currentMarker, toLatLong,false);
    }

    public void animateMarkerMovement(final Marker marker, final LatLng toPosition, final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = googleMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 900;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude;

                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
// Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    protected Marker getMarker(LatLng latLng, String title, String snippet, String tag) {
        MarkerOptions markerOptions = new MarkerOptions()
                .title(title)
                .flat(true)
                .anchor(0.5f, 0.5f)
                .rotation(0)
                .position(latLng);

        if(snippet != null && !snippet.isEmpty()){
            markerOptions.snippet(snippet);
        }

        Marker airportMarker = googleMap.addMarker(markerOptions);
        airportMarker.setTag(tag);
        return airportMarker;
    }

    protected BitmapDescriptor getBitmapDescriptor(@DrawableRes int id) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}