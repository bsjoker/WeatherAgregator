package bs.joker.weatheragregator.mvp.presenter;

import android.text.format.Time;
import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;

import java.io.InvalidClassException;
import java.util.List;

import javax.inject.Inject;

import bs.joker.weatheragregator.common.manager.NetworkManager;
import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.mvp.view.HourlyForecastView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by bakays on 24.10.2017.
 */

public abstract class BaseHourlyPresenter<V extends HourlyForecastView> extends MvpPresenter<V> {
    public static final String LOG_TAG = "BaseHourlyPresenter";
    private boolean mIsInLoading;

    @Inject
    NetworkManager mNetworkManager;

//    public void loadDataWU(boolean update) {
//        if (update) {
//            onCreateLoadDataObservable()
//                    .toList()
//                    .retry()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnSubscribe(disposable -> {
//                        onLoadStartWU();
//                    })
//                    .doFinally(() -> {
//                        onLoadFinishWU();
//                    })
//                    .subscribe(repositories -> {
//                        Log.d(LOG_TAG, "SubscribeNet");
//                        onLoadingSuccess(repositories);
//                    }, error -> {
//                        error.printStackTrace();
//                        onLoadingError(error);
//                    });
//        } else {
//            onCreateRestoreDataObservable()
//                    .toList()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnSubscribe(disposable -> {
//                        onLoadStartWU();
//                    })
//                    .doFinally(() -> {
//                        onLoadFinishWU();
//                    })
//                    .subscribe(repositories -> {
//                        Log.d(LOG_TAG, "doOnSubscribeDB");
//                        onLoadingSuccess(repositories);
//                    }, error -> {
//                        error.printStackTrace();
//                        onLoadingError(error);
//                    });
//        }
//    }

    public void loadDataWBIO(boolean update) {
        if (update) {
            onCreateLoadDataObservableHourlyWBIO()
                    .toList()
                    //.retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> {
                        onLoadStartWBIO();
                        Log.d(LOG_TAG, "onLoadStartWBIO");
                    })
                    .doFinally(() -> {
                        onLoadFinishWBIO();
                    })
                    .subscribe(repositoriesWBIO -> {
                        Log.d(LOG_TAG, "SubscribeNet");
                        onLoadingSuccessWBIO(repositoriesWBIO);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        } else {
            onCreateRestoreDataObservable()
                    .toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> {
                        onLoadStartWBIO();
                        Log.d(LOG_TAG, "onLoadStartWBIO");
                    })
                    .doFinally(() -> {
                        onLoadFinishWBIO();
                    })
                    .subscribe(repositoriesWBIO -> {
                        Log.d(LOG_TAG, "doOnSubscribeDB");
                        onLoadingSuccessWBIO(repositoriesWBIO);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public void loadDataAW(boolean update) {
        if (update) {
            onCreateLoadDataObservableHourlyAW()

                    .toList()
                    .retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableAW -> {
                        onLoadStartAW();
                    })
                    .doFinally(() -> {
                        onLoadFinishAW();
                    })
                    .subscribe(repositoriesAW -> {
                        onLoadingSuccessAW(repositoriesAW);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        } else {
            onCreateRestoreDataObservableHourlyAW()
                    .toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableAW -> {
                        onLoadStartAW();
                    })
                    .doFinally(() -> {
                        onLoadFinishAW();
                    })
                    .subscribe(repositoriesAW -> {
                        onLoadingSuccessAW(repositoriesAW);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public void loadDataDS(boolean update) {
        if (update) {
            onCreateLoadDataObservableHourlyDS()
                    .toList()
                    .retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableDS -> {
                        onLoadStartDS();
                    })
                    .doFinally(() -> {
                        onLoadFinishDS();
                    })
                    .subscribe(repositoriesDS -> {
                        onLoadingSuccessDS(repositoriesDS);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        } else {
            onCreateRestoreDataObservableHourlyDS()
                    .toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableDS -> {
                        onLoadStartDS();
                    })
                    .doFinally(() -> {
                        onLoadFinishDS();
                    })
                    .subscribe(repositoriesDS -> {
                        onLoadingSuccessDS(repositoriesDS);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableHourlyWBIO();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableHourlyAW();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableHourlyDS();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservable();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableHourlyAW();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableHourlyDS();


    public void loadStart(boolean update) {
        loadDataWBIO(update);
        loadDataAW(update);
        loadDataDS(update);

        if (update) {
            try {
                PreferencesHelper.savePreference("lastUpdateHourly", getCurrentTime().toMillis(false));
                //Log.d(LOG_TAG, "Time hourly: " + getCurrentTime() + ". " + update);
                PreferencesHelper.savePreference("ChangeCityHourly", false);
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    public void onLoadStartWBIO() {
        getViewState().showListProgress(1);
        Log.d(LOG_TAG, "onLoadStartWBIO()");
    }

    public void onLoadFinishWBIO() {
        Log.d(LOG_TAG, "onLoadFinishWBIO()");
    }

    public void onLoadStartAW() {
        getViewState().showListProgress(2);
        Log.d(LOG_TAG, "onLoadStartAW()");
    }

    public void onLoadFinishAW() {
        Log.d(LOG_TAG, "onLoadFinishAW()");
    }
    
    public void onLoadStartDS() {
        getViewState().showListProgress(3);
        Log.d(LOG_TAG, "onLoadStartDS()");
    }

    public void onLoadFinishDS() {
        Log.d(LOG_TAG, "onLoadFinishDS()");
    }

    public void onLoadingSuccessWBIO(List<BaseViewModel> items) {
        getViewState().setItems(items);
        getViewState().hideListProgress(1);
        Log.d(LOG_TAG, "onLoadingSuccessWBIO() - after");
    }

    public void onLoadingSuccessAW(List<BaseViewModel> items) {
        getViewState().setItemsAW(items);
        getViewState().hideListProgress(2);
        //Log.d(LOG_TAG, "onLoadingSuccessAW() - after");
    }

    public void onLoadingSuccessDS(List<BaseViewModel> items) {
        getViewState().setItemsDS(items);
        getViewState().hideListProgress(3);
        //Log.d(LOG_TAG, "onLoadingSuccessDS() - after");
    }

    public void onLoadingError(Throwable throwable) {
        getViewState().showError(throwable.getMessage());
    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
    }

    public Time getCurrentTime() {
        Time curTime = new Time(Time.getCurrentTimezone());
        curTime.setToNow();
        return curTime;
    }
}
