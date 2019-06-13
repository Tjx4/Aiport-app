package co.za.dvt.airportapp.features.dashboard;

import android.view.View;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import co.za.dvt.airportapp.features.base.view.BaseView;
import co.za.dvt.airportapp.models.AirportModel;

public interface DashboardView extends BaseView {
    DashboardPresenter getPresenter();
    void onFindAirportsClicked(View view);
    void plotUserMarker(LatLng latLng, String title, String snippet);
    void plotAirportMarker(LatLng latLng, String title, String snippet, String tag);
    void plotAirportMarkers(List<AirportModel> airports);
    void showAirportFindErrorMessage(String errorMessage);
}