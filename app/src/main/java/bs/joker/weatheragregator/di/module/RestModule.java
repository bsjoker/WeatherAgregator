package bs.joker.weatheragregator.di.module;

import javax.inject.Singleton;

import bs.joker.weatheragregator.rest.RestClient;
import bs.joker.weatheragregator.rest.api.WeatherApi;
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
