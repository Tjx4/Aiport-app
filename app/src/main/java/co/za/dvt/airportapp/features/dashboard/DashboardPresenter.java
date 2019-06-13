package co.za.dvt.airportapp.features.dashboard;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
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
        double distanceInMeters = getDistanceInKm(userCoordinates, airportCoordinates);
        return  distanceInMeters+"km from you";
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
airport2.setName("Basani airport");
airport2.setIataCode("BAP");
airport2.setLatitude(dlat);
airport2.setLongitude(dlong);
airports.add(airport2);

        if(airports != null && airports.size() > 0){
            showAirportsAndPlotMarkers();
        }
        else if(airports != null && airports.size() < 1){
            dashboardView.showAirportFindErrorMessage("No airports found in your area airports");
        }
        else {
            dashboardView.showAirportFindErrorMessage("Error finding airports");
        }
    }

    private void showAirportsAndPlotMarkers(){
        dashboardView.plotAirportMarkers(airports);
        dashboardView.showAirportsCarousel(airports);
    }
}