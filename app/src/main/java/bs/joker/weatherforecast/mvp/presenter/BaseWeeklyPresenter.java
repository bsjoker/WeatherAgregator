package bs.joker.weatherforecast.mvp.presenter;

import android.text.format.Time;
import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;

import java.io.InvalidClassException;
import java.util.List;

import bs.joker.weatherforecast.model.PreferencesHelper;
import bs.joker.weatherforecast.model.view.BaseViewModel;
import bs.joker.weatherforecast.mvp.view.WeeklyForecastView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by 1 on 07.05.2018.
 */

public abstract class BaseWeeklyPresenter<V extends WeeklyForecastView> extends MvpPresenter<V> {
    public static final String LOG_TAG = "BaseWeeklyPresenter";

    public void loadDataWeeklyWBIO(boolean update) {
        onCreateRestoreDataObservableWeeklyWBIO()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableWeeklyWBIO -> {
                    onLoadStartWBIO();
                })
                .doFinally(() -> {
                    onLoadFinishWBIO();
                })
                .subscribe(repositoriesWeeklyWBIO -> {
                    onLoadingSuccessWeeklyWBIO(repositoriesWeeklyWBIO);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
        if (update) {
            onCreateLoadDataObservableWeeklyWBIO()
                    .toList()
                    .retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableWeeklyWBIO -> {
                        onLoadStartWBIO();
                    })
                    .doFinally(() -> {
                        onLoadFinishWBIO();
                    })
                    .subscribe(repositoriesWeeklyWBIO -> {
                        onLoadingSuccessWeeklyWBIO(repositoriesWeeklyWBIO);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public void loadDataWeeklyAW(boolean update) {
        onCreateRestoreDataObservableWeeklyAW()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableWeeklyAW -> {
                    onLoadStartAW();
                })
                .doFinally(() -> {
                    onLoadFinishAW();
                })
                .subscribe(repositoriesWeeklyAW -> {
                    onLoadingSuccessWeeklyAW(repositoriesWeeklyAW);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });

        if (update) {
            onCreateLoadDataObservableWeeklyAW()
                    .toList()
                    .retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableWeeklyAW -> {
                        onLoadStartAW();
                    })
                    .doFinally(() -> {
                        onLoadFinishAW();
                    })
                    .subscribe(repositoriesWeeklyAW -> {
                        onLoadingSuccessWeeklyAW(repositoriesWeeklyAW);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public void loadDataWeeklyDS(boolean update) {
        onCreateRestoreDataObservableWeeklyDS()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableWeeklyDS -> {
                    onLoadStartDS();
                })
                .doFinally(() -> {
                    onLoadFinishDS();
                })
                .subscribe(repositoriesWeeklyDS -> {
                    onLoadingSuccessWeeklyDS(repositoriesWeeklyDS);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });

        if (update) {
            onCreateLoadDataObservableWeeklyDS()
                    .toList()
                    .retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableWeeklyDS -> {
                        onLoadStartDS();
                    })
                    .doFinally(() -> {
                        onLoadFinishDS();
                    })
                    .subscribe(repositoriesWeeklyDS -> {
                        onLoadingSuccessWeeklyDS(repositoriesWeeklyDS);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableWeeklyWBIO();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableWeeklyAW();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableWeeklyDS();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyWBIO();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyAW();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyDS();

    public void loadStartWeekly(boolean update) {
        loadDataWeeklyWBIO(update);
        loadDataWeeklyAW(update);
        loadDataWeeklyDS(update);

        if (update) {
            try {
                PreferencesHelper.savePreference("lastUpdateWeekly", getCurrentTime().toMillis(false));
                //Log.d(LOG_TAG, "Time weekly: " + getCurrentTime() + ". " + update);
                PreferencesHelper.savePreference("ChangeCityWeekly", false);
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    public void onLoadStartWBIO() {
        getViewState().showListProgressWeekly(1);
        Log.d(LOG_TAG, "onLoadStartWBIO()");
    }

    public void onLoadFinishWBIO() {
        Log.d(LOG_TAG, "onLoadFinishWBIO()");
    }

    public void onLoadStartAW() {
        getViewState().showListProgressWeekly(2);
        Log.d(LOG_TAG, "onLoadStartAW()");
    }

    public void onLoadFinishAW() {
        Log.d(LOG_TAG, "onLoadFinishAW()");
    }

    public void onLoadStartDS() {
        getViewState().showListProgressWeekly(3);
        Log.d(LOG_TAG, "onLoadStartDS()");
    }

    public void onLoadFinishDS() {
        Log.d(LOG_TAG, "onLoadFinishDS()");
    }

    private void onLoadingSuccessWeeklyWBIO(List<BaseViewModel> items) {
        getViewState().setItemsWeeklyWBIO(items);
        getViewState().hideListProgressWeekly(1);
        //Log.d(LOG_TAG, "onLoadingSuccessWeekly_WU() - after");
    }

    private void onLoadingSuccessWeeklyAW(List<BaseViewModel> items) {
        getViewState().setItemsWeeklyAW(items);
        getViewState().hideListProgressWeekly(2);
        //Log.d(LOG_TAG, "onLoadingSuccessWeekly_AW() - after");
    }

    private void onLoadingSuccessWeeklyDS(List<BaseViewModel> items) {
        getViewState().setItemsWeeklyDS(items);
        getViewState().hideListProgressWeekly(3);
        //Log.d(LOG_TAG, "onLoadingSuccessWeekly_DS() - after");
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
