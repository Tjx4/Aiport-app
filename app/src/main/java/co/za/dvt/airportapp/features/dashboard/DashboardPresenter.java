package co.za.dvt.airportapp.features.dashboard;

import co.za.dvt.airportapp.features.base.presenter.BaseMapPresenter;

public class DashboardPresenter extends BaseMapPresenter {
    private DashboardView dashboardView;

    public DashboardPresenter(DashboardView dashboardView) {
        super(dashboardView);
        this.dashboardView = dashboardView;
    }
}