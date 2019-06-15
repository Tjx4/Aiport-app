package co.za.dvt.airportapp.helpers;

import java.util.List;
import java.util.Map;
import co.za.dvt.airportapp.constants.Constants;
import co.za.dvt.airportapp.models.AirportModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitHelper {
    @GET(Constants.NEARBY_AIRPORTS)
    Call<List<AirportModel>> getNearbyAirports(@Query("key") String api_key, @QueryMap Map<String, String> params);
}