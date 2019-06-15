package co.za.dvt.airportapp.di.modules;

import co.za.dvt.airportapp.features.departures.FlightsPresenter;
import co.za.dvt.airportapp.features.departures.FlightsView;
import dagger.Module;
import dagger.Provides;

@Module
public class FlightsModule {
    private FlightsView flightsView;

    public FlightsModule(FlightsView flightsView) {
        this.flightsView = flightsView;
    }

    @Provides
    public FlightsView provideFlightsView() {
        return flightsView;
    }

    @Provides
    static FlightsPresenter providePresenter(FlightsView flightsView) {
        return new FlightsPresenter(flightsView);
    }
}