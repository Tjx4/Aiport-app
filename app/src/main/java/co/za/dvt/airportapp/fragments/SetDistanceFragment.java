package co.za.dvt.airportapp.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.features.dashboard.DashboardView;

public class SetDistanceFragment extends BaseDialogFragment {
    private TextView distanceTv;
    private SeekBar distanceSb;
    private DashboardView dashboardView;

    public static SetDistanceFragment newInstance(Activity context) {
        return (SetDistanceFragment)Fragment.instantiate(context, SetDistanceFragment.class.getName(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setDimAmount(0.9f);
        View parentView = super.onCreateView(inflater,  container, savedInstanceState);
        dashboardView = (DashboardView)getActivity();
        distanceTv = parentView.findViewById(R.id.tvDistance);

        int distance = dashboardView.getPresenter().getDistance();
        distanceSb = parentView.findViewById(R.id.sbDistance);
        distanceSb.setProgress(distance);
        distanceTv.setText(distance+"km");
        distanceSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                distanceTv.setText(i+"km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int distance = seekBar.getProgress();
                dashboardView.setDistance(distance);
            }
        });

        return parentView;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        dashboardView.showDistanceMessage();

    }
}
