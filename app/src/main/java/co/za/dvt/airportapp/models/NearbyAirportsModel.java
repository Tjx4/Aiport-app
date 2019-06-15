package co.za.dvt.airportapp.models;

import java.util.List;

public class NearbyAirportsModel {
    private List<AirportModel> airports;

    public List<AirportModel> getAirports() {
        return airports;
    }

    public void setAirports(List<AirportModel> airports) {
        this.airports = airports;
    }
}