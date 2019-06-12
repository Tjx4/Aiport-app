package co.za.dvt.airportapp.di.modules;

import android.app.Application;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import co.za.dvt.airportapp.MyApplication;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private MyApplication myApplication;

    public AppModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return myApplication;
    }

    @Provides
    @Singleton
    OkHttpClient providOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);

        return builder.build();
    }

    @Provides
    @Singleton
    GsonConverterFactory providGsonConverterFactory(){
        return GsonConverterFactory.create();
    }
}