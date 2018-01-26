package bs.joker.weatheragregator.mvp.presenter;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.common.utils.ListHelper;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.view.ForecastDaily5ItemViewModel;
import bs.joker.weatheragregator.model.view.ForecastHourlyItemViewModel;
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

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable() {
        return mWeatherApi.getForecastWU(ApiMethods.HOURLY_WUNDERGROUND).flatMap(hourlyForecastWundergroundResponse ->
                Observable.fromIterable(hourlyForecastWundergroundResponse.hourly_forecast_wa)
        )
                .take(12)
                .flatMap(hourlyForecastWunderground -> {
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastHourlyItemViewModel(hourlyForecastWunderground));
                    return io.reactivex.Observable.fromIterable(items);
                });
//        return mWeatherApi.getForecast().flatMap(new Function<HourlyForecastWundergroundResponse, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(@NonNull HourlyForecastWundergroundResponse hourlyForecastWundergroundResponse) throws Exception {
//                Log.d(LOG_TAG, "1" + hourlyForecastWundergroundResponse.hourly_forecast.get(10).getFeelslike().getMetric());
//
//                return Observable.fromIterable(hourlyForecastWundergroundResponse.hourly_forecast);
//            }
//        })
//                .flatMap(hourlyForecastWunderground -> {
//                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
//                    Log.d(LOG_TAG, "2" + hourlyForecastWunderground.getFeelslike().getMetric());
//                    items.add(new ForecastHourlyItemViewModel(hourlyForecastWunderground));
//                    return io.reactivex.Observable.fromIterable(items);
//                });
    }

    public Observable<BaseViewModel> onCreateLoadDataObservableHourlyAW() {
        return mWeatherApi.getForecastAW(ApiMethods.HOURLY_ACCUWEATHER).flatMap(hourlyForecastAccuweatherResponse ->
                Observable.fromIterable(hourlyForecastAccuweatherResponse)
        )
                .flatMap(hourlyForecastAccuweather -> {
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastHourlyItemViewModel(hourlyForecastAccuweather));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableHourlyDS() {
        Log.d(LOG_TAG, "Observable_hourlyDS");
        return mWeatherApi.getForecastDS(ApiMethods.HOURLY_DARKSKY).flatMap(hourlyForecastDarkskyResponse ->
                //Observable.fromIterable(ListHelper.getForecastListDS(hourlyForecastDarkskyResponse.hourly_forecast_ds))
                Observable.fromIterable(hourlyForecastDarkskyResponse.hourly_forecast_ds.data)
        )
                .take(12)
                .flatMap(hourlyForecastDarksky -> {
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastHourlyItemViewModel(hourlyForecastDarksky));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableDaily5WU() {
        return mWeatherApi.getDaily5ForecastWU(ApiMethods.DAILY_5_WUNDERGROUND).flatMap(daily5ForecastWundergroundResponse ->
                Observable.fromIterable(ListHelper.getConditionListWU(daily5ForecastWundergroundResponse.daily5forecastWU.simpleforecast.forecastday, daily5ForecastWundergroundResponse.daily5forecastWU.txt_forecast.forecastday))
        )
                .take(5)
                .flatMap(daily5ForecastWunderground -> {
                    //Log.d(LOG_TAG, "Daily: " + daily5ForecastWunderground.getIcon_day() + " Day: " + daily5ForecastWunderground.getDate_readable());
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastDaily5ItemViewModel(daily5ForecastWunderground));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableDaily5AW() {
        //Log.d(LOG_TAG, "Observable");
        return mWeatherApi.getDaily5ForecastAW(ApiMethods.DAILY_5_ACCUWEATHER).flatMap(daily5ForecastAccuWeatherResponse ->
                        Observable.fromIterable(daily5ForecastAccuWeatherResponse.daily5forecastAW)
        )

                .flatMap(daily5ForecastAccuWeather -> {

                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastDaily5ItemViewModel(daily5ForecastAccuWeather));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableDaily5DS() {
        Log.d(LOG_TAG, "Observable");
        return mWeatherApi.getDaily5ForecastDS(ApiMethods.DAILY_5_DARKSKY).flatMap(daily5ForecastDarkskyResponse ->
                //Observable.fromIterable(ListHelper.getForecastListDS(hourlyForecastDarkskyResponse.hourly_forecast_ds))
                Observable.fromIterable(daily5ForecastDarkskyResponse.daily5forecastDS.data)
        )
                .take(5)
                .flatMap(daily5ForecastDarksky -> {
                    Log.d(LOG_TAG, "Daily: " + daily5ForecastDarksky.getTime());
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastDaily5ItemViewModel(daily5ForecastDarksky));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return null;
    }
}
