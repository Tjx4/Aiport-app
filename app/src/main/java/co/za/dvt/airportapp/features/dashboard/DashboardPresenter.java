package co.za.dvt.airportapp.features.dashboard;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import co.za.dvt.airportapp.features.base.presenter.BaseMapPresenter;
import co.za.dvt.airportapp.models.AirportModel;

public class DashboardPresenter extends BaseMapPresenter {
    private DashboardView dashboardView;
    private List<AirportModel> airports;

    public DashboardPresenter(DashboardView dashboardView) {
        super(dashboardView);
        this.dashboardView = dashboardView;
    }

    public String getDistanceFromUserMessage(LatLng userCoordinates, LatLng airportCoordinates) {

        double distance = getDistanceInMeters(userCoordinates, airportCoordinates);
        String unit = "meters";

        if(distance >= 1000) {
            distance = getDistanceInKm(userCoordinates, airportCoordinates);
            unit = "Km";
        }

        distance = Math.round(distance);
        String distanceMessage = distance+""+unit+" away";

        return  distanceMessage;
    }

    public void findNearbyAirports(LatLng userCoordinates, int distance) {
        airports = new ArrayList<>();
        // apiKey userCoordinates, int distance


double dlat = userCoordinates.latitude + 0.1f;
double dlong = userCoordinates.longitude + 0.2f;
AirportModel airport = new AirportModel();
airport.setName("Basani airport");
airport.setIataCode("BAP");
airport.setLatitude(dlat);
airport.setLongitude(dlong);
airports.add(airport);

dlat = userCoordinates.latitude + 0.1f;
dlong = userCoordinates.longitude;
AirportModel airport2 = new AirportModel();
airport2.setName("Botitshepo airport");
airport2.setIataCode("BTA");
airport2.setLatitude(dlat);
airport2.setLongitude(dlong);
airports.add(airport2);

dlat = -25.753358;
dlong = 28.1274063;
AirportModel airport3 = new AirportModel();
airport3.setName("Musa airport");
airport3.setIataCode("MSA");
airport3.setLatitude(dlat);
airport3.setLongitude(dlong);
airports.add(airport3);

        if(airports != null && airports.size() > 0){
            sortAirportsByDistance(airports, userCoordinates);
            showAirportsAndPlotMarkers();
        }
        else if(airports != null && airports.size() < 1){
            dashboardView.hideDialogAndshowAirportFindErrorMessage("No airports found in your area airports");
        }
        else {
            dashboardView.hideDialogAndshowAirportFindErrorMessage("Error finding airports");
        }
    }

    public void showAirportsAndPlotMarkers(){
        dashboardView.hideFindingAirportsDialog();
        dashboardView.plotAirportMarkers(airports);
        dashboardView.showAirportsCarousel(airports);
    }

    public String getResutsMessage(int total, int currentPosition){
        int airportNumber = ++currentPosition;
        String message = "1 airport found";

        if(total > 1){
            message = airportNumber+" of "+total+" airports found";
        }

        return message;
    }

    protected List<AirportModel> sortAirportsByDistance(final List<AirportModel> stylists, final LatLng userPosition) {
        Collections.sort(stylists, new Comparator<AirportModel>() {
            @Override
            public int compare(AirportModel airport1, AirportModel airport2) {
                LatLng airport1Position = new LatLng(airport1.getLatitude(), airport1.getLongitude());
                LatLng airport2Position = new LatLng(airport2.getLatitude(), airport2.getLongitude());

                double st1Distance =  getDistanceInKm(userPosition, airport1Position);
                double st2Distance =  getDistanceInKm(userPosition, airport2Position);
                int ans = (int)Math.round(st1Distance - st2Distance);

                return ans;
            }
        });

        List<AirportModel> sortedList = stylists;
        return sortedList;
    }
}