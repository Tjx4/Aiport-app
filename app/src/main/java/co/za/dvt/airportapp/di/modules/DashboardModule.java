package co.za.dvt.airportapp.di.modules;

import co.za.dvt.airportapp.features.dashboard.DashboardPresenter;
import co.za.dvt.airportapp.features.dashboard.DashboardView;
import dagger.Provides;

public class DashboardModule {
    private DashboardView dashboardView;

    public DashboardModule(DashboardView clientMapView) {
        this.dashboardView = dashboardView;
    }

    @Provides
    public DashboardView provideDashboardView() {
        return dashboardView;
    }

    @Provides
    static DashboardPresenter providePresenter(DashboardView dashboardView) {
        return new DashboardPresenter(dashboardView);
    }
}
