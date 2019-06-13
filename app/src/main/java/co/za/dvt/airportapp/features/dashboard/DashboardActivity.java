package co.za.dvt.airportapp.features.dashboard;

import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import javax.inject.Inject;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.di.components.AppComponent;
import co.za.dvt.airportapp.di.components.DaggerDashboardComponent;
import co.za.dvt.airportapp.di.modules.DashboardModule;
import co.za.dvt.airportapp.features.base.activity.BaseMapActivity;
import co.za.dvt.airportapp.helpers.TransitionHelper;

public class DashboardActivity extends BaseMapActivity implements DashboardView{

    @Inject
    DashboardPresenter dashboardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_activity);
        checkLocationPermissionAndContinue();
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isNewActivity)
            return;

        overridePendingTransition(TransitionHelper.slideOutActivity()[0], TransitionHelper.slideOutActivity()[1]);
    }

    @Override
    protected void initViews() {

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
    public void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        LatLng userCordinates = new LatLng(location.getLatitude(), location.getLongitude());

        if(getPresenter().isMoved4Meters(userCordinates, userMarker.getPosition())){
            moveUserMarker(userCordinates);
        }
    }
}