package co.za.dvt.airportapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.model.LatLng;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.features.dashboard.DashboardView;
import co.za.dvt.airportapp.helpers.ConverterHelper;
import co.za.dvt.airportapp.models.AirportModel;

public class AirportFragment extends Fragment {
    private String name;
    private String distanceFromUser;
    private String iataCode;

    public static AirportFragment getInstance(Activity activity, AirportModel airport) {
        DashboardView dashboardView = (DashboardView)activity;
        LatLng userCoordinates = dashboardView.getUserMarker().getPosition();
        LatLng airportCoordinates = new LatLng(ConverterHelper.stringToDouble(airport.getLatitudeAirport()), ConverterHelper.stringToDouble(airport.getLongitudeAirport()));
        String distanceFrom = dashboardView.getPresenter().getDistanceFromUserMessage(userCoordinates, airportCoordinates);

        Bundle payload = new Bundle();
        payload.putString(Constants.AIRPORT_NAME, airport.getNameAirport());
        payload.putString(Constants.AIRPORT_IATACODE, airport.getCodeIataAirport());
        payload.putString(Constants.DISTANCE_FROM_USER, distanceFrom);

        return (AirportFragment)Fragment.instantiate(activity, AirportFragment.class.getName(), payload);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View parentView = inflater.inflate(R.layout.fragment_airport, container, false);
        TextView airportNameTv = parentView.findViewById(R.id.tvAirportName);
        TextView distanceFromUserTv = parentView.findViewById(R.id.tvdistanceFromUser);
        Button departuresBtn = parentView.findViewById(R.id.btnDepartures);

        name = getArguments().getString(Constants.AIRPORT_NAME);
        distanceFromUser = getArguments().getString(Constants.DISTANCE_FROM_USER);
        iataCode = getArguments().getString(Constants.AIRPORT_IATACODE);

        airportNameTv.setText(name);
        distanceFromUserTv.setText(distanceFromUser);

        departuresBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                DashboardView dashboardView = (DashboardView)getActivity();
                dashboardView.goToDepartures(iataCode, name, "Airport Location");
            }
        });

        return parentView;
    }
}