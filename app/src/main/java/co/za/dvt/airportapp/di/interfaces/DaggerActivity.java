package co.za.dvt.airportapp.di.interfaces;

import co.za.dvt.airportapp.di.components.AppComponent;

public interface DaggerActivity {
    void setupComponent(AppComponent appComponent);
}