package co.za.dvt.airportapp.models;

import java.util.List;

public class FlightsModel {
    private String airportName;
    private String airportLocation;
    private List<FlightModel> flights;

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportLocation() {
        return airportLocation;
    }

    public void setAirportLocation(String airportLocation) {
        this.airportLocation = airportLocation;
    }

    public List<FlightModel> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightModel> flights) {
        this.flights = flights;
    }
}