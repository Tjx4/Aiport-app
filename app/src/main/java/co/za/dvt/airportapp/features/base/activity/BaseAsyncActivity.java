package co.za.dvt.airportapp.features.base.activity;

import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.fragments.LoadingSpinnerFragmentBase;
import co.za.dvt.airportapp.helpers.NotificationHelper;

public abstract class BaseAsyncActivity extends BaseActivity{
    private LoadingSpinnerFragmentBase loadingDialogFragment;

    protected void hideLoader(){
        if(loadingDialogFragment != null)
            loadingDialogFragment.dismiss();
    }

    protected void showLoadingDialog(String loadingMessage) {
        loadingDialogFragment = new LoadingSpinnerFragmentBase();
        NotificationHelper.showFragmentDialog(this, loadingMessage, R.layout.fragment_loading_spinner, loadingDialogFragment);
    }
}