package bs.joker.weatheragregator.rest.api;

import java.util.List;

import bs.joker.weatheragregator.model.accuweather.HourlyForecastAccuweather;
import bs.joker.weatheragregator.model.accuweather.cityKey.CityKey;
import bs.joker.weatheragregator.rest.model.response.AstronomyWundergroundResponse;
import bs.joker.weatheragregator.rest.model.response.CurrentWeatherbitIOResponse;
import bs.joker.weatheragregator.rest.model.response.CurrentWundergroundResponse;
import bs.joker.weatheragregator.rest.model.response.Daily5ForecastAccuWeatherResponse;
import bs.joker.weatheragregator.rest.model.response.Daily5ForecastDarkskyResponse;
import bs.joker.weatheragregator.rest.model.response.Daily5ForecastWeatherbitIOResponse;
import bs.joker.weatheragregator.rest.model.response.Daily5ForecastWundergroundResponse;
import bs.joker.weatheragregator.rest.model.response.GeoNamesResponse;
import bs.joker.weatheragregator.rest.model.response.HourlyForecastDarkskyResponse;
import bs.joker.weatheragregator.rest.model.response.HourlyForecastWeatherbitIOResponse;
import bs.joker.weatheragregator.rest.model.response.HourlyForecastWundergroundResponse;
import bs.joker.weatheragregator.rest.model.response.WeeklyForecastWundergroundResponse;
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
    Observable<AstronomyWundergroundResponse> getAstronomyWU(@Url String url);

    @GET()
    Observable<CityKey> getCityKeyAccuweather(@Url String url);

    @GET()
    Observable<CurrentWundergroundResponse> getCurrentWU(@Url String url);

    @GET()
    Observable<CurrentWeatherbitIOResponse> getCurrentWeatherbitIO(@Url String url);

    @GET()
    Observable<GeoNamesResponse> getGeoNameCity(@Url String url);

//    @GET(ApiMethods.CURRENT_WUNDERGROUND)
//    Observable<CurrentWundergroundResponse> getCurrentWU(@Path("cityID") String cityID);

    @GET()
    Observable<HourlyForecastWundergroundResponse> getForecastWU(@Url String url);

    @GET()
    Observable<HourlyForecastWeatherbitIOResponse> getForecastWBIO(@Url String url);

    @GET()
    //Observable<HourlyForecastWundergroundResponse> getForecastAW(@Url String url, @Path("cityID") String cityID);
    Observable<List<HourlyForecastAccuweather>> getForecastAW(@Url String url);

    @GET()
    Observable<HourlyForecastDarkskyResponse> getForecastDS(@Url String url);

    @GET()
    Observable<Daily5ForecastWeatherbitIOResponse> getDaily5ForecastWBIO(@Url String url);

    @GET()
    Observable<Daily5ForecastAccuWeatherResponse> getDaily5ForecastAW(@Url String url);

    @GET()
    Observable<Daily5ForecastDarkskyResponse> getDaily5ForecastDS(@Url String url);

    @GET()
    Observable<WeeklyForecastWundergroundResponse> getWeeklyForecastWU(@Url String url);

}
