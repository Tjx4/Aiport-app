package co.za.dvt.airportapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.features.dashboard.DashboardView;
import co.za.dvt.airportapp.models.AirportModel;

public class AirportFragment extends Fragment {
    private String name;
    private String distanceFromUser;

    public static AirportFragment getInstance(Activity activity, AirportModel airport) {
        DashboardView dashboardView = (DashboardView)activity;
        LatLng userCoordinates = dashboardView.getUserMarker().getPosition();
        LatLng airportCoordinates = new LatLng(airport.getLatitude(), airport.getLongitude());
        String distanceFrom = dashboardView.getPresenter().getDistanceFromUserMessage(userCoordinates, airportCoordinates);

        Bundle payload = new Bundle();
        payload.putString(Constants.AIRPORT_NAME, airport.getName());
        payload.putString(Constants.AIRPORT_IATACODE, airport.getIataCode());
        payload.putString(Constants.DISTANCE_FROM_USER, distanceFrom);

        return (AirportFragment)Fragment.instantiate(activity, AirportFragment.class.getName(), payload);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View parentView = inflater.inflate(R.layout.fragment_airport, container, false);

        name = getArguments().getString(Constants.AIRPORT_NAME);
        distanceFromUser = getArguments().getString(Constants.DISTANCE_FROM_USER);

        TextView airportNameTv = parentView.findViewById(R.id.tvAirportName);
        airportNameTv.setText(name);

        TextView distanceFromUserTv = parentView.findViewById(R.id.tvdistanceFromUser);
        distanceFromUserTv.setText(distanceFromUser);


        return parentView;
    }
}