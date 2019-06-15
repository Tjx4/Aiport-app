package co.za.dvt.airportapp.models;

import java.util.List;

public class AirportFlightsModel {
    private String nameAirport;
    private String nameCountry;
    List<TimetableModel> flights;

    public List<TimetableModel> getFlights() {
        return flights;
    }

    public void setFlights(List<TimetableModel> flights) {
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