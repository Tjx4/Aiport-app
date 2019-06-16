package co.za.dvt.airportapp.features.departures;

import java.util.HashMap;
import java.util.List;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.features.base.presenter.BaseAsyncPresenter;
import co.za.dvt.airportapp.models.DepartureFlightsModel;
import co.za.dvt.airportapp.models.TimetableModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightsPresenter extends BaseAsyncPresenter {
    private FlightsView flightsView;
    private TimetableModel timetableModel;

    public FlightsPresenter(FlightsView flightsView) {
        super(flightsView);
        this.flightsView = flightsView;
    }

    public void getFlights(String iataCode, String type, String airPortName, String airPortLocation){
        if(isBusy)
            return;

        isBusy = true;

        timetableModel = new TimetableModel();
        timetableModel.setNameAirport(airPortName);
        timetableModel.setAirportLocation(airPortLocation);

        HashMap<String, String> payload = new HashMap<>();
        payload.put(Constants.IATACODE, iataCode);
        payload.put(Constants.TYPE, type);

        Call<List<DepartureFlightsModel>> call1 = retrofitHelper.getFlights(apiKey, payload);
        call1.enqueue(new Callback<List<DepartureFlightsModel>>() {
            @Override
            public void onResponse(Call<List<DepartureFlightsModel>> call, Response<List<DepartureFlightsModel>> response) {
                if(response.isSuccessful()){
                    timetableModel.setDepartureFlights(response.body());

                    if(timetableModel.getDepartureFlights().size() > 0){
                        showAirportFlights(timetableModel.getDepartureFlights(), timetableModel.getNameAirport(), timetableModel.getAirportLocation());
                    }
                    else{
                        showFlightsErrorText(context.getResources().getString(R.string.no_flights_message));
                    }
                }
                else{
                    showFlightsErrorDialog(context.getResources().getString(R.string.error_finding_flights));
                }
                isBusy = false;
            }

            @Override
            public void onFailure(Call<List<DepartureFlightsModel>> call, Throwable t) {
                showFlightsErrorDialog(context.getResources().getString(R.string.error_finding_flights));
                isBusy = false;
            }
        });
    }

    private void showAirportFlights(List<DepartureFlightsModel> flights, String airportName, String airportLocation){
        flightsView.hideFindingFlightsDialog();
        flightsView.showFlights(flights, airportName, airportLocation);
    }

    private void showFlightsErrorDialog(String errorMessage){
        flightsView.hideFindingFlightsDialog();
        flightsView.showNoFlightsFoundErrorDialog(errorMessage);
    }

    private void showFlightsErrorText(String errorMessage){
        flightsView.hideFindingFlightsDialog();
        flightsView.showFlightRetrieveErrorText(errorMessage);
    }
}