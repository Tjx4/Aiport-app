package co.za.dvt.airportapp.features.departures;

import java.util.HashMap;
import java.util.List;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.features.base.presenter.BaseAsyncPresenter;
import co.za.dvt.airportapp.models.AirportFlightsModel;
import co.za.dvt.airportapp.models.TimetableModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightsPresenter extends BaseAsyncPresenter {
    private FlightsView flightsView;
    private AirportFlightsModel airportFlights;

    public FlightsPresenter(FlightsView flightsView) {
        super(flightsView);
        this.flightsView = flightsView;
    }

    public void getFlights(String iataCode, String type){
        if(isBusy)
            return;

        isBusy = true;

        HashMap<String, String> payload = new HashMap<>();
        payload.put(Constants.IATACODE, iataCode);
        payload.put(Constants.TYPE, type);

        Call<List<TimetableModel>> call1 = retrofitHelper.getFlights(apiKey, payload);
        call1.enqueue(new Callback<List<TimetableModel>>() {
            @Override
            public void onResponse(Call<List<TimetableModel>> call, Response<List<TimetableModel>> response) {
                if(response.isSuccessful()){
                    airportFlights = new AirportFlightsModel();
                    airportFlights.setFlights(response.body());

                    if(airportFlights.getFlights().size() > 0){
                        showAirportFlights(airportFlights.getFlights(), airportFlights.getNameAirport(),  airportFlights.getNameCountry());
                    }
                    else{
                        showFlightsError(context.getResources().getString(R.string.no_flights_message));
                    }
                }
                else{
                    showFlightsError(context.getResources().getString(R.string.error_finding_flights));
                }
                isBusy = false;
            }

            @Override
            public void onFailure(Call<List<TimetableModel>> call, Throwable t) {
                showFlightsError(context.getResources().getString(R.string.error_finding_flights));
                isBusy = false;
            }
        });
    }

    private void showAirportFlights(List<TimetableModel> flights, String airportName, String airportLocation){
        flightsView.hideFindingFlightsDialog();
        flightsView.showFlights(flights, airportName, airportLocation);
    }

    private void showFlightsError(String errorMessage){
        flightsView.hideFindingFlightsDialog();
        flightsView.showFlightRetrieveError(errorMessage);
    }
}