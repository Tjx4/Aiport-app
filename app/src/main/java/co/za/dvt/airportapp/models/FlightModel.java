package co.za.dvt.airportapp.models;

public class FlightModel {
    private String airlineName;
    private String departureTime;
    private String flightNumber;
    private String Destination;
    private boolean deperted;

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public boolean isDeperted() {
        return deperted;
    }

    public void setDeperted(boolean deperted) {
        this.deperted = deperted;
    }
}