package co.za.dvt.airportapp.features.base.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.fragments.BaseDialogFragment;
import co.za.dvt.airportapp.fragments.LoadingSpinnerFragmentBase;
import co.za.dvt.airportapp.fragments.NoInternetFragment;
import co.za.dvt.airportapp.helpers.NotificationHelper;

public abstract class BaseAsyncActivity extends BaseActivity{
    protected BaseDialogFragment dialogFragment;
    protected LoadingSpinnerFragmentBase loadingDialogFragment;

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
            NoInternetFragment noInternetFragment = NoInternetFragment.newInstance(this, null);
            NotificationHelper.showFragmentDialog(this, getString(R.string.no_internet), R.layout.fragment_no_internet, noInternetFragment);
            dialogFragment = noInternetFragment;
        }
        else {
            hideDialog();
        }
    }

    protected boolean isGPSOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            return lm.isLocationEnabled();
        } else {
            int mode = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF);
            return  (mode != Settings.Secure.LOCATION_MODE_OFF);
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