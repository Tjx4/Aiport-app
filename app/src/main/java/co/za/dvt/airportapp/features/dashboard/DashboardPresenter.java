package co.za.dvt.airportapp.features.dashboard;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.features.base.presenter.BaseMapPresenter;
import co.za.dvt.airportapp.models.AirportModel;
import co.za.dvt.airportapp.models.NearbyAirpoprtsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardPresenter extends BaseMapPresenter {
    private DashboardView dashboardView;
    private List<AirportModel> airports;

    public DashboardPresenter(DashboardView dashboardView) {
        super(dashboardView);
        this.dashboardView = dashboardView;
    }

    public void findAirports(final LatLng userCoordinates, int distance) {
        if(isBusy)
            return;

        airports = new ArrayList<>();

        HashMap<String, String> payload = new HashMap<>();
        payload.put(Constants.LAT, String.valueOf(userCoordinates.latitude));
        payload.put(Constants.LNG, String.valueOf(userCoordinates.longitude));
        payload.put(Constants.DISTANCE, String.valueOf(distance));

        Call<NearbyAirpoprtsModel> call1 = retrofitHelper.getNearbyAirports(apiKey, payload);
        call1.enqueue(new Callback<NearbyAirpoprtsModel>() {
            @Override
            public void onResponse(Call<NearbyAirpoprtsModel> call, Response<NearbyAirpoprtsModel> response) {
                if(response.isSuccessful()){
                    NearbyAirpoprtsModel nearbyAirpoprtsModel = response.body();

                    if(nearbyAirpoprtsModel.getAirports().size() > 0){
                        sortAirportsByDistance(nearbyAirpoprtsModel.getAirports(), userCoordinates);
                        plotMarkersAndShowAirports(userCoordinates);
                        airports = nearbyAirpoprtsModel.getAirports();
                    }
                    else{
                        dashboardView.hideDialogAndshowAirportFindErrorMessage(context.getResources().getString(R.string.no_airports_message));
                    }
                }
                else{
                    dashboardView.hideDialogAndshowAirportFindErrorMessage(context.getResources().getString(R.string.error_finding_airports));
                }
                isBusy = false;
            }

            @Override
            public void onFailure(Call<NearbyAirpoprtsModel> call, Throwable t) {
                dashboardView.hideDialogAndshowAirportFindErrorMessage(context.getResources().getString(R.string.error_finding_airports));
                isBusy = false;
            }
        });
    }

    public void findMockAirports(LatLng userCoordinates, int distance) {
        airports = new ArrayList<>();


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
            plotMarkersAndShowAirports(userCoordinates);
        }
        else if(airports != null && airports.size() < 1){
            dashboardView.hideDialogAndshowAirportFindErrorMessage(context.getResources().getString(R.string.no_airports_message));
        }
        else {
            dashboardView.hideDialogAndshowAirportFindErrorMessage(context.getResources().getString(R.string.error_finding_airports));
        }
    }

    public void plotMarkersAndShowAirports(LatLng userCoordinates){
        dashboardView.hideFindingAirportsDialog();
        dashboardView.plotAirportMarkers(airports);
        dashboardView.showAirportsCarousel(airports);
        dashboardView.plotUserMarker(userCoordinates, context.getResources().getString(R.string.you), context.getResources().getString(R.string.user_location_message));

    }

    public String getResutsMessage(int total, int currentPosition){
        int airportNumber = ++currentPosition;
        String message = "1 airport found";

        if(total > 1){
            message = airportNumber+" of "+total+" airports found";
        }

        return message;
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