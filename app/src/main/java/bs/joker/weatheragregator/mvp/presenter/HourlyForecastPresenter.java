package bs.joker.weatheragregator.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.common.utils.SetID;
import bs.joker.weatheragregator.common.utils.UrlMaker;
import bs.joker.weatheragregator.model.accuweather.HourlyForecastAccuweather;
import bs.joker.weatheragregator.model.darksky.HourlyForecastDarksky;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.view.ForecastHourlyItemViewModel;
import bs.joker.weatheragregator.model.wunderground.HourlyForecastWunderground;
import bs.joker.weatheragregator.mvp.view.HourlyForecastView;
import bs.joker.weatheragregator.rest.api.ApiMethods;
import bs.joker.weatheragregator.rest.api.WeatherApi;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 1 on 04.05.2018.
 */

@InjectViewState

public class HourlyForecastPresenter extends BaseHourlyPresenter<HourlyForecastView> {
    public static final String LOG_TAG = "HourlyForecastPresenter";
    private List<HourlyForecastWunderground> listWU;
    private List<HourlyForecastAccuweather> listAW;
    private List<HourlyForecastDarksky> listDS;

    @Inject
    WeatherApi mWeatherApi;

    public HourlyForecastPresenter() {
        MyApplication.getApplicationComponent().inject(this);
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
                    Log.d(LOG_TAG, "ObservableHourlyWU " + hourlyForecastWunderground.getTemp().getMetric());
                    items.add(new ForecastHourlyItemViewModel(hourlyForecastWunderground));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    public Observable<BaseViewModel> onCreateLoadDataObservableHourlyAW() {
        Log.d(LOG_TAG, "ObservableHourlyAW");
        final int[] i = {1};
        return mWeatherApi.getForecastAW(UrlMaker.getUrlAW(ApiMethods.HOURLY_ACCUWEATHER)).flatMap(hourlyForecastAccuweatherResponse ->
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
        return mWeatherApi.getForecastDS(UrlMaker.getUrlDS(ApiMethods.HOURLY_DARKSKY)).flatMap(hourlyForecastDarkskyResponse ->
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

    public Callable<List<HourlyForecastWunderground>> getListFromRealmCallable() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(inRealm-> {
                final RealmResults<HourlyForecastWunderground> realmResults = realm.where(HourlyForecastWunderground.class)
                        .findAll();
                 listWU = realm.copyFromRealm(realmResults);
            });

            return listWU;
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
            realm.executeTransaction(inRealm-> {
                RealmResults<HourlyForecastAccuweather> realmResults = realm.where(HourlyForecastAccuweather.class)
                        .findAll();
                listAW = realm.copyFromRealm(realmResults);
                    });
            return listAW;
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
            realm.executeTransaction(inRealm-> {
                RealmResults<HourlyForecastDarksky> realmResults = realm.where(HourlyForecastDarksky.class)
                        .findAll();
                listDS = realm.copyFromRealm(realmResults);
            });
            return listDS;
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
            Log.d(LOG_TAG, "RealmWU " + hourlyForecastWunderground.getTemp().getMetric());
            //Log.d(LOG_TAG, "RealmWU: " + hourlyForecastWunderground.getId());
        }
        if (hourlyForecastAccuweather != null) {
            items.add(new ForecastHourlyItemViewModel(hourlyForecastAccuweather));
            Log.d(LOG_TAG, "RealmAW " + hourlyForecastAccuweather.getTemperature().getValue());
            //Log.d(LOG_TAG, "RealmAW: " + hourlyForecastAccuweather.getId());
        }
        if (hourlyForecastDarksky != null) {
            items.add(new ForecastHourlyItemViewModel(hourlyForecastDarksky));
            Log.d(LOG_TAG, "RealmDS " + hourlyForecastDarksky.getTemperature());
            //Log.d(LOG_TAG, "RealmDS: " + hourlyForecastDarksky.getId());
        }
        return items;
    }

}
