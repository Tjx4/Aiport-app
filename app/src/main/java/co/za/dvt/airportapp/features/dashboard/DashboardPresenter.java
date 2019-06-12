package co.za.dvt.airportapp.features.dashboard;

import co.za.dvt.airportapp.features.base.presenter.BaseAsyncPresenter;

public class DashboardPresenter extends BaseAsyncPresenter {
    private DashboardView dashboardView;

    public DashboardPresenter(DashboardView dashboardView) {
        super(dashboardView);
        this.dashboardView = dashboardView;
    }
}