package co.za.dvt.airportapp.features.base.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import co.za.dvt.airportapp.MyApplication;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.di.interfaces.DaggerActivity;
import co.za.dvt.airportapp.helpers.TranslucentStatusBarHelper;

public abstract class BaseActivity extends AppCompatActivity implements DaggerActivity {

    protected boolean isNewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNewActivity = true;
        setransparentStatusAndBar();
        getActivityTransition();
        setupComponent(MyApplication.get(this).component());
    }

    @Override
    protected void onPause() {
        super.onPause();
        isNewActivity = false;
    }

    protected void setransparentStatusAndBar() {
        TranslucentStatusBarHelper.setTranslucent(this, TranslucentStatusBarHelper.DEFAULT_STATUS_BAR_ALPHA);
    }

    protected abstract void initViews();

    private void getActivityTransition() {
        try {
            int[] activityTransition = getIntent().getBundleExtra(Constants.PAYLOAD_KEY).getIntArray(Constants.ACTIVITY_TRANSITION);
            overridePendingTransition(activityTransition[0], activityTransition[1]);
        } catch (Exception e) {
        }
    }
}
