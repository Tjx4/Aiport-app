package co.za.dvt.airportapp.models;

import com.google.gson.annotations.SerializedName;

public class DepartureModel {
    @SerializedName("iataCode")
    private String iataCode;
    @SerializedName("icaoCode")
    private String icaoCode;
    @SerializedName("terminal")
    private String terminal;
    @SerializedName("gate")
    private String gate;
    @SerializedName("scheduledTime")
    private String scheduledTime;
    @SerializedName("estimatedTime")
    private String estimatedTime;
    @SerializedName("actualTime")
    private String actualTime;
    @SerializedName("estimatedRunway")
    private String estimatedRunway;
    @SerializedName("actualRunway")
    private String actualRunway;

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

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getEstimatedRunway() {
        return estimatedRunway;
    }

    public void setEstimatedRunway(String estimatedRunway) {
        this.estimatedRunway = estimatedRunway;
    }

    public String getActualRunway() {
        return actualRunway;
    }

    public void setActualRunway(String actualRunway) {
        this.actualRunway = actualRunway;
    }
}