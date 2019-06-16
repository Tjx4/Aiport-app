package co.za.dvt.airportapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoGPSFragment extends BaseDialogFragment {

    public static NoGPSFragment newInstance(Activity context, Bundle bundle) {
        return (NoGPSFragment)Fragment.instantiate(context, NoGPSFragment.class.getName(), bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setDimAmount(0.9f);

        View parentView = super.onCreateView( inflater,  container, savedInstanceState);

        return parentView;
    }
}
