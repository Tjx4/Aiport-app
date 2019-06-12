package co.za.dvt.airportapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import co.za.dvt.airportapp.constants.Constants;

public class NavigationHelper {

    private static void goToActivity(Context activityFrom, Class activityTo, int[] transitionAnimation, Bundle payload) {
        Intent intent = new Intent(activityFrom, activityTo);

        Bundle fullPayload = (payload == null) ? new Bundle() : payload;
        fullPayload.putIntArray(Constants.ACTIVITY_TRANSITION, transitionAnimation);

        intent.putExtra(Constants.PAYLOAD_KEY, fullPayload);
        activityFrom.startActivity(intent);
    }

    public static void goToActivityWithNoPayload(Context activityFrom, Class activity, int[]  transitionAnimation) {
        goToActivity(activityFrom, activity, transitionAnimation, null);
    }

    public static void goToActivityWithPayload(Context activityFrom, Class activity, Bundle payload, int[]  transitionAnimation) {
        goToActivity(activityFrom, activity, transitionAnimation, payload);
    }

}