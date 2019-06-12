package co.za.dvt.airportapp.features.splash;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import co.za.dvt.airportapp.features.dashboard.DashboardActivity;
import co.za.dvt.airportapp.helpers.NavigationHelper;
import co.za.dvt.airportapp.helpers.TransitionHelper;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationHelper.goToActivityWithNoPayload(this, DashboardActivity.class, TransitionHelper.fadeInActivity());
    }
}
