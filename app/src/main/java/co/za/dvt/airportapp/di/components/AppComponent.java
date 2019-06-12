package co.za.dvt.airportapp.di.components;

import javax.inject.Singleton;
import co.za.dvt.airportapp.MyApplication;
import co.za.dvt.airportapp.di.modules.AppModule;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MyApplication myApplication);
}