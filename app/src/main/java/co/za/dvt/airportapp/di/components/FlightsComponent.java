package co.za.dvt.airportapp.di.components;

import co.za.dvt.airportapp.di.modules.FlightsModule;
import co.za.dvt.airportapp.di.scope.PerActivityScope;
import co.za.dvt.airportapp.features.departures.FlightsActivity;
import co.za.dvt.airportapp.features.departures.FlightsPresenter;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = FlightsModule.class)
public interface FlightsComponent {
    void inject(FlightsActivity flightsActivity);
    FlightsPresenter getMainPresenter();
}