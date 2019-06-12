package co.za.dvt.airportapp.helpers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PermissionsHelper {
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private static boolean isPermissionGranted(AppCompatActivity activity, String permission) {
        if(isbellowMashMellow())
            return true;

        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isAccesFimeLocationPermissionGranted(AppCompatActivity activity) {
        return isPermissionGranted(activity, ACCESS_FINE_LOCATION);
    }

    public static void requestAccesFimeLocationPermission(AppCompatActivity activity) {
        if(isbellowMashMellow() )
            return;

        ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private static boolean isbellowMashMellow() {
        return Build.VERSION.SDK_INT < 23;
    }
}
