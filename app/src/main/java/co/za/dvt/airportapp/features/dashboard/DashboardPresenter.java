package co.za.dvt.airportapp.features.dashboard;

import com.google.android.gms.maps.model.LatLng;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.features.base.presenter.BaseMapPresenter;
import co.za.dvt.airportapp.helpers.ConverterHelper;
import co.za.dvt.airportapp.models.AirportModel;
import co.za.dvt.airportapp.models.NearbyAirportsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardPresenter extends BaseMapPresenter {
    private DashboardView dashboardView;
    private NearbyAirportsModel nearbyAirportsModel;

    public DashboardPresenter(DashboardView dashboardView) {
        super(dashboardView);
        this.dashboardView = dashboardView;
    }

    public void findAirports(final LatLng userCoordinates, String distance) {
        if(isBusy)
            return;

        isBusy = true;

        HashMap<String, String> payload = new HashMap<>();
        payload.put(Constants.LAT, String.valueOf(userCoordinates.latitude));
        payload.put(Constants.LNG, String.valueOf(userCoordinates.longitude));
        payload.put(Constants.DISTANCE, distance);

        Call<List<AirportModel>> call1 = retrofitHelper.getNearbyAirports(apiKey, payload);
        call1.enqueue(new Callback<List<AirportModel>>() {
            @Override
            public void onResponse(Call<List<AirportModel>> call, Response<List<AirportModel>> response) {
                if(response.isSuccessful()){
                    nearbyAirportsModel = new NearbyAirportsModel();
                    nearbyAirportsModel.setAirports(response.body());

                    if(nearbyAirportsModel.getAirports().size() > 0){
                        sortAirportsByDistance(nearbyAirportsModel.getAirports());
                        plotMarkersAndShowAirports(userCoordinates, nearbyAirportsModel.getAirports());
                    }
                    else{
                        showAirportsError(context.getResources().getString(R.string.no_airports_message));
                    }
                }
                else{
                    showAirportsError(context.getResources().getString(R.string.error_finding_airports));
                }
                isBusy = false;
            }

            @Override
            public void onFailure(Call<List<AirportModel>> call, Throwable t) {
                showAirportsError(context.getResources().getString(R.string.error_finding_airports));
                isBusy = false;
            }
        });
    }

    public void plotMarkersAndShowAirports(LatLng userCoordinates, List<AirportModel> airports){
        dashboardView.hideFindingAirportsDialog();
        dashboardView.plotAirportMarkers(airports);
        dashboardView.plotUserMarker(userCoordinates, context.getResources().getString(R.string.you), context.getResources().getString(R.string.user_location_message));
        dashboardView.showAirportsCarousel(airports);
    }

    public void showAirportsError(String errorMessage){
        dashboardView.hideFindingAirportsDialog();
        dashboardView.showAirportsErrorMessage(errorMessage);
    }

    public String getAirportsFoundMessage(int total, int currentPosition){
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

        NumberFormat formatter = new DecimalFormat("#0");
        String distanceMessage = formatter.format(distance)+""+unit+" away";
        return  distanceMessage;
    }

    protected List<AirportModel> sortAirportsByDistance(final List<AirportModel> airports) {

        Collections.sort(airports, new Comparator<AirportModel>() {
            @Override
            public int compare(AirportModel airport1, AirportModel airport2) {
                double airport1Distance =  ConverterHelper.stringToDouble(airport1.getDistance());
                double airport2Distance =  ConverterHelper.stringToDouble(airport2.getDistance());
                int ans = (int)Math.round(airport1Distance - airport2Distance);

                return ans;
            }
        });

        return airports;
    }

}