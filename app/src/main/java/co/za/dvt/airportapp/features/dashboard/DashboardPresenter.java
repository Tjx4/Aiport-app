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

    public void findNearbyAirports(LatLng userCoordinates, int distance) {
        airports = new ArrayList<>();
        // apiKey userCoordinates, int distance


double dlat = userCoordinates.latitude - 0.60;
double dlong = userCoordinates.longitude - 0.20;
AirportModel airport = new AirportModel();
airport.setName("Basani airport");
airport.setIataCode("BAP");
airport.setLatitude(dlat);
airport.setLongitude(dlong);
airports.add(airport);

dlat = userCoordinates.latitude - 0.50;
dlong = userCoordinates.longitude - 0;
AirportModel airport2 = new AirportModel();
airport2.setName("Basani airport");
airport2.setIataCode("BAP");
airport2.setLatitude(dlat);
airport2.setLongitude(dlong);
airports.add(airport2);


        if(airports != null && airports.size() > 0){
            dashboardView.plotAirportMarkers(airports);
        }
        else if(airports != null && airports.size() < 1){
            dashboardView.showAirportFindErrorMessage("No airports found in your area airports");
        }
        else {
            dashboardView.showAirportFindErrorMessage("Error finding airports");
        }
    }
}