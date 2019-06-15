package co.za.dvt.airportapp.features.departures;

import java.util.ArrayList;
import java.util.List;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.features.base.presenter.BaseAsyncPresenter;
import co.za.dvt.airportapp.models.AirportflightsModel;
import co.za.dvt.airportapp.models.FlightModel;

public class FlightsPresenter extends BaseAsyncPresenter {
    private FlightsView flightsView;

    public FlightsPresenter(FlightsView flightsView) {
        super(flightsView);
        this.flightsView = flightsView;
    }

    public void getMockFlights(String iataCode, final String airportName, final String airportLocation) {

        AirportflightsModel airportflightsModel = new AirportflightsModel();
        airportflightsModel.setFlights(new ArrayList<FlightModel>());

        List<FlightModel> flights = airportflightsModel.getFlights();

        FlightModel flight1 = new FlightModel();
        flight1.setAirlineName("South African Airways");
        flight1.setDepartureTime("08:30");
        flight1.setDestination("DRC");
        flight1.setFlightNumber("SAA015");
        flight1.setDeperted(false);
        flights.add(flight1);

        FlightModel flight2 = new FlightModel();
        flight2.setAirlineName("British Airways");
        flight2.setDepartureTime("12:00");
        flight2.setDestination("London");
        flight2.setFlightNumber("B52106");
        flight2.setDeperted(true);
        flights.add(flight2);

        FlightModel flight3 = new FlightModel();
        flight3.setAirlineName("Virgin");
        flight3.setDepartureTime("03:30");
        flight3.setDestination("New york");
        flight3.setFlightNumber("V000247");
        flight3.setDeperted(false);
        flights.add(flight3);

        FlightModel flight4 = new FlightModel();
        flight4.setAirlineName("Mango");
        flight4.setDepartureTime("01:00");
        flight4.setDestination("Cape town");
        flight4.setFlightNumber("M980807");
        flight4.setDeperted(false);
        flights.add(flight4);

        if(flights.size() > 0){
            flightsView.showFlights(flights, airportName, airportLocation);
        }
        else {
            flightsView.showFlightRetrieveError(context.getResources().getString(R.string.no_flights_found));
        }
    }

    public void getFlights(String iataCode, final String setAirportName, final String setAirportLocation){
        //flightsView.showFlights(flights, airportName, airportLocation);
    }

}