package co.za.dvt.airportapp.features.base.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.fragments.BaseDialogFragment;
import co.za.dvt.airportapp.fragments.LoadingSpinnerFragmentBase;
import co.za.dvt.airportapp.fragments.NoInternetFragmentBase;
import co.za.dvt.airportapp.helpers.NotificationHelper;

public abstract class BaseAsyncActivity extends BaseActivity{
    private BaseDialogFragment dialogFragment;
    private LoadingSpinnerFragmentBase loadingDialogFragment;

    protected void hideLoader(){
        if(loadingDialogFragment != null)
            loadingDialogFragment.dismiss();
    }

    protected void showLoadingDialog(String loadingMessage) {
        loadingDialogFragment = LoadingSpinnerFragmentBase.getInstance(this);
        NotificationHelper.showFragmentDialog(this, loadingMessage, R.layout.fragment_loading_spinner, loadingDialogFragment);
    }

    public void onDialogDismissed(DialogInterface dialogInterface) {}

    protected void onResume() {
        super.onResume();
        if(!isNetworkAvailable()){
            NoInternetFragmentBase noInternetFragmentBase = NoInternetFragmentBase.newInstance(this, null);
            NotificationHelper.showFragmentDialog(this, getString(R.string.no_internet), R.layout.fragment_no_internet, noInternetFragmentBase);
            dialogFragment = noInternetFragmentBase;
        }
        else {
            hideDialog();
        }
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected void hideDialog() {
        if(dialogFragment != null) {
            dialogFragment.dismiss();
            dialogFragment = null;
        }
    }
}