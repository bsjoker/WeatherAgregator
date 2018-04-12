package bs.joker.weatheragregator.mvp.presenter;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.common.utils.ListHelper;
import bs.joker.weatheragregator.common.utils.SetID;
import bs.joker.weatheragregator.common.utils.UrlMaker;
import bs.joker.weatheragregator.model.accuweather.HourlyForecastAccuweather;
import bs.joker.weatheragregator.model.accuweather.cityKey.CityKey;
import bs.joker.weatheragregator.model.accuweather.daily5.Daily5ForecastAW;
import bs.joker.weatheragregator.model.darksky.DailyForecastDarksky;
import bs.joker.weatheragregator.model.darksky.HourlyForecastDarksky;
import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.view.ForecastDaily5ItemViewModel;
import bs.joker.weatheragregator.model.view.ForecastHourlyItemViewModel;
import bs.joker.weatheragregator.model.view.ForecastWeeklyItemViewModel;
import bs.joker.weatheragregator.model.wunderground.HourlyForecastWunderground;
import bs.joker.weatheragregator.model.wunderground.astronomy.SunPhase;
import bs.joker.weatheragregator.model.wunderground.current.CurrentObservation;
import bs.joker.weatheragregator.model.wunderground.daily5.Forecast_ok;
import bs.joker.weatheragregator.model.wunderground.daily5.Forecastday_;
import bs.joker.weatheragregator.mvp.view.BaseView;
import bs.joker.weatheragregator.rest.api.ApiMethods;
import bs.joker.weatheragregator.rest.api.WeatherApi;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

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
        //Log.d(LOG_TAG, "ObservableAstronomyWU");
        return mWeatherApi.getAstronomyWU(ApiMethods.ASTRONOMY_WUNDERGROUND).flatMap(sunPhase ->
        {
            //Log.d(LOG_TAG, "ObservableCurrentWU: " + hourlyCurrentWunderground.currentData.getIcon());
            return io.reactivex.Observable.just(sunPhase.astronomyData);
        });
    }

    public Observable<CityKey> onCreateLoadCityKeyObservableAccuweather(String lat, String lng) {
        //Log.d(LOG_TAG, "ObservableAstronomyWU");
        return mWeatherApi.getCityKeyAccuweather(UrlMaker.getUrlCityKeyAW(ApiMethods.CITY_KEY_ACCUWEATHER, lat, lng)).flatMap(cityKey ->
        {
            //Log.d(LOG_TAG, "ObservableCurrentWU: " + hourlyCurrentWunderground.currentData.getIcon());
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

    public Observable<Geoname> onCreateLoadDataObservableGeoNames(String query) {
        String citiID = "Moscow";
        //return mWeatherApi.getGeoNameCity(UrlMaker.getUrl(ApiMethods.CURRENT_WUNDERGROUND)).flatMap(hourlyCurrentWunderground ->
        return mWeatherApi.getGeoNameCity(UrlMaker.getUrlGeoNames(ApiMethods.GEONAMES_SEARCH, query)).flatMap(geoNames ->
        {
            //Log.d(LOG_TAG, "ObservableCurrentWU: " + geoNames.geonames_list.get());
            return io.reactivex.Observable.fromIterable(geoNames.geonames_list);
        });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable() {
        Log.d(LOG_TAG, "ObservableHourlyWU");
        final int[] i = {1};
        return mWeatherApi.getForecastWU(UrlMaker.getUrl(ApiMethods.HOURLY_WUNDERGROUND)).flatMap(hourlyForecastWundergroundResponse ->
                Observable.fromIterable(hourlyForecastWundergroundResponse.hourly_forecast_wa)
        )
                .take(12)
                .doOnNext(hourlyForecastWunderground -> {
                    hourlyForecastWunderground = SetID.setIDHourlyWU(hourlyForecastWunderground, i[0]);
                    saveToDb(hourlyForecastWunderground);
                    i[0]++;
                })

                .flatMap(hourlyForecastWunderground -> {
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastHourlyItemViewModel(hourlyForecastWunderground));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    public Observable<BaseViewModel> onCreateLoadDataObservableHourlyAW() {
        Log.d(LOG_TAG, "ObservableHourlyAW");
        final int[] i = {1};
        return mWeatherApi.getForecastAW(ApiMethods.HOURLY_ACCUWEATHER).flatMap(hourlyForecastAccuweatherResponse ->
                Observable.fromIterable(hourlyForecastAccuweatherResponse)
        )
                .take(12)
                .doOnNext(hourlyForecastAccuweather -> {
                    hourlyForecastAccuweather = SetID.setIDHourlyAW(hourlyForecastAccuweather, i[0]);
                    saveToDb(hourlyForecastAccuweather);
                    i[0]++;
                })
                .flatMap(hourlyForecastAccuweather -> {
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastHourlyItemViewModel(hourlyForecastAccuweather));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableHourlyDS() {
        Log.d(LOG_TAG, "ObservableHourlyDS");
        final int[] i = {1};
        return mWeatherApi.getForecastDS(UrlMaker.getUrl(ApiMethods.HOURLY_DARKSKY)).flatMap(hourlyForecastDarkskyResponse ->
                Observable.fromIterable(hourlyForecastDarkskyResponse.hourly_forecast_ds.data)
        )
                .skip(1)
                .take(12)
                .doOnNext(hourlyForecastDarksky -> {
                    hourlyForecastDarksky.setId(i[0]);
                    saveToDb(hourlyForecastDarksky);
                    i[0]++;
                })
                .flatMap(hourlyForecastDarksky -> {
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastHourlyItemViewModel(hourlyForecastDarksky));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableDaily5WU() {
        Log.d(LOG_TAG, "ObservableDaily5WU");
        final int[] i = {1};
        return mWeatherApi.getDaily5ForecastWU(UrlMaker.getUrl(ApiMethods.DAILY_5_WEEKLY_WUNDERGROUND)).flatMap(daily5ForecastWundergroundResponse ->
                Observable.fromIterable(ListHelper.getListDaily5WU(daily5ForecastWundergroundResponse.daily5forecastWU.simpleforecast.forecastday, daily5ForecastWundergroundResponse.daily5forecastWU.txt_forecast.forecastday))
        )
                .take(5)
                .doOnNext(daily5ForecastWunderground -> {
                    daily5ForecastWunderground.setId(i[0]);
                    //Log.d(LOG_TAG, "Daily5WU" + daily5ForecastWunderground.getLow());
                    saveToDb(daily5ForecastWunderground);
                    i[0]++;
                })
                .flatMap(daily5ForecastWunderground -> {
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastDaily5ItemViewModel(daily5ForecastWunderground));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableDaily5AW() {
        Log.d(LOG_TAG, "ObservableDaily5AW");
        final int[] i = {1};
        return mWeatherApi.getDaily5ForecastAW(ApiMethods.DAILY_5_ACCUWEATHER).flatMap(daily5ForecastAccuWeatherResponse ->
                Observable.fromIterable(daily5ForecastAccuWeatherResponse.daily5forecastAW)
        )
                .take(5)
                .doOnNext(daily5ForecastAccuWeather -> {
                    daily5ForecastAccuWeather = SetID.setIDDaily5AW(daily5ForecastAccuWeather, i[0]);
                    saveToDb(daily5ForecastAccuWeather);
                    i[0]++;
                })
                .flatMap(daily5ForecastAccuWeather -> {
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastDaily5ItemViewModel(daily5ForecastAccuWeather));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableDaily5DS() {
        Log.d(LOG_TAG, "ObservableDaily5DS");
        final int[] i = {1};
        return mWeatherApi.getDaily5ForecastDS(UrlMaker.getUrl(ApiMethods.DAILY_5_DARKSKY)).flatMap(daily5ForecastDarkskyResponse ->
                //Observable.fromIterable(ListHelper.getForecastListDS(hourlyForecastDarkskyResponse.hourly_forecast_ds))
                Observable.fromIterable(daily5ForecastDarkskyResponse.daily5forecastDS.data)
        )
                .take(5)
                .doOnNext(daily5ForecastDarksky -> {
                    daily5ForecastDarksky.setId(i[0]);
                    saveToDb(daily5ForecastDarksky);
                    i[0]++;
                })
                .flatMap(daily5ForecastDarksky -> {
                    //Log.d(LOG_TAG, "Daily: " + daily5ForecastDarksky.getTime());
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastDaily5ItemViewModel(daily5ForecastDarksky));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableWeeklyWU() {
        Log.d(LOG_TAG, "ObservableWeeklyWU");
        final int[] i = {1};
        return mWeatherApi.getWeeklyForecastWU(UrlMaker.getUrl(ApiMethods.DAILY_5_WEEKLY_WUNDERGROUND)).flatMap(weeklyForecastWundergroundResponse ->
                Observable.fromIterable(weeklyForecastWundergroundResponse.daily5forecastWU.simpleforecast.forecastday)
        )
                .skip(1)
                .take(7)
                .doOnNext(weeklyForecastWunderground -> {
                    weeklyForecastWunderground = SetID.setIDWeekly5WU(weeklyForecastWunderground, i[0]);
                    saveToDb(weeklyForecastWunderground);
                    i[0]++;
                })
                .flatMap(weeklyForecastWunderground -> {
                    //Log.d(LOG_TAG, "Weekly: " + weeklyForecastWunderground.getDate().getEpoch());
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastWeeklyItemViewModel(weeklyForecastWunderground));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableWeeklyAW() {
        Log.d(LOG_TAG, "ObservableWeeklyAW");
        final int[] i = {1};
        return mWeatherApi.getDaily5ForecastAW(ApiMethods.DAILY_5_ACCUWEATHER).flatMap(daily5ForecastAccuWeatherResponse ->
                Observable.fromIterable(daily5ForecastAccuWeatherResponse.daily5forecastAW)
        )
                .doOnNext(weeklyForecastAccuWeather -> {
                    weeklyForecastAccuWeather = SetID.setIDDaily5AW(weeklyForecastAccuWeather, i[0]);
                    saveToDb(weeklyForecastAccuWeather);
                    i[0]++;
                })
                .flatMap(weeklyForecastAccuWeather -> {
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastWeeklyItemViewModel(weeklyForecastAccuWeather));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableWeeklyDS() {
        Log.d(LOG_TAG, "ObservableWeeklyDS");
        final int[] i = {1};
        return mWeatherApi.getDaily5ForecastDS(UrlMaker.getUrl(ApiMethods.DAILY_5_DARKSKY)).flatMap(daily5ForecastDarkskyResponse ->
                Observable.fromIterable(daily5ForecastDarkskyResponse.daily5forecastDS.data)
        )
                .skip(1)
                .take(7)
                .doOnNext(weeklyForecastDarksky -> {
                    weeklyForecastDarksky.setId(i[0]);
                    saveToDb(weeklyForecastDarksky);
                    i[0]++;
                })
                .flatMap(weeklyForecastDarksky -> {
                    Log.d(LOG_TAG, "Weekly: " + weeklyForecastDarksky.getTemperatureLow());
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastWeeklyItemViewModel(weeklyForecastDarksky));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }


    public Callable<List<HourlyForecastWunderground>> getListFromRealmCallable() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();

            RealmResults<HourlyForecastWunderground> realmResults = realm.where(HourlyForecastWunderground.class)
                    .findAll();
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable).take(12)
                .flatMap(hourlyForecastWunderground -> Observable.fromIterable(parsePojoModel(hourlyForecastWunderground, null, null)));
    }

    //Восстановление из БД почасовой погоды с AccuWeather
    public Callable<List<HourlyForecastAccuweather>> getListFromRealmCallableHourlyAW() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();

            RealmResults<HourlyForecastAccuweather> realmResults = realm.where(HourlyForecastAccuweather.class)
                    .findAll();
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservableHourlyAW() {
        return Observable.fromCallable(getListFromRealmCallableHourlyAW())
                .flatMap(Observable::fromIterable).take(12)
                .flatMap(hourlyForecastAccuweather -> Observable.fromIterable(parsePojoModel(null, hourlyForecastAccuweather, null)));
    }

    //Восстановление из БД почасовой погоды с Darksky
    public Callable<List<HourlyForecastDarksky>> getListFromRealmCallableHourlyDS() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();

            RealmResults<HourlyForecastDarksky> realmResults = realm.where(HourlyForecastDarksky.class)
                    .findAll();
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservableHourlyDS() {
        return Observable.fromCallable(getListFromRealmCallableHourlyDS())
                .flatMap(Observable::fromIterable).take(12)
                .flatMap(hourlyForecastDarksky -> Observable.fromIterable(parsePojoModel(null, null, hourlyForecastDarksky)));
    }

    private List<BaseViewModel> parsePojoModel(HourlyForecastWunderground hourlyForecastWunderground, HourlyForecastAccuweather hourlyForecastAccuweather, HourlyForecastDarksky hourlyForecastDarksky) {
        List<BaseViewModel> items = new ArrayList<BaseViewModel>();
        //Log.d(LOG_TAG, "Realm: " + hourlyForecastWunderground.getId());
        if (hourlyForecastWunderground != null) {
            items.add(new ForecastHourlyItemViewModel(hourlyForecastWunderground));
            Log.d(LOG_TAG, "RealmWU: " + hourlyForecastWunderground.getId());
        }
        if (hourlyForecastAccuweather != null) {
            items.add(new ForecastHourlyItemViewModel(hourlyForecastAccuweather));
            Log.d(LOG_TAG, "RealmAW: " + hourlyForecastAccuweather.getId());
        }
        if (hourlyForecastDarksky != null) {
            items.add(new ForecastHourlyItemViewModel(hourlyForecastDarksky));
        }
        return items;
    }

    //Восстановление из БД погоды на 5 дней с Wunderground
    public Callable<List<Forecast_ok>> getListFromRealmCallableDaily5WU() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();

            RealmResults<Forecast_ok> realmResults = realm.where(Forecast_ok.class)
                    .findAll();
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservableDaily5WU() {
        return Observable.fromCallable(getListFromRealmCallableDaily5WU())
                .flatMap(Observable::fromIterable).take(5)
                .flatMap(daily5ForecastWU -> Observable.fromIterable(parsePojoModelDaily(daily5ForecastWU, null, null)));
    }

    //Восстановление из БД погоды на 5 дней с AccuWeather
    public Callable<List<Daily5ForecastAW>> getListFromRealmCallableDaily5AW() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();

            RealmResults<Daily5ForecastAW> realmResults = realm.where(Daily5ForecastAW.class)
                    .findAll();
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservableDaily5AW() {
        return Observable.fromCallable(getListFromRealmCallableDaily5AW())
                .flatMap(Observable::fromIterable).take(5)
                .flatMap(daily5ForecastAW -> Observable.fromIterable(parsePojoModelDaily(null, daily5ForecastAW, null)));
    }

    //Восстановление из БД погоды на 5 дней с Darksky
    public Callable<List<DailyForecastDarksky>> getListFromRealmCallableDaily5DS() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();

            RealmResults<DailyForecastDarksky> realmResults = realm.where(DailyForecastDarksky.class)
                    .findAll();
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservableDaily5DS() {
        return Observable.fromCallable(getListFromRealmCallableDaily5DS())
                .flatMap(Observable::fromIterable).take(5)
                .flatMap(dailyForecastDarksky -> Observable.fromIterable(parsePojoModelDaily(null, null, dailyForecastDarksky)));
    }

    private List<BaseViewModel> parsePojoModelDaily(Forecast_ok daily5ForecastWU, Daily5ForecastAW daily5ForecastAW, DailyForecastDarksky dailyForecastDarksky) {
        List<BaseViewModel> items = new ArrayList<BaseViewModel>();
        //Log.d(LOG_TAG, "Realm: " + hourlyForecastWunderground.getId());
        if (daily5ForecastWU != null) {
            items.add(new ForecastDaily5ItemViewModel(daily5ForecastWU));
            Log.d(LOG_TAG, "Realm5D_WU: " + daily5ForecastWU.getId());
        }
        if (daily5ForecastAW != null) {
            items.add(new ForecastDaily5ItemViewModel(daily5ForecastAW));
            Log.d(LOG_TAG, "Realm5D_AW: " + daily5ForecastAW.getId());
        }
        if (dailyForecastDarksky != null) {
            items.add(new ForecastDaily5ItemViewModel(dailyForecastDarksky));
            Log.d(LOG_TAG, "Realm5D_DS: " + dailyForecastDarksky.getId());
        }
        return items;
    }

    //Восстановление из БД погоды на неделю с Wunderground
    public Callable<List<Forecastday_>> getListFromRealmCallableWeeklyWU() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();

            RealmResults<Forecastday_> realmResults = realm.where(Forecastday_.class)
                    .findAll();
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyWU() {
        return Observable.fromCallable(getListFromRealmCallableWeeklyWU())
                .flatMap(Observable::fromIterable).take(7)
                .flatMap(weeklyForecastWU -> Observable.fromIterable(parsePojoModelWeekly(weeklyForecastWU, null, null)));
    }

    //Восстановление из БД погоды на неделю с Accuweather
    public Callable<List<Daily5ForecastAW>> getListFromRealmCallableWeeklyAW() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();

            RealmResults<Daily5ForecastAW> realmResults = realm.where(Daily5ForecastAW.class)
                    .findAll();
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyAW() {
        return Observable.fromCallable(getListFromRealmCallableWeeklyAW())
                .flatMap(Observable::fromIterable).take(5)
                .flatMap(weeklyForecastAW -> Observable.fromIterable(parsePojoModelWeekly(null, weeklyForecastAW, null)));
    }

    //Восстановление из БД погоды на неделю с Darksky
    public Callable<List<DailyForecastDarksky>> getListFromRealmCallableWeeklyDS() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();

            RealmResults<DailyForecastDarksky> realmResults = realm.where(DailyForecastDarksky.class)
                    .findAll();
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyDS() {
        return Observable.fromCallable(getListFromRealmCallableWeeklyDS())
                .flatMap(Observable::fromIterable).take(7)
                .flatMap(weeklyForecastDS -> Observable.fromIterable(parsePojoModelWeekly(null, null, weeklyForecastDS)));
    }

    private List<BaseViewModel> parsePojoModelWeekly(Forecastday_ weeklyForecastWU, Daily5ForecastAW weeklyForecastAW, DailyForecastDarksky weeklyForecastDarksky) {
        List<BaseViewModel> items = new ArrayList<BaseViewModel>();
        //Log.d(LOG_TAG, "Realm: " + hourlyForecastWunderground.getId());
        if (weeklyForecastWU != null) {
            items.add(new ForecastWeeklyItemViewModel(weeklyForecastWU));
            Log.d(LOG_TAG, "RealmWeeklyWU: " + weeklyForecastWU.getId());
        }

        if (weeklyForecastAW != null) {
            items.add(new ForecastWeeklyItemViewModel(weeklyForecastAW));
            Log.d(LOG_TAG, "RealmWeeklyAW: " + weeklyForecastAW.getId());
        }

        if (weeklyForecastDarksky != null) {
            items.add(new ForecastWeeklyItemViewModel(weeklyForecastDarksky));
            Log.d(LOG_TAG, "RealmWeeklyDS: " + weeklyForecastDarksky.getId());
        }

        return items;
    }
}
