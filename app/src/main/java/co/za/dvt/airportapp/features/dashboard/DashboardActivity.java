package co.za.dvt.airportapp.features.dashboard;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import co.za.dvt.airportapp.adapters.AirportPagerAdapter;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.di.components.AppComponent;
import co.za.dvt.airportapp.di.components.DaggerDashboardComponent;
import co.za.dvt.airportapp.di.modules.DashboardModule;
import co.za.dvt.airportapp.features.base.activity.BaseMapActivity;
import co.za.dvt.airportapp.features.departures.FlightsActivity;
import co.za.dvt.airportapp.fragments.AirportFragment;
import co.za.dvt.airportapp.fragments.SetDistanceFragment;
import co.za.dvt.airportapp.helpers.ConverterHelper;
import co.za.dvt.airportapp.helpers.NavigationHelper;
import co.za.dvt.airportapp.helpers.NotificationHelper;
import co.za.dvt.airportapp.helpers.TransitionHelper;
import co.za.dvt.airportapp.models.AirportModel;

public class DashboardActivity extends BaseMapActivity implements DashboardView{

    @Inject
    DashboardPresenter dashboardPresenter;
    private LinearLayout airportsCarouselContainerFl;
    private RelativeLayout searchContainerLl;
    private ViewPager airportsViewPager;
    private TextView resultsTv;
    private TextView messageTv;
    private View statusBar;
    private final int AIRPORT_ZOOM = 12;
    private final int USER_ZOOM = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_activity);
        checkLocationPermissionAndContinue();
        initViews();
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerDashboardComponent.builder().appComponent(appComponent)
                .dashboardModule(new DashboardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public DashboardPresenter getPresenter() {
        return dashboardPresenter;
    }

    @Override
    protected void initViews() {
        statusBar = findViewById(R.id.flStatusBar);
        ViewGroup.LayoutParams params = statusBar.getLayoutParams();
        params.height = getStatusBarHeight();
        statusBar.setLayoutParams(params);

        airportsCarouselContainerFl = findViewById(R.id.flAirportsCarouselContainer);
        searchContainerLl = findViewById(R.id.llSearchContainer);
        airportsViewPager = findViewById(R.id.vpAirports);
        messageTv = findViewById(R.id.tvMessage);
        resultsTv = findViewById(R.id.tvResults);
    }

    public void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        showFindAirportsView();
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
    public void hideFindingAirportsDialog() {
        hideLoader();
    }

    public void showFindingAirportsDialog(String message) {
        showLoadingDialog(message);
    }

    @Override
    public void showAirportsErrorMessage(String errorMessage) {
        NotificationHelper.showErrorDialog(this, getResources().getString(R.string.error_dialog_title), errorMessage,  getResources().getString(R.string.ok));
    }

    @Override
    public void onFindAirportsClicked(View view) {
        showFindingAirportsDialog(getResources().getString(R.string.finding_airports_message));
        LatLng userCoordinates = userMarker.getPosition();
        getPresenter().findAirports(userCoordinates);
    }

    @Override
    public void onCloseAirportListClicked(View view) {
        showFindAirportsView();
    }

    @Override
    public void setDistance(int distance) {
        getPresenter().setDistance(distance);
    }

    @Override
    public void showDistanceMessage() {
        String message = getString(R.string.finding_range_message, getPresenter().getDistance());
        messageTv.setText(message);
    }

    @Override
    public void onSetDistanceClicked(View view) {
        SetDistanceFragment setDistanceFragment = SetDistanceFragment.newInstance(this);
        NotificationHelper.showFragmentDialog(this, getString(R.string.no_internet), R.layout.fragment_set_distance, setDistanceFragment);
        dialogFragment = setDistanceFragment;
    }

    @Override
    public void showFindAirportsView() {
        airportsCarouselContainerFl.setVisibility(View.GONE);
        searchContainerLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAirportsFoundView() {
        airportsCarouselContainerFl.setVisibility(View.VISIBLE);
        searchContainerLl.setVisibility(View.GONE);
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
        googleMap.clear();

        for(AirportModel airport : airports){
            String iataCode = airport.getCodeIataAirport();
            LatLng airportCoordinates = new LatLng(ConverterHelper.stringToDouble(airport.getLatitudeAirport()), ConverterHelper.stringToDouble(airport.getLongitudeAirport()));
            String airportName =  airport.getNameAirport();
            plotAirportMarker(airportCoordinates, airportName, null, iataCode);
        }

        listenForMarkerClicks();
        goToLocationZoomAnimated(airportMarkers.get(0).getPosition(), AIRPORT_ZOOM);
    }

    @Override
    public void showAirportsResultCount(String message) {
        resultsTv.setText(message);
    }

    @Override
    public void showAirportsCarousel(final List<AirportModel> airports){
        List<AirportFragment> airportFragments = new ArrayList<>();
        for(AirportModel airport : airports){
            AirportFragment airportFragment = AirportFragment.getInstance(this, airport);
            airportFragments.add(airportFragment);
        }

        AirportPagerAdapter stylistsViewPagerAdapter = new AirportPagerAdapter(this, this.getSupportFragmentManager(), airportFragments);
        stylistsViewPagerAdapter.notifyDataSetChanged();
        airportsViewPager.setAdapter(stylistsViewPagerAdapter);
        airportsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                goToLocationZoomAnimated(airportMarkers.get(position).getPosition(), AIRPORT_ZOOM);
                String message = getPresenter().getAirportsFoundMessage(airports.size(), position);
                showAirportsResultCount(message);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        String message = getPresenter().getAirportsFoundMessage(airports.size(), 0);
        showAirportsResultCount(message);
        showAirportsFoundView();
    }

    @Override
    public void goToDepartures(String iataCode, String name, String location) {
        Bundle payload = new Bundle();
        payload.putString(Constants.AIRPORT_IATACODE, iataCode);
        payload.putString(Constants.AIRPORT_NAME, name);
        payload.putString(Constants.AIRPORT_LOCATION, location);
        NavigationHelper.goToActivityWithPayload(this, FlightsActivity.class, payload, TransitionHelper.slideInActivity());
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
                            airportsCarouselContainerFl.setVisibility(View.VISIBLE);
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