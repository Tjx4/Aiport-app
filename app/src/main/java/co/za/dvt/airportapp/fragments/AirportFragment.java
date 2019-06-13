package co.za.dvt.airportapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.models.AirportModel;

public  class AirportFragment extends Fragment {

    private String name;

    public static Fragment getInstance(Activity activity, AirportModel airport) {
        String distanceFrom = airport.getLatitude()+" "+airport.getLongitude();

        Bundle payload = new Bundle();
        payload.putString(Constants.AIRPORT_NAME, airport.getName());
        payload.putString(Constants.AIRPORT_IATACODE, airport.getIataCode());
        payload.putString(Constants.DISTANCE_FROM_USER, distanceFrom);

        return Fragment.instantiate(activity, AirportFragment.class.getName(), payload);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View parentView = inflater.inflate(R.layout.fragment_airport, container, false);

        name = getArguments().getString(Constants.AIRPORT_NAME);

        TextView airportNameTv = parentView.findViewById(R.id.tvAirportName);
        airportNameTv.setText(name);

        return parentView;
    }
}