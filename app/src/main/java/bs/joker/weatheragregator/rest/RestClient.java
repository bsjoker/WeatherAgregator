package bs.joker.weatheragregator.rest;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bakays on 27.10.2017.
 */

public class RestClient {
    private final static String BASE_URL = "http://api.wunderground.com/";
    private Retrofit mRetrofit;
    //API поиска городов http://api.geonames.org/searchJSON?name_startsWith=%D0%9D%D0%B8%D0%B6%D0%BD%D0%B5&cities=cities15000&maxRows=5&username=%20bsjoker


    public RestClient(){
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
//        WeatherApi service = mRetrofit.create(WeatherApi.class);
//        service.profilePicture("https://s3.amazon.com/profile-picture/path");

    }


    public <S> S createService(Class<S> serviceClass){
        return mRetrofit.create(serviceClass);
    }
}
