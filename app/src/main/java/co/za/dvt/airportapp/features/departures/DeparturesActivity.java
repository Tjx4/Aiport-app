package co.za.dvt.airportapp.features.departures;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.di.components.AppComponent;
import co.za.dvt.airportapp.features.base.activity.BaseChildActivity;
import co.za.dvt.airportapp.helpers.NotificationHelper;

public class DeparturesActivity extends BaseChildActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private RecyclerView departuresRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departures);
        initViews();

String iataCode = getIntent().getBundleExtra(Constants.PAYLOAD_KEY).get(Constants.AIRPORT_IATACODE).toString();
NotificationHelper.showShortToast(this, iataCode);
    }

    @Override
    protected void initViews() {
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.lightestText));
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        departuresRv = findViewById(R.id.rvDepartures);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void setupComponent(AppComponent appComponent) {

    }
}
