package co.za.dvt.airportapp.models;

public class DepartureFlightsModel {
    private String type;
    private String status;
    private DepartureModel departure;
    private ArrivalModel arrival;
    private AirlineModel airline;
    private FlightsModel flight;
    private String destination;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DepartureModel getDeparture() {
        return departure;
    }

    public void setDeparture(DepartureModel departure) {
        this.departure = departure;
    }

    public AirlineModel getAirline() {
        return airline;
    }

    public void setAirline(AirlineModel airline) {
        this.airline = airline;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public FlightsModel getFlight() {
        return flight;
    }

    public void setFlight(FlightsModel flight) {
        this.flight = flight;
    }

    public ArrivalModel getArrival() {
        return arrival;
    }

    public void setArrival(ArrivalModel arrival) {
        this.arrival = arrival;
    }
}