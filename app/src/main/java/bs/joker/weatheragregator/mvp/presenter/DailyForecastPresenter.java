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
import bs.joker.weatheragregator.model.accuweather.daily5.Daily5ForecastAW;
import bs.joker.weatheragregator.model.darksky.DailyForecastDarksky;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.view.ForecastDaily5ItemViewModel;
import bs.joker.weatheragregator.model.wunderground.daily5.Forecast_ok;
import bs.joker.weatheragregator.mvp.view.DailyForecastView;
import bs.joker.weatheragregator.rest.api.ApiMethods;
import bs.joker.weatheragregator.rest.api.WeatherApi;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 1 on 07.05.2018.
 */

@InjectViewState

public class DailyForecastPresenter extends BaseDailyPresenter<DailyForecastView> {
    public static final String LOG_TAG = "DailyForecastPresenter";
    private List<Forecast_ok> listWU;
    private List<Daily5ForecastAW> listAW;
    private List<DailyForecastDarksky> listDS;

    @Inject
    WeatherApi mWeatherApi;

    public DailyForecastPresenter() {
        MyApplication.getApplicationComponent().inject(this);
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
        return mWeatherApi.getDaily5ForecastAW(UrlMaker.getUrlAW(ApiMethods.DAILY_5_ACCUWEATHER)).flatMap(daily5ForecastAccuWeatherResponse ->
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
        return mWeatherApi.getDaily5ForecastDS(UrlMaker.getUrlDS(ApiMethods.DAILY_5_DARKSKY)).flatMap(daily5ForecastDarkskyResponse ->
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

    //Восстановление из БД погоды на 5 дней с Wunderground
    public Callable<List<Forecast_ok>> getListFromRealmCallableDaily5WU() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(inRealm-> {
                RealmResults<Forecast_ok> realmResults = realm.where(Forecast_ok.class)
                        .findAll();
                listWU = realm.copyFromRealm(realmResults);
                });
            return listWU;
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
            realm.executeTransaction(inRealm-> {
                RealmResults<Daily5ForecastAW> realmResults = realm.where(Daily5ForecastAW.class)
                        .findAll();
                listAW = realm.copyFromRealm(realmResults);
            });
            return listAW;
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
            realm.executeTransaction(inRealm-> {
                RealmResults<DailyForecastDarksky> realmResults = realm.where(DailyForecastDarksky.class)
                        .findAll();
                listDS = realm.copyFromRealm(realmResults);
            });
            return listDS;
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
            //Log.d(LOG_TAG, "Realm5D_WU: " + daily5ForecastWU.getId());
        }
        if (daily5ForecastAW != null) {
            items.add(new ForecastDaily5ItemViewModel(daily5ForecastAW));
            //Log.d(LOG_TAG, "Realm5D_AW: " + daily5ForecastAW.getId());
        }
        if (dailyForecastDarksky != null) {
            items.add(new ForecastDaily5ItemViewModel(dailyForecastDarksky));
            //Log.d(LOG_TAG, "Realm5D_DS: " + dailyForecastDarksky.getId());
        }
        return items;
    }
}
