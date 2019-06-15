package co.za.dvt.airportapp.models;

import java.util.List;

public class AirportFlightsModel {
    private String nameAirport;
    private String nameCountry;
    List<DepartureFlightsModel> flights;

    public List<DepartureFlightsModel> getFlights() {
        return flights;
    }

    public void setFlights(List<DepartureFlightsModel> flights) {
        this.flights = flights;
    }

    public String getNameAirport() {
        return nameAirport;
    }

    public void setNameAirport(String nameAirport) {
        this.nameAirport = nameAirport;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }
}