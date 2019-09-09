package bs.joker.weatheragregator.mvp.presenter;

import android.text.format.Time;
import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;

import java.io.InvalidClassException;
import java.util.List;

import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.mvp.view.DailyForecastView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by 1 on 07.05.2018.
 */

public abstract class BaseDailyPresenter<V extends DailyForecastView> extends MvpPresenter<V> {
    public static final String LOG_TAG = "BaseDailyPresenter";

    public void loadDataDaily5WBIO(boolean update) {
        onCreateRestoreDataObservableDaily5WBIO()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableD5WBIO -> {
                    onLoadStartWBIO();
                })
                .doFinally(() -> {
                    onLoadFinishWBIO();
                })
                .subscribe(repositoriesD5WBIO -> {
                    onLoadingSuccessD5WBIO(repositoriesD5WBIO);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });

        if (update) {
            onCreateLoadDataObservableDaily5WBIO()
                    .toList()
                    //.retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableD5WBIO -> {
                        onLoadStartWBIO();
                    })
                    .doFinally(() -> {
                        onLoadFinishWBIO();
                    })
                    .subscribe(repositoriesD5WBIO -> {
                        onLoadingSuccessD5WBIO(repositoriesD5WBIO);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public void loadDataDaily5AW(boolean update) {
        onCreateRestoreDataObservableDaily5AW()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableD5AW -> {
                    onLoadStartAW();
                })
                .doFinally(() -> {
                    onLoadFinishAW();
                })
                .subscribe(repositoriesD5AW -> {
                    onLoadingSuccessD5AW(repositoriesD5AW);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });

        if (update) {
            onCreateLoadDataObservableDaily5AW()
                    .toList()
                    .retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableD5AW -> {
                        onLoadStartAW();
                    })
                    .doFinally(() -> {
                        onLoadFinishAW();
                    })
                    .subscribe(repositoriesD5AW -> {
                        onLoadingSuccessD5AW(repositoriesD5AW);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public void loadDataDaily5DS(boolean update) {
        onCreateRestoreDataObservableDaily5DS()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableD5DS -> {
                    onLoadStartDS();
                })
                .doFinally(() -> {
                    onLoadFinishDS();
                })
                .subscribe(repositoriesD5DS -> {
                    onLoadingSuccessD5DS(repositoriesD5DS);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });

        if (update) {
            onCreateLoadDataObservableDaily5DS()
                    .toList()
                    .retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableD5DS -> {
                        onLoadStartDS();
                    })
                    .doFinally(() -> {
                        onLoadFinishDS();
                    })
                    .subscribe(repositoriesD5DS -> {
                        onLoadingSuccessD5DS(repositoriesD5DS);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableDaily5WBIO();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableDaily5AW();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableDaily5DS();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableDaily5WBIO();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableDaily5AW();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableDaily5DS();

    public void loadStartDaily(boolean update) {
        loadDataDaily5WBIO(update);
        loadDataDaily5AW(update);
        loadDataDaily5DS(update);

        if (update) {
            try {
                PreferencesHelper.savePreference("lastUpdateDaily", getCurrentTime().toMillis(false));
                //Log.d(LOG_TAG, "Time daily: " + getCurrentTime() + ". " + update);
                PreferencesHelper.savePreference("ChangeCityDaily", false);
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    private void onLoadingSuccessD5WBIO(List<BaseViewModel> items) {
        getViewState().setItemsD5WBIO(items);
        getViewState().hideListProgressD5(1);
        //Log.d(LOG_TAG, "onLoadingSuccessD5_WU() - after");
    }

    private void onLoadingSuccessD5AW(List<BaseViewModel> items) {
        getViewState().setItemsD5AW(items);
        getViewState().hideListProgressD5(2);
        //Log.d(LOG_TAG, "onLoadingSuccessD5_AW() - after");
    }

    private void onLoadingSuccessD5DS(List<BaseViewModel> items) {
        getViewState().setItemsD5DS(items);
        getViewState().hideListProgressD5(3);
        //Log.d(LOG_TAG, "onLoadingSuccessD5_DS() - after");
    }

    public void onLoadStartWBIO() {
        getViewState().showListProgressD5(1);
        Log.d(LOG_TAG, "onLoadStartWBIO()");
    }

    public void onLoadFinishWBIO() {
        Log.d(LOG_TAG, "onLoadFinishWBIO()");
    }

    public void onLoadStartAW() {
        getViewState().showListProgressD5(2);
        Log.d(LOG_TAG, "onLoadStartAW()");
    }

    public void onLoadFinishAW() {
        Log.d(LOG_TAG, "onLoadFinishAW()");
    }
    public void onLoadStartDS() {
        getViewState().showListProgressD5(3);
        Log.d(LOG_TAG, "onLoadStartDS()");
    }

    public void onLoadFinishDS() {
        Log.d(LOG_TAG, "onLoadFinishDS()");
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
