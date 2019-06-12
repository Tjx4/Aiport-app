package co.za.dvt.airportapp.features.base.activity;

import co.za.dvt.airportapp.di.components.AppComponent;

public abstract class BaseAsyncActivity extends BaseActivity{

    protected abstract void setupComponent(AppComponent appComponent);
}
