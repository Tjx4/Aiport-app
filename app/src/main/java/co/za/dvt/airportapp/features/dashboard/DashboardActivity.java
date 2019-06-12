package co.za.dvt.airportapp.features.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import javax.inject.Inject;
import co.za.dvt.airportapp.R;

public class DashboardActivity extends AppCompatActivity implements DashboardView{

    @Inject
    DashboardPresenter dashboardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_activity);
    }

    @Override
    public DashboardPresenter getPresenter() {
        return dashboardPresenter;
    }
}