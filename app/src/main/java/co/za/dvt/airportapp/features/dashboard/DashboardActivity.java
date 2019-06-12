package co.za.dvt.airportapp.features.dashboard;

import android.os.Bundle;
import android.view.KeyEvent;

import javax.inject.Inject;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.di.components.AppComponent;
import co.za.dvt.airportapp.di.components.DaggerDashboardComponent;
import co.za.dvt.airportapp.di.interfaces.DaggerActivity;
import co.za.dvt.airportapp.di.modules.DashboardModule;
import co.za.dvt.airportapp.features.base.activity.BaseAsyncActivity;

public class DashboardActivity extends BaseAsyncActivity implements DashboardView, DaggerActivity {

    @Inject
    DashboardPresenter dashboardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_activity);
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerDashboardComponent.builder().appComponent(appComponent)
                .dashboardModule(new DashboardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public DashboardPresenter getPresenter() {
        return dashboardPresenter;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}