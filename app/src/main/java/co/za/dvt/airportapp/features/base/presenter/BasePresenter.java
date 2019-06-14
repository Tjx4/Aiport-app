package co.za.dvt.airportapp.features.base.presenter;

import android.content.Context;
import co.za.dvt.airportapp.features.base.view.BaseView;

public abstract class BasePresenter {
    protected Context context;
    public BasePresenter(BaseView baseView) {
        context = (Context)baseView;
    }
}