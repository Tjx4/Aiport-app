package co.za.dvt.airportapp.features.departures;

import java.util.List;
import co.za.dvt.airportapp.features.base.view.BaseView;
import co.za.dvt.airportapp.models.FlightModel;

public interface FlightsView extends BaseView {
    FlightsPresenter getPresenter();
    void showFlights(List<FlightModel> flights, String airportName, String airportLocation);
    void showFlightRetrieveError(String errorMessage);
}