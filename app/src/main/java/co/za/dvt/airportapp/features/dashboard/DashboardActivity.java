package co.za.dvt.airportapp.features.dashboard;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.viewpager.widget.ViewPager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.adapters.CustomPagerAdapter;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.di.components.AppComponent;
import co.za.dvt.airportapp.di.components.DaggerDashboardComponent;
import co.za.dvt.airportapp.di.modules.DashboardModule;
import co.za.dvt.airportapp.features.base.activity.BaseMapActivity;
import co.za.dvt.airportapp.fragments.AirportFragment;
import co.za.dvt.airportapp.helpers.NotificationHelper;
import co.za.dvt.airportapp.helpers.TransitionHelper;
import co.za.dvt.airportapp.models.AirportModel;

public class DashboardActivity extends BaseMapActivity implements DashboardView{

    @Inject
    DashboardPresenter dashboardPresenter;
    private LinearLayout airportsCarouselContainerFl;
    private ViewPager airportsViewPager;
    private final int AIRPORT_ZOOM = 13;
    private final int USER_ZOOM = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_activity);
        checkLocationPermissionAndContinue();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isNewActivity)
            return;

        overridePendingTransition(TransitionHelper.slideOutActivity()[0], TransitionHelper.slideOutActivity()[1]);
    }

    @Override
    protected void initViews() {
        airportsCarouselContainerFl = findViewById(R.id.flAirportsCarouselContainer);
        airportsViewPager = findViewById(R.id.vpAirports);
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerDashboardComponent.builder().appComponent(appComponent)
                .dashboardModule(new DashboardModule(this))
                .build()
                .inject(this);
    }
    public void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
    }

    @Override
    protected void onRequestListenerSuccess(Location location) {
        LatLng userCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
        plotUserMarker(userCoordinates, getString(R.string.you), getString(R.string.user_location_message));
        goToLocationZoomNoAnimation(userCoordinates, USER_ZOOM);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng currentCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
        if(getPresenter().isMoved25Meters(currentCoordinates, userMarker.getPosition())){
            moveUserMarker(currentCoordinates);
        }
    }

    @Override
    public DashboardPresenter getPresenter() {
        return dashboardPresenter;
    }

    @Override
    public void hideFindingAirportsDialog() {
        hideLoader();
    }

    @Override
    public void showFindingAirportsDialog(String message) {
        showLoadingDialog(message);
    }

    @Override
    public void hideDialogAndshowAirportFindErrorMessage(String errorMessage) {
        hideFindingAirportsDialog();
        NotificationHelper.showErrorDialog(this, "", "", "");
    }

    @Override
    public void onFindAirportsClicked(View view) {
        showFindingAirportsDialog(getResources().getString(R.string.finding_airports_message));
        LatLng userCoordinates = userMarker.getPosition();
        int distance = 1; // Find some way to set distance
        getPresenter().findNearbyAirports(userCoordinates, distance);
    }

    @Override
    public void onCloseAirpotListClicked(View view) {
        airportsCarouselContainerFl.setVisibility(View.GONE);
    }

    @Override
    public void plotUserMarker(LatLng latLng, String title, String snippet) {
        Marker userMarker = getMarker(latLng, title, snippet, Constants.USER_MARKER_TAG);
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.client_marker);
        userMarker.setIcon(markerIcon);
        this.userMarker = userMarker;
    }

    @Override
    public void plotAirportMarker(LatLng latLng, String title, String snippet, String tag) {
        Marker airportMarker = getMarker(latLng, title, snippet, tag);
        BitmapDescriptor markerIcon =  getBitmapDescriptor(R.drawable.ic_pin);
        airportMarker.setIcon(markerIcon);
        airportMarkers.add(airportMarker);
    }

    @Override
    public Marker getUserMarker() {
        return userMarker;
    }

    public void plotAirportMarkers(List<AirportModel> airports) {
        airportMarkers = new ArrayList<>();
        for(AirportModel airport : airports){
            String iataCode = airport.getIataCode();
            LatLng airportCoordinates = new LatLng(airport.getLatitude(), airport.getLongitude());
            String airportName =  airport.getName();
//String distanceFromUser = getPresenter().getDistanceFromUserMessage(userMarker.getPosition(), airportCoordinates);
            plotAirportMarker(airportCoordinates, airportName, null, iataCode);
        }

        listenForMarkerClicks();
        goToLocationZoomAnimated(airportMarkers.get(0).getPosition(), AIRPORT_ZOOM);
    }

    @Override
    public void showAirportsCarousel(List<AirportModel> airports){
        airportsCarouselContainerFl.setVisibility(View.VISIBLE);

        List<AirportFragment> airportFragments = new ArrayList<>();
        for(AirportModel airport : airports){
            AirportFragment airportFragment = AirportFragment.getInstance(this, airport);
            airportFragments.add(airportFragment);
        }

        CustomPagerAdapter stylistsViewPagerAdapter = new CustomPagerAdapter(this, this.getSupportFragmentManager(), airportFragments);
        stylistsViewPagerAdapter.notifyDataSetChanged();
        airportsViewPager.setAdapter(stylistsViewPagerAdapter);
        airportsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                goToLocationZoomAnimated(airportMarkers.get(position).getPosition(), AIRPORT_ZOOM);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected void listenForMarkerClicks() {
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String selectedMarkerTag = marker.getTag().toString();

                if(selectedMarkerTag.equals(Constants.USER_MARKER_TAG))
                    return false;

                try{
                    int markerIndex = 0;
                    for(Marker airportMarker : airportMarkers){
                        String currentMarkerTag = airportMarker.getTag().toString();
                        if(selectedMarkerTag.equals(currentMarkerTag)){
                            goToAirportPosition(markerIndex);
                            break;
                        }
                        ++markerIndex;
                    }
                }
                catch (Exception e){
                    Log.e("MARKER_CLICK_ERROR", "Marker click error: "+e);
                }
                return false;
            }
        });
    }

    protected void goToAirportPosition(int position){
        airportsViewPager.setCurrentItem(position);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}