package co.za.dvt.airportapp.features.base.presenter;

import com.google.android.gms.maps.model.LatLng;
import co.za.dvt.airportapp.features.base.view.BaseView;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public abstract class BaseMapPresenter extends BaseAsyncPresenter{

    public BaseMapPresenter(BaseView baseView) {
        super(baseView);
    }
    private static double PI_RAD = Math.PI / 180.0;

    public double greatCircleInKilometers(double lat1, double long1, double lat2, double long2) {
        double phi1 = lat1 * PI_RAD;
        double phi2 = lat2 * PI_RAD;
        double lam1 = long1 * PI_RAD;
        double lam2 = long2 * PI_RAD;

        return 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
    }

    public double greatCircleInMeters(LatLng latLng1, LatLng latLng2) {
        return greatCircleInKilometers(latLng1.latitude, latLng1.longitude, latLng2.latitude,
                latLng2.longitude) * 1000;
    }

    public double getDistanceInMeters(double lat1, double lon1, double lat2, double lon2) {
        LatLng latLng1 = new LatLng(lat1, lon1);
        LatLng latLng2 = new LatLng(lat2, lon2);
        double distanceInMeters = greatCircleInMeters(latLng1, latLng2);

        return distanceInMeters;
    }

    public double getDistanceInMeters(LatLng latLng1, LatLng latLng2) {
        double distanceTo = getDistanceInMeters(latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude);
        return distanceTo;
    }

    public double getDistanceInKm(LatLng latLng1, LatLng latLng2) {
        double distanceInMiters = getDistanceInMeters(latLng1, latLng2);
        return Math.round(distanceInMiters / 1000);
    }

    public boolean isMoved4Meters(LatLng userCordinates, LatLng userLastCoordinates){
        double distanceMoved = getDistanceInMeters(userCordinates, userLastCoordinates);
        boolean moved4Meters = distanceMoved > 3;
        return moved4Meters;
    }
}
