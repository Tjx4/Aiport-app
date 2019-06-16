package co.za.dvt.airportapp.features.dashboard;

import android.view.View;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import java.util.List;
import co.za.dvt.airportapp.features.base.view.BaseView;
import co.za.dvt.airportapp.models.AirportModel;

public interface DashboardView extends BaseView {
    DashboardPresenter getPresenter();
    void plotUserMarker(LatLng latLng, String title, String snippet);
    void showFindAirportsView();
    void onFindAirportsClicked(View view);
    void onCloseAirportListClicked(View view);
    void setDistance(int distance);
    void onSetDistanceClicked(View view);
    void showAirportsFoundView();
    void showFindingAirportsDialog(String message);
    void hideFindingAirportsDialog();
    void showAirportsResultCount(String message);
    void showAirportsErrorMessage(String errorMessage);
    Marker getUserMarker();
    void plotAirportMarker(LatLng latLng, String title, String snippet, String tag);
    void plotAirportMarkers(List<AirportModel> airports);
    void showAirportsCarousel(List<AirportModel> airport);
    void goToDepartures(String iataCode, String name, String location);
}