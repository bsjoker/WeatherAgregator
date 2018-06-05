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
import bs.joker.weatheragregator.model.accuweather.daily5.Daily5ForecastAW;
import bs.joker.weatheragregator.model.darksky.DailyForecastDarksky;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.view.ForecastWeeklyItemViewModel;
import bs.joker.weatheragregator.model.wunderground.daily5.Forecastday_;
import bs.joker.weatheragregator.mvp.view.WeeklyForecastView;
import bs.joker.weatheragregator.rest.api.ApiMethods;
import bs.joker.weatheragregator.rest.api.WeatherApi;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 1 on 07.05.2018.
 */

@InjectViewState

public class WeeklyForecastPresenter extends BaseWeeklyPresenter<WeeklyForecastView> {
    public static final String LOG_TAG = "WeeklyForecastPresenter";
    private List<Forecastday_> listWU;
    private List<Daily5ForecastAW> listAW;
    private List<DailyForecastDarksky> listDS;

    @Inject
    WeatherApi mWeatherApi;

    public WeeklyForecastPresenter() {
        MyApplication.getApplicationComponent().inject(this);
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
                    Log.d(LOG_TAG, "WeeklyWU_NET: " + weeklyForecastWunderground.getHigh().getCelsius());
                    List<BaseViewModel> items = new ArrayList<BaseViewModel>();
                    items.add(new ForecastWeeklyItemViewModel(weeklyForecastWunderground));
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
    public Callable<List<Forecastday_>> getListFromRealmCallableWeeklyWU() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(inRealm-> {
                RealmResults<Forecastday_> realmResults = realm.where(Forecastday_.class).findAll();
                listWU = realm.copyFromRealm(realmResults);
            });
            return listWU;
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

    private List<BaseViewModel> parsePojoModelWeekly(Forecastday_ weeklyForecastWU, Daily5ForecastAW weeklyForecastAW, DailyForecastDarksky weeklyForecastDarksky) {
        List<BaseViewModel> items = new ArrayList<BaseViewModel>();
        //Log.d(LOG_TAG, "Realm: " + hourlyForecastWunderground.getId());
        if (weeklyForecastWU != null) {
            items.add(new ForecastWeeklyItemViewModel(weeklyForecastWU));
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
