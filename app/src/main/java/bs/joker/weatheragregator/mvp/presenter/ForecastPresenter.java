package bs.joker.weatheragregator.mvp.presenter;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.common.utils.UrlMaker;
import bs.joker.weatheragregator.model.accuweather.cityKey.CityKey;
import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.model.weatherbitio.currentConditions.DatumCurWeatherbitio;
import bs.joker.weatheragregator.model.wunderground.astronomy.SunPhase;
import bs.joker.weatheragregator.model.wunderground.current.CurrentObservation;
import bs.joker.weatheragregator.mvp.view.BaseView;
import bs.joker.weatheragregator.rest.api.ApiMethods;
import bs.joker.weatheragregator.rest.api.WeatherApi;
import io.reactivex.Observable;

/**
 * Created by bakays on 24.10.2017.
 */

@InjectViewState

public class ForecastPresenter extends BasePresenter<BaseView> {
    public static final String LOG_TAG = "ForecastPresenter";
    @Inject
    WeatherApi mWeatherApi;

    public ForecastPresenter() {
        MyApplication.getApplicationComponent().inject(this);
    }

    public Observable<SunPhase> onCreateLoadDataObservableAstronomy() {
        Log.d(LOG_TAG, "ObservableAstronomyWU");
        return mWeatherApi.getAstronomyWU(UrlMaker.getUrl(ApiMethods.ASTRONOMY_WUNDERGROUND)).flatMap(sunPhase ->
        {
            //Log.d(LOG_TAG, "ObservableCurrentWU: " + hourlyCurrentWunderground.currentData.getIcon());
            return io.reactivex.Observable.just(sunPhase.astronomyData);
        });
    }

    public Observable<CityKey> onCreateLoadCityKeyObservableAccuweather(String lat, String lng) {
        Log.d(LOG_TAG, "ObservableCityKeyAW");
        return mWeatherApi.getCityKeyAccuweather(UrlMaker.getUrlCityKeyAW(ApiMethods.CITY_KEY_ACCUWEATHER, lat, lng)).flatMap(cityKey ->
        //return mWeatherApi.getCityKeyAccuweather("http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=Yx8bmNySr0Vgyw8LmG6FAfGBhQnXEXZk&q=60.9344,76.5531").flatMap(cityKey ->
        {
            Log.d(LOG_TAG, "CityKeyObservableAccuweather: " + cityKey.getKey());
            return io.reactivex.Observable.just(cityKey);
        });
    }

    public Observable<CurrentObservation> onCreateLoadDataObservableCurrent() {
        Log.d(LOG_TAG, "ObservableCurrentWU");
        return mWeatherApi.getCurrentWU(UrlMaker.getUrl(ApiMethods.CURRENT_WUNDERGROUND)).flatMap(hourlyCurrentWunderground ->
        {
            Log.d(LOG_TAG, "ObservableCurrentWU: " + hourlyCurrentWunderground.currentData.getTemperatureString());
            return io.reactivex.Observable.just(hourlyCurrentWunderground.currentData);
        });
    }

    public Observable<DatumCurWeatherbitio> onCreateLoadDataObservableCurrentWeatherbitIO() {
        Log.d(LOG_TAG, "ObservableCurrentWeatherbitio");
        return mWeatherApi.getCurrentWeatherbitIO(UrlMaker.getUrlWeatherbitio(ApiMethods.CURRENT_CONDITIONS_IO)).flatMap(currentWeatherbitIO ->
        {
            //Log.d(LOG_TAG, "ObservableCurrentWeatherbitio: " + currentWeatherbitIO.weatherbitio_list.get(0).getWeather().getCode());
            Log.d(LOG_TAG, "ObservableCurrentWeatherbitio: res");
            return io.reactivex.Observable.fromIterable(currentWeatherbitIO.weatherbitio_list);
        });
    }

    public Observable<Geoname> onCreateLoadDataObservableGeoNames(String query) {
        String citiID = "Moscow";
        //return mWeatherApi.getGeoNameCity(UrlMaker.getUrl(ApiMethods.CURRENT_WUNDERGROUND)).flatMap(hourlyCurrentWunderground ->
        return mWeatherApi.getGeoNameCity(UrlMaker.getUrlGeoNames(ApiMethods.GEONAMES_SEARCH, query)).flatMap(geoNames ->
        {
            Log.d(LOG_TAG, "ObservableGeoNames: " + geoNames.geonames_list.get(0).getCountryName());
            return io.reactivex.Observable.fromIterable(geoNames.geonames_list);
        });
    }
}
