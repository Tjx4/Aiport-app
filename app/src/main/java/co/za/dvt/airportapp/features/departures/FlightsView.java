package co.za.dvt.airportapp.features.departures;

import java.util.List;
import co.za.dvt.airportapp.features.base.view.BaseView;
import co.za.dvt.airportapp.models.TimetableModel;

public interface FlightsView extends BaseView {
    FlightsPresenter getPresenter();
    void showFindingFlightsDialog(String message);
    void showFlights(List<TimetableModel> flights, String airportName, String airportLocation);
    void showFlightRetrieveError(String errorMessage);
    void hideFindingFlightsDialog();
}