package co.za.dvt.airportapp.models;

import com.google.gson.annotations.SerializedName;

public class ArrivalModel {
    @SerializedName("iataCode")
    private String iataCode;
    @SerializedName("icaoCode")
    private String icaoCode;
    @SerializedName("scheduledTime")
    private String scheduledTime;

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}