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
import bs.joker.weatherforecast.model.accuweather.daily5.Daily5ForecastAW;
import bs.joker.weatherforecast.model.darksky.DailyForecastDarksky;
import bs.joker.weatherforecast.model.view.BaseViewModel;
import bs.joker.weatherforecast.model.view.ForecastWeeklyItemViewModel;
import bs.joker.weatherforecast.model.weatherbitio.daily.DatumDailyWeatherbitio;
import bs.joker.weatherforecast.mvp.view.WeeklyForecastView;
import bs.joker.weatherforecast.rest.api.ApiMethods;
import bs.joker.weatherforecast.rest.api.WeatherApi;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 1 on 07.05.2018.
 */

@InjectViewState

public class WeeklyForecastPresenter extends BaseWeeklyPresenter<WeeklyForecastView> {
    public static final String LOG_TAG = "WeeklyForecastPresenter";
    private List<DatumDailyWeatherbitio> listWBIO;
    private List<Daily5ForecastAW> listAW;
    private List<DailyForecastDarksky> listDS;

    @Inject
    WeatherApi mWeatherApi;

    public WeeklyForecastPresenter() {
        MyApplication.getApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableWeeklyWBIO() {
        Log.d(LOG_TAG, "ObservableWeeklyWBIO");
        final int[] i = {1};
        return mWeatherApi.getDaily5ForecastWBIO(UrlMaker.getUrlWeatherbitio(ApiMethods.DAILY_5_WEATHERBIT_IO)).flatMap(weeklyForecastWeatherbitResponse ->
                Observable.fromIterable(weeklyForecastWeatherbitResponse.weatherbitio_list)
        )
                .skip(1)
                .take(7)
                .doOnNext(weeklyForecastWeatherbit -> {
                    weeklyForecastWeatherbit = SetID.setIDDaily5WBIO(weeklyForecastWeatherbit, i[0]);
                    saveToDb(weeklyForecastWeatherbit);
                    i[0]++;
                })
                .flatMap(weeklyForecastWeatherbitIO -> {
                    Log.d(LOG_TAG, "WeeklyWU_NET: " + weeklyForecastWeatherbitIO.getTemp());
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastWeeklyItemViewModel(weeklyForecastWeatherbitIO));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservableWeeklyAW() {
        Log.d(LOG_TAG, "ObservableWeeklyAW");
        final int[] i = {1};
        return mWeatherApi.getDaily5ForecastAW(UrlMaker.getUrlAW(ApiMethods.DAILY_5_ACCUWEATHER)).flatMap(daily5ForecastAccuWeatherResponse ->
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
        return mWeatherApi.getDaily5ForecastDS(UrlMaker.getUrlDS(ApiMethods.DAILY_5_DARKSKY)).flatMap(daily5ForecastDarkskyResponse ->
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
                    //Log.d(LOG_TAG, "Weekly: " + weeklyForecastDarksky.getTemperatureLow());
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastWeeklyItemViewModel(weeklyForecastDarksky));
                    return io.reactivex.Observable.fromIterable(items);
                });
    }

    //Восстановление из БД погоды на неделю с Wunderground
    public Callable<List<DatumDailyWeatherbitio>> getListFromRealmCallableWeeklyWBIO() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(inRealm-> {
                RealmResults<DatumDailyWeatherbitio> realmResults = realm.where(DatumDailyWeatherbitio.class).findAll();
                listWBIO = realm.copyFromRealm(realmResults);
            });
            return listWBIO;
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyWBIO() {
        return Observable.fromCallable(getListFromRealmCallableWeeklyWBIO())
                .flatMap(Observable::fromIterable).take(7)
                .flatMap(weeklyForecastWBIO -> Observable.fromIterable(parsePojoModelWeekly(weeklyForecastWBIO, null, null)));
    }

    //Восстановление из БД погоды на неделю с Accuweather
    public Callable<List<Daily5ForecastAW>> getListFromRealmCallableWeeklyAW() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(inRealm-> {
                RealmResults<Daily5ForecastAW> realmResults = realm.where(Daily5ForecastAW.class)
                        .findAll();
                listAW = realm.copyFromRealm(realmResults);
            });
            return listAW;
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
            realm.executeTransaction(inRealm-> {
                RealmResults<DailyForecastDarksky> realmResults = realm.where(DailyForecastDarksky.class)
                        .findAll();
                listDS = realm.copyFromRealm(realmResults);
            });
            return listDS;
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyDS() {
        return Observable.fromCallable(getListFromRealmCallableWeeklyDS())
                .flatMap(Observable::fromIterable).take(7)
                .flatMap(weeklyForecastDS -> Observable.fromIterable(parsePojoModelWeekly(null, null, weeklyForecastDS)));
    }

    private List<BaseViewModel> parsePojoModelWeekly(DatumDailyWeatherbitio weeklyForecastWBIO, Daily5ForecastAW weeklyForecastAW, DailyForecastDarksky weeklyForecastDarksky) {
        List<BaseViewModel> items = new ArrayList<BaseViewModel>();
        //Log.d(LOG_TAG, "Realm: " + hourlyForecastWunderground.getId());
        if (weeklyForecastWBIO != null) {
            items.add(new ForecastWeeklyItemViewModel(weeklyForecastWBIO));
            //Log.d(LOG_TAG, "RealmWeeklyWU: " + weeklyForecastWU.getId());
        }

        if (weeklyForecastAW != null) {
            items.add(new ForecastWeeklyItemViewModel(weeklyForecastAW));
            //Log.d(LOG_TAG, "RealmWeeklyAW: " + weeklyForecastAW.getId());
        }

        if (weeklyForecastDarksky != null) {
            items.add(new ForecastWeeklyItemViewModel(weeklyForecastDarksky));
            //Log.d(LOG_TAG, "RealmWeeklyDS: " + weeklyForecastDarksky.getId());
        }

        return items;
    }
}
