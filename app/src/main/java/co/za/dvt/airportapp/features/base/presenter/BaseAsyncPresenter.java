package co.za.dvt.airportapp.features.base.presenter;

import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.features.base.view.BaseView;
import co.za.dvt.airportapp.helpers.RetrofitHelper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseAsyncPresenter extends BasePresenter{
    protected String apiKey;
    protected boolean isBusy;
    protected RetrofitHelper retrofitHelper;

    public BaseAsyncPresenter(BaseView baseView) {
        super(baseView);
        setupRetrofit();
    }

    public void setupRetrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.CURRENTENVIRONMENT)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        retrofitHelper = retrofit.create(RetrofitHelper.class);
        apiKey = Constants.API_KEY;
    }
}