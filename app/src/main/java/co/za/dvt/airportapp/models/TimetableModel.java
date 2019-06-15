package co.za.dvt.airportapp.models;

import java.util.List;

public class TimetableModel {
    private String nameAirport;
    private String airportLocation;

    private List<DepartureFlightsModel> departureFlights;

    public List<DepartureFlightsModel> getDepartureFlights() {
        return departureFlights;
    }

    public void setDepartureFlights(List<DepartureFlightsModel> departureFlights) {
        this.departureFlights = departureFlights;
    }

    public String getNameAirport() {
        return nameAirport;
    }

    public void setNameAirport(String nameAirport) {
        this.nameAirport = nameAirport;
    }

    public String getAirportLocation() {
        return airportLocation;
    }

    public void setAirportLocation(String airportLocation) {
        this.airportLocation = airportLocation;
    }
}