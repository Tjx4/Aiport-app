package co.za.dvt.airportapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NearbyAirportsModel {
    @SerializedName("airports")
    @Expose
    private List<AirportModel> airports;

    public List<AirportModel> getAirports() {
        return airports;
    }

    public void setAirports(List<AirportModel> airports) {
        this.airports = airports;
    }
}
