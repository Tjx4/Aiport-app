package co.za.dvt.airportapp.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import com.wang.avi.AVLoadingIndicatorView;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;

public class LoadingSpinnerFragmentBase extends BaseDialogFragment {
    private TextView loadingTxt;
    private AVLoadingIndicatorView loader;

    public static LoadingSpinnerFragmentBase getInstance(Activity activity) {
        return (LoadingSpinnerFragmentBase)DialogFragment.instantiate(activity, LoadingSpinnerFragmentBase.class.getName(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setDimAmount(0.9f);

        View parentView = super.onCreateView(inflater, container, savedInstanceState);
        loader = parentView.findViewById(R.id.progressBarLoading);
        loadingTxt = parentView.findViewById(R.id.txtLoading);

        String loadingMessage = getArguments().getString(Constants.TITLE);
        loadingTxt.setText(loadingMessage);

        return parentView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {

            }
        };
    }
}
