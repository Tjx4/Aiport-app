package co.za.dvt.airportapp.features.base.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import co.za.dvt.airportapp.MyApplication;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.di.interfaces.DaggerActivity;

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

    private void setransparentStatusAndBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
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
