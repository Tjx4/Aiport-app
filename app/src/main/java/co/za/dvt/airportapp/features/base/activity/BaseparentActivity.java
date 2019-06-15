package co.za.dvt.airportapp.features.base.activity;

import co.za.dvt.airportapp.helpers.TransitionHelper;

public abstract class BaseparentActivity extends BaseAsyncActivity {

    @Override
    protected void onResume() {
        super.onResume();
        if(isNewActivity)
            return;

        overridePendingTransition(TransitionHelper.slideOutActivity()[0], TransitionHelper.slideOutActivity()[1]);
    }
}