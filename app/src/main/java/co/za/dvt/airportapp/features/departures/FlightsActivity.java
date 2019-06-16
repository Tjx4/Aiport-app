package co.za.dvt.airportapp.features.departures;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import java.util.List;
import javax.inject.Inject;
import co.za.dvt.airportapp.R;
import co.za.dvt.airportapp.adapters.FlightsAdapter;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.di.components.AppComponent;
import co.za.dvt.airportapp.di.components.DaggerFlightsComponent;
import co.za.dvt.airportapp.di.modules.FlightsModule;
import co.za.dvt.airportapp.features.base.activity.BaseChildActivity;
import co.za.dvt.airportapp.helpers.NotificationHelper;
import co.za.dvt.airportapp.models.DepartureFlightsModel;

public class FlightsActivity extends BaseChildActivity implements FlightsView , FlightsAdapter.ItemClickListener {

    @Inject
    FlightsPresenter flightsPresenter;
    private View appBarLayout;
    private TextView airportLocationTv;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private RecyclerView departuresRv;
    private TextView noflightsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);
        initViews();

        showFindingFlightsDialog(getResources().getString(R.string.finding_flights_message));
        String iataCode = getIntent().getBundleExtra(Constants.PAYLOAD_KEY).get(Constants.AIRPORT_IATACODE).toString();
        String airPortName  = getIntent().getBundleExtra(Constants.PAYLOAD_KEY).get(Constants.AIRPORT_NAME).toString();
        String airPortLocation = getIntent().getBundleExtra(Constants.PAYLOAD_KEY).get(Constants.AIRPORT_LOCATION).toString();
        String flightType = "departure";
        getPresenter().getFlights(iataCode, flightType, airPortName, airPortLocation);
    }

    @Override
    protected void initViews() {
        appBarLayout = findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.lightestText));
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));
        airportLocationTv = findViewById(R.id.tvAirportLocation);
        noflightsTv = findViewById(R.id.tvNoflights);

        departuresRv = findViewById(R.id.rvDepartures);
        departuresRv.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        DaggerFlightsComponent.builder().appComponent(appComponent)
                .flightsModule(new FlightsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public FlightsPresenter getPresenter() {
        return flightsPresenter;
    }

    @Override
    public void showFindingFlightsDialog(String message) {
        showLoadingDialog(message);
    }

    @Override
    public void hideFindingFlightsDialog() {
        hideLoader();
    }

    @Override
    public void showFlights(List<DepartureFlightsModel> flights, String airportName, String airportLocation) {
        appBarLayout.setVisibility(View.VISIBLE);
        collapsingToolbarLayout.setTitle(airportName);
        airportLocationTv.setText(airportLocation);
        FlightsAdapter flightsAdapter = new FlightsAdapter(this, flights);
        flightsAdapter.setClickListener(this);
        departuresRv.setAdapter(flightsAdapter);
    }

    public void onDialogDismissed(DialogInterface dialogInterface) {
        finish();
    }

    @Override
    public void onItemClick(View view, DepartureFlightsModel flight) {

    }

    @Override
    public void showFlightRetrieveErrorText(String errorMessage) {
        noflightsTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoFlightsFoundErrorDialog(String errorMessage) {
        NotificationHelper.showErrorDialog(this, getResources().getString(R.string.error_dialog_title), errorMessage,  getResources().getString(R.string.ok));
    }

}