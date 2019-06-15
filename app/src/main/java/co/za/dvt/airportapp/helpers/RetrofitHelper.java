package co.za.dvt.airportapp.helpers;

import java.util.Map;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.models.NearbyAirportsModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitHelper {
    @GET(Constants.NEARBY_AIRPORTS)
    Call<NearbyAirportsModel> getNearbyAirports(@Query("api_key") String api_key, @QueryMap Map<String, String> params);
}