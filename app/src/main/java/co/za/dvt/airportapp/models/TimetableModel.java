package co.za.dvt.airportapp.models;

import co.za.dvt.airportapp.di.modules.FlightsModule;

public class TimetableModel {
    private String type;
    private String status;
    private DepartureModel dparture;
    private AirlineModel airline;
    private FlightsModule flight;
}