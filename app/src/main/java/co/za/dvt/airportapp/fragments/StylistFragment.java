package co.za.dvt.airportapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.customViews.CustomLinearLayout;

public abstract class StylistFragment extends Fragment {

    public static Fragment getInstance(Activity activity) {
        return Fragment.instantiate(activity, StylistFragment.class.getName(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null)
            return null;

        CustomLinearLayout parentView = (CustomLinearLayout) inflater.inflate(R.layout.fragment_stylist, container, false);
        CustomLinearLayout customLinearLayout  = parentView.findViewById(R.id.item_root);

        return parentView;
    }

}
