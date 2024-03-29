package co.za.dvt.airportapp.features.base.activity;

import android.os.Bundle;
import android.view.MenuItem;
import co.za.dvt.airportapp.helpers.TransitionHelper;

public abstract class BaseChildActivity extends BaseAsyncActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        TransitionHelper.slideOutActivity();
        return super.onOptionsItemSelected(item);
    }
}