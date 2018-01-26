package bs.joker.weatheragregator.rest.api;

import java.util.List;

import bs.joker.weatheragregator.model.accuweather.HourlyForecastAccuweather;
import bs.joker.weatheragregator.rest.model.response.Daily5ForecastAccuWeatherResponse;
import bs.joker.weatheragregator.rest.model.response.Daily5ForecastDarkskyResponse;
import bs.joker.weatheragregator.rest.model.response.Daily5ForecastWundergroundResponse;
import bs.joker.weatheragregator.rest.model.response.HourlyForecastDarkskyResponse;
import bs.joker.weatheragregator.rest.model.response.HourlyForecastWundergroundResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by bakays on 26.10.2017.
 */

public interface WeatherApi {
//    @GET(ApiMethods.HOURLY_WUNDERGROUND)
//    Observable<HourlyForecastWundergroundResponse> getForecastWU(@Path("cityID") String cityID);
//@GET("f4405e44881d62b7/hourly/lang:RU/q/zmw:00000.164.27612.json")

    @GET()
    Observable<HourlyForecastWundergroundResponse> getForecastWU(@Url String url);

    @GET()
    //Observable<HourlyForecastWundergroundResponse> getForecastAW(@Url String url, @Path("cityID") String cityID);
    Observable<List<HourlyForecastAccuweather>> getForecastAW(@Url String url);

    @GET()
    Observable<HourlyForecastDarkskyResponse> getForecastDS(@Url String url);

    @GET()
    Observable<Daily5ForecastWundergroundResponse> getDaily5ForecastWU(@Url String url);

    @GET()
    Observable<Daily5ForecastAccuWeatherResponse> getDaily5ForecastAW(@Url String url);

    @GET()
    Observable<Daily5ForecastDarkskyResponse> getDaily5ForecastDS(@Url String url);
}
