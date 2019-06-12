package co.za.dvt.airportapp.di.components;

import co.za.dvt.airportapp.di.modules.DashboardModule;
import co.za.dvt.airportapp.di.scope.PerActivityScope;
import co.za.dvt.airportapp.features.dashboard.DashboardActivity;
import co.za.dvt.airportapp.features.dashboard.DashboardPresenter;
import dagger.Component;

@PerActivityScope
@Component(dependencies = AppComponent.class, modules = DashboardModule.class)
public interface DashboardComponent {
    void inject(DashboardActivity dashboardActivity);
    DashboardPresenter getMainPresenter();
}