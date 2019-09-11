package bs.joker.weatherforecast.mvp.presenter;

import android.text.format.Time;
import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;

import java.io.InvalidClassException;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import bs.joker.weatherforecast.common.manager.NetworkManager;
import bs.joker.weatherforecast.model.PreferencesHelper;
import bs.joker.weatherforecast.model.accuweather.cityKey.CityKey;
import bs.joker.weatherforecast.model.geonames.Geoname;
import bs.joker.weatherforecast.model.weatherbitio.currentConditions.DatumCurWeatherbitio;
import bs.joker.weatherforecast.model.weatherbitio.currentConditions.Weather;
import bs.joker.weatherforecast.model.wunderground.astronomy.SunPhase;
import bs.joker.weatherforecast.mvp.view.BaseView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by bakays on 24.10.2017.
 */

public abstract class BasePresenter<V extends BaseView> extends MvpPresenter<V> {
    public static final String LOG_TAG = "BasePresenter";

    @Inject
    NetworkManager mNetworkManager;

    public void loadDataAstronomy() {
        onCreateLoadDataObservableAstronomy()
                //.retry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
                })
                .subscribe(repositorAstronomyObservation -> {
                    onLoadingSuccessAstronomy(repositorAstronomyObservation);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
    }

    public void loadDataCityKeyAW(String lat, String lng) {
        onCreateLoadCityKeyObservableAccuweather(lat, lng)
                //.retry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
                })
                .subscribe(repositorCityKeyObservation -> {
                    onLoadingSuccessCityKey(repositorCityKeyObservation);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
    }


//    public void loadDataCurrent(boolean update) {
//        onLoadingCurrentFromPrefs();
//
//        if (update) {
//            onCreateLoadDataObservableCurrent()
//                    .retry()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnSubscribe(disposable -> {
//                        onLoadStart();
//                    })
//                    .doFinally(() -> {
//                        onLoadFinish();
//                    })
//                    .subscribe(repositorCurrentObservation -> {
//                        onLoadingSuccessCurrent(repositorCurrentObservation);
//                    }, error -> {
//                        error.printStackTrace();
//                        onLoadingError(error);
//                    });
//        }
//    }

    public void loadDataCurrent(boolean update) {
        onLoadingCurrentFromPrefs();

        if (update) {
            onCreateLoadDataObservableCurrentWeatherbitIO()
                    .toList()
                    //.retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> {
                        onLoadStart();
                    })
                    .doFinally(() -> {
                        onLoadFinish();
                    })
                    .subscribe(repositoryCurrentObservation -> {
                        onLoadingSuccessCurrent(repositoryCurrentObservation);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public void loadCity(String query) {
//        onCreateRestoreDataObservable()
//                .toList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable -> {
//                    onLoadStart();
//                })
//                .doFinally(() -> {
//                    onLoadFinish();
//                })
//                .subscribe(repositories -> {
//                    onLoadingSuccess(repositories);
//                }, error -> {
//                    error.printStackTrace();
//                    onLoadingError(error);
//                });
//
//        if (update) {
        onCreateLoadDataObservableGeoNames(query)
                .toList()
                //.retry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
                })
                .subscribe(repositories -> {
                    onLoadingCitySuccess(repositories);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
    }

    public abstract Observable<SunPhase> onCreateLoadDataObservableAstronomy();

    public abstract Observable<CityKey> onCreateLoadCityKeyObservableAccuweather(String lat, String lng);

    public abstract Observable<DatumCurWeatherbitio> onCreateLoadDataObservableCurrentWeatherbitIO();

    public abstract Observable<Geoname> onCreateLoadDataObservableGeoNames(String query);

    public void loadStartAstronomy() {
        loadDataAstronomy();
    }

    public void loadStartCurrent(boolean update) {
        loadDataCurrent(update);
    }

    public void loadStartCityKeyAW(String lat, String lng) {
        loadDataCityKeyAW(lat, lng);
    }

    public void loadStartCity(String query) {
        loadCity(query);
    }


    public void onLoadStart() {
        //getViewState().showListProgress();
        //Log.d(LOG_TAG, "onLoadStart()");
    }

    public void onLoadFinish() {
        //getViewState().hideListProgress();
        //Log.d(LOG_TAG, "onLoadFinish()");
    }

    public void onLoadingSuccessCityKey(CityKey cityKey) {
        getViewState().setCityKeyAWToDatabase(cityKey.getKey());
        Log.d(LOG_TAG, "onLoadingSuccessCityKey() - " + cityKey.getKey());
    }

    public void onLoadingSuccessAstronomy(SunPhase item) {

//        Time curTime = new Time(Time.getCurrentTimezone());
//        curTime.setToNow();
//
//        Time sunRise = new Time(Time.getCurrentTimezone());
//        Time sunSet = new Time(Time.getCurrentTimezone());
//        sunRise.set(0, Integer.parseInt(item.getSunrise().getMinute()), Integer.parseInt(item.getSunrise().getHour()), curTime.monthDay, curTime.month, curTime.year);
//        sunSet.set(0, Integer.parseInt(item.getSunset().getMinute()), Integer.parseInt(item.getSunset().getHour()), curTime.monthDay, curTime.month, curTime.year);
//
//        try {
//            PreferencesHelper.savePreference("sunRise", sunRise.toMillis(false));
//            PreferencesHelper.savePreference("sunSet", sunSet.toMillis(false));
//        } catch (InvalidClassException e) {
//            e.printStackTrace();
//        }

        //Log.d(LOG_TAG, "onLoadingSuccessCurrentAstronomy() - before " + item.getSunrise().getHour());
        //Boolean day = sunRise.before(curTime) && sunSet.after(curTime);
    }

//    public void onLoadingCurrentFromPrefs() {
//        CurrentObservation item = new CurrentObservation();
//
//        item.setTempC(Double.longBitsToDouble(PreferencesHelper.getSharedPreferences().getLong("curTemp", 0)));
//        item.setTempF(Double.longBitsToDouble(PreferencesHelper.getSharedPreferences().getLong("curTempF", 0)));
//        item.setWeatherDaily(PreferencesHelper.getSharedPreferences().getString("curCond", "Н/Д"));
//        item.setIcon(PreferencesHelper.getSharedPreferences().getString("curIcon", "Н/Д"));
//        item.setWindKph(Double.longBitsToDouble(PreferencesHelper.getSharedPreferences().getLong("curWindSpeed", 0)));
//        item.setWindMph(Double.longBitsToDouble(PreferencesHelper.getSharedPreferences().getLong("curWindSpeedUs", 0)));
//        Time time = new Time(Time.getCurrentTimezone());
//        time.set(PreferencesHelper.getSharedPreferences().getLong("lastUpdate", 0l));
//        getViewState().setCurrentCond(item, time);
//    }

    public void onLoadingCurrentFromPrefs() {
        DatumCurWeatherbitio item = new DatumCurWeatherbitio();

        Weather weatherModel = new Weather();
        weatherModel.setDescription(PreferencesHelper.getSharedPreferences().getString("curCond", "Н/Д"));
        weatherModel.setCode(PreferencesHelper.getSharedPreferences().getString("curCode", "900"));

        item.setTemp(Double.longBitsToDouble(PreferencesHelper.getSharedPreferences().getLong("curTemp", 0)));
        item.setWeather(weatherModel);
        item.setWindSpd(Double.longBitsToDouble(PreferencesHelper.getSharedPreferences().getLong("curWindSpeed", 0)));
        Time time = new Time(Time.getCurrentTimezone());
        time.set(PreferencesHelper.getSharedPreferences().getLong("lastUpdate", 0l));
        getViewState().setCurrentCond(item, time);
    }

//    public void onLoadingSuccessCurrent(CurrentObservation item) {
//        //Log.d(LOG_TAG, "onLoadingSuccessCurrentWU() - before ");
//
//        getViewState().setCurrentCond(item, getCurrentTime());
//
//        try {
//            PreferencesHelper.savePreference("lastUpdate", getCurrentTime().toMillis(false));
//            PreferencesHelper.savePreference("curTemp", Double.doubleToRawLongBits(item.getTempC()));
//            PreferencesHelper.savePreference("curTempF", Double.doubleToRawLongBits(item.getTempF()));
//            PreferencesHelper.savePreference("curWindSpeed", Double.doubleToRawLongBits(item.getWindKph()));
//            PreferencesHelper.savePreference("curWindSpeed", Double.doubleToRawLongBits(item.getWindKph()));
//            PreferencesHelper.savePreference("curWindSpeedUs", Double.doubleToRawLongBits(item.getWindMph()));
//            PreferencesHelper.savePreference("curCond", item.getWeatherDaily());
//            PreferencesHelper.savePreference("curIcon", item.getIcon());
//        } catch (InvalidClassException e) {
//            e.printStackTrace();
//        }
//
//        //Log.d(LOG_TAG, "onLoadingSuccessCurrentWU() - after " + getCurrentTime());
//    }

    public void onLoadingSuccessCurrent(List<DatumCurWeatherbitio> itemList) {
        Log.d(LOG_TAG, "onLoadingSuccessCurrentIO() - before ");
        DatumCurWeatherbitio item = itemList.get(0);
        Log.d(LOG_TAG, "Temp weatherbitIO: " + item.getTemp()); //1час = 3600000мс
        getViewState().setCurrentCond(item, getCurrentTime());

        Time curTime = new Time(Time.getCurrentTimezone());
        curTime.setToNow();

        Calendar cal = Calendar.getInstance();
        Log.d(LOG_TAG, "Offset: " + cal.getTimeZone().getRawOffset()/3600000); //1час = 3600000мс
        int timeZoneOffset = cal.getTimeZone().getRawOffset()/3600000;

        Time sunRise = new Time(Time.getCurrentTimezone());
        Time sunSet = new Time(Time.getCurrentTimezone());
        sunRise.set(0, Integer.parseInt(item.getSunrise().substring(3,5)), Integer.parseInt(item.getSunrise().substring(0,2))+timeZoneOffset, curTime.monthDay, curTime.month, curTime.year);
        sunSet.set(0, Integer.parseInt(item.getSunset().substring(3,5)), Integer.parseInt(item.getSunset().substring(0,2))+timeZoneOffset, curTime.monthDay, curTime.month, curTime.year);

        Log.d(LOG_TAG, "SunSet: " + sunSet.format("%d.%m.%Y %H.%M.%S") + ". SunRise: " + sunRise.format("%d.%m.%Y %H.%M.%S"));

        try {
            PreferencesHelper.savePreference("sunRise", sunRise.toMillis(false));
            PreferencesHelper.savePreference("sunSet", sunSet.toMillis(false));
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }

        try {
            PreferencesHelper.savePreference("lastUpdate", getCurrentTime().toMillis(false));
            PreferencesHelper.savePreference("curTemp", Double.doubleToRawLongBits(item.getTemp()));
            PreferencesHelper.savePreference("curWindSpeed", Double.doubleToRawLongBits(item.getWindSpd()));
            PreferencesHelper.savePreference("curCond", item.getWeather().getDescription());
            PreferencesHelper.savePreference("curCode", item.getWeather().getCode());
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "onLoadingSuccessCurrentIO() - after " + getCurrentTime().format("%d.%m.%Y %H.%M.%S"));
    }

    public void onLoadingCitySuccess(List<Geoname> items) {
        //Log.d(LOG_TAG, "onLoadingCitySuccess() - before " + items.size());
        if (items.size() > 0) {
            Log.d(LOG_TAG, "onLoadingCitySuccess() - name: " + items.get(0).getName());
        }
        getViewState().setItemsCIty(items);
        //Log.d(LOG_TAG, "onLoadingCitySuccess() - after");
    }

    public void onLoadingError(Throwable throwable) {
        getViewState().showError(throwable.getMessage());
    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
        //Log.d(LOG_TAG, "saveToDb");
    }

    public Time getCurrentTime() {
        Time curTime = new Time(Time.getCurrentTimezone());
        curTime.setToNow();
        return curTime;
    }
}
