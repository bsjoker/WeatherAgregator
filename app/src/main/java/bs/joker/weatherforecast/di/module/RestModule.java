package bs.joker.weatherforecast.di.module;

import javax.inject.Singleton;

import bs.joker.weatherforecast.rest.RestClient;
import bs.joker.weatherforecast.rest.api.WeatherApi;
import dagger.Module;
import dagger.Provides;

/**
 * Created by bakays on 06.12.2017.
 */

@Module
public class RestModule {
    private RestClient mRestClient;


    public RestModule() {
        mRestClient = new RestClient();
    }

    @Singleton
    @Provides
    public RestClient provideRestClient(){
        return mRestClient;
    }

    @Singleton
    @Provides
    public WeatherApi provideWeatherApi(){
        return mRestClient.createService(WeatherApi.class);
    }
}
