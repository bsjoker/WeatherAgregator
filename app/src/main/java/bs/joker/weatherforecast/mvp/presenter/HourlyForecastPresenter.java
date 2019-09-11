package bs.joker.weatherforecast.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatherforecast.common.utils.SetID;
import bs.joker.weatherforecast.common.utils.UrlMaker;
import bs.joker.weatherforecast.model.accuweather.HourlyForecastAccuweather;
import bs.joker.weatherforecast.model.darksky.HourlyForecastDarksky;
import bs.joker.weatherforecast.model.view.BaseViewModel;
import bs.joker.weatherforecast.model.view.ForecastHourlyItemViewModel;
import bs.joker.weatherforecast.model.weatherbitio.hourly.DatumHourlyWeatherbitio;
import bs.joker.weatherforecast.mvp.view.HourlyForecastView;
import bs.joker.weatherforecast.rest.api.ApiMethods;
import bs.joker.weatherforecast.rest.api.WeatherApi;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 1 on 04.05.2018.
 */

@InjectViewState

public class HourlyForecastPresenter extends BaseHourlyPresenter<HourlyForecastView> {
    public static final String LOG_TAG = "HourlyForecastPresenter";
    private List<DatumHourlyWeatherbitio> listWBIO;
    private List<HourlyForecastAccuweather> listAW;
    private List<HourlyForecastDarksky> listDS;

    @Inject
    WeatherApi mWeatherApi;

    public HourlyForecastPresenter() {
        MyApplication.getApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableHourlyWBIO() {
        Log.d(LOG_TAG, "ObservableHourlyWBIO");
        final int[] i = {1};
        return mWeatherApi.getForecastWBIO(UrlMaker.getUrlWeatherbitio(ApiMethods.HOURLY_WEATHERBIT_IO)).flatMap(hourlyForecastWeatherbitIOResponse ->
                Observable.fromIterable(hourlyForecastWeatherbitIOResponse.weatherbitio_list)
        )
                .take(12)
                .doOnNext(hourlyForecastWeatherbitIO -> {
                    Log.d(LOG_TAG, "ObservableHourlyWBIO temp: " + hourlyForecastWeatherbitIO.getTemp());
                    hourlyForecastWeatherbitIO = SetID.setIDHourlyWBIO(hourlyForecastWeatherbitIO, i[0]);
                    saveToDb(hourlyForecastWeatherbitIO);
                    i[0]++;
                })

                .flatMap(hourlyForecastWeatherbitIO -> {
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    Log.d(LOG_TAG, "ObservableHourlyWBIO temp: " + hourlyForecastWeatherbitIO.getTemp());
                    items.add(new ForecastHourlyItemViewModel(hourlyForecastWeatherbitIO));
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
                    Log.d(LOG_TAG, "ObservableHourlyAW temp: " + hourlyForecastAccuweather.getTemperature().getValue());
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

    public Callable<List<DatumHourlyWeatherbitio>> getListFromRealmCallable() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(inRealm-> {
                final RealmResults<DatumHourlyWeatherbitio> realmResults = realm.where(DatumHourlyWeatherbitio.class)
                        .findAll();
                 listWBIO = realm.copyFromRealm(realmResults);
            });

            return listWBIO;
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable)
                .flatMap(hourlyForecastWeatherbitIO -> Observable.fromIterable(parsePojoModel(hourlyForecastWeatherbitIO, null, null)));
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

    private List<BaseViewModel> parsePojoModel(DatumHourlyWeatherbitio hourlyForecastWeatherbitIO, HourlyForecastAccuweather hourlyForecastAccuweather, HourlyForecastDarksky hourlyForecastDarksky) {
        List<BaseViewModel> items = new ArrayList<BaseViewModel>();
        //Log.d(LOG_TAG, "Realm: " + hourlyForecastWunderground.getId());
        if (hourlyForecastWeatherbitIO != null) {
            items.add(new ForecastHourlyItemViewModel(hourlyForecastWeatherbitIO));
            Log.d(LOG_TAG, "RealmWBIO " + hourlyForecastWeatherbitIO.getTemp());
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
