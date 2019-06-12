package co.za.dvt.airportapp.features.base.activity;

import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public abstract class BaseMapActivity extends BaseAsyncActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    protected SupportMapFragment mapFragment;
    protected GoogleMap googleMap;

    protected void prepareMap() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAv = api.isGooglePlayServicesAvailable(this);

        if (isAv == ConnectionResult.SUCCESS) {
            initMap();
        } else if (api.isUserResolvableError(isAv)) {
            //Toast.makeText(this, "No but it can be fixed", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, "Hell no", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
//
    }

    @Override
    public void onConnectionSuspended(int i) {
//
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    protected abstract void initMap();
    protected abstract void onFineLocationPermissionGranted();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // ACCESS_FINE_LOCATION

        //googleMap.setMyLocationEnabled(true);
        this.googleMap = googleMap;
        onFineLocationPermissionGranted();
    }
}
