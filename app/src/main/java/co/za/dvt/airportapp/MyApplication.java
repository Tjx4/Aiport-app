package co.za.dvt.airportapp;

import android.app.Application;
import android.content.Context;
import co.za.dvt.airportapp.di.components.AppComponent;
import co.za.dvt.airportapp.di.components.DaggerAppComponent;

public class MyApplication extends Application {
    private AppComponent component;

    public static MyApplication get(Context context) {
        Context myApplication = context.getApplicationContext();
        return (MyApplication)myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    private void initApplication() {
        component = DaggerAppComponent.builder().build();
        component.inject(this);
    }

    public AppComponent component() {
        return component;
    }
}
