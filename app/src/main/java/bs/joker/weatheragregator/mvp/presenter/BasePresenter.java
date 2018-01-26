package bs.joker.weatheragregator.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import bs.joker.weatheragregator.common.manager.NetworkManager;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.mvp.view.BaseView;
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
    private boolean mIsInLoading;

    @Inject
    NetworkManager mNetworkManager;

    public void loadData() {
        if (mIsInLoading) {
            return;
        }

        mIsInLoading = true;

        onCreateLoadDataObservable()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
                })
                .subscribe(repositories -> {
                    onLoadingSuccess(repositories);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
    }

    public void loadDataAW() {
        onCreateLoadDataObservableHourlyAW()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableAW -> {
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
                })
                .subscribe(repositoriesAW -> {
                    onLoadingSuccessAW(repositoriesAW);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
    }

    public void loadDataDS() {
        onCreateLoadDataObservableHourlyDS()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableDS -> {
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
                })
                .subscribe(repositoriesDS -> {
                    onLoadingSuccessDS(repositoriesDS);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
    }

    public void loadDataDaily5WU() {
        onCreateLoadDataObservableDaily5WU()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableD5WU -> {
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
                })
                .subscribe(repositoriesD5WU -> {
                    onLoadingSuccessD5WU(repositoriesD5WU);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
    }

    public void loadDataDaily5AW() {
        onCreateLoadDataObservableDaily5AW()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableD5AW -> {
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
                })
                .subscribe(repositoriesD5AW -> {
                    onLoadingSuccessD5AW(repositoriesD5AW);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
    }

    public void loadDataDaily5DS() {
        onCreateLoadDataObservableDaily5DS()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableD5DS -> {
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
                })
                .subscribe(repositoriesD5DS -> {
                    onLoadingSuccessD5DS(repositoriesD5DS);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
    }


//        mNetworkManager.getNetworkObservable()
//                .flatMap(aBoolean -> {
//                    if (!aBoolean) {
//                        return Observable.empty();
//                    }
//                    return aBoolean
//                            ? onCreateLoadDataObservable()
//                            : onCreateRestoreDataObservable();
//                })
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


    public abstract Observable<BaseViewModel> onCreateLoadDataObservableDaily5WU();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableDaily5AW();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableDaily5DS();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableHourlyAW();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableHourlyDS();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservable();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservable();

    public void loadStart() {
        loadData();
        loadDataAW();
        loadDataDS();
    }

    public void loadStartDaily() {
        loadDataDaily5WU();
        loadDataDaily5AW();
        loadDataDaily5DS();
    }

    public void onLoadStart() {
        //getViewState().showListProgress();
        Log.d(LOG_TAG, "onLoadStart()");
    }

    public void onLoadStartDaily() {
        //getViewState().showListProgress();
        Log.d(LOG_TAG, "onLoadStart()");
    }

    public void onLoadFinish() {
        //getViewState().hideListProgress();
        mIsInLoading = false;
        Log.d(LOG_TAG, "onLoadFinish()");
    }

    public void onLoadDailyFinish() {
        //getViewState().hideListProgress();
        mIsInLoading = false;
        Log.d(LOG_TAG, "onLoadDailyFinish()");
    }

    public void onLoadingSuccess(List<BaseViewModel> items) {
        Log.d(LOG_TAG, "onLoadingSuccessWU() - before " + items.size());

        getViewState().setItems(items);
        Log.d(LOG_TAG, "onLoadingSuccessWU() - after");
    }

    public void onLoadingSuccessAW(List<BaseViewModel> items) {
        Log.d(LOG_TAG, "onLoadingSuccessAW() - before " + items.size());

        getViewState().setItemsAW(items);
        Log.d(LOG_TAG, "onLoadingSuccessAW() - after");
    }

    public void onLoadingSuccessDS(List<BaseViewModel> items) {
        Log.d(LOG_TAG, "onLoadingSuccessDS() - before " + items.size());

        getViewState().setItemsDS(items);
        Log.d(LOG_TAG, "onLoadingSuccessDS() - after");
    }

    private void onLoadingSuccessD5WU(List<BaseViewModel> items) {
        Log.d(LOG_TAG, "onLoadingSuccessD5_WU() - before " + items.size());
        getViewState().setItemsD5WU(items);
        Log.d(LOG_TAG, "onLoadingSuccessD5_WU() - after");
    }

    private void onLoadingSuccessD5AW(List<BaseViewModel> items) {
        Log.d(LOG_TAG, "onLoadingSuccessD5_AW() - before " + items.size());
        getViewState().setItemsD5AW(items);
        Log.d(LOG_TAG, "onLoadingSuccessD5_AW() - after");
    }

    private void onLoadingSuccessD5DS(List<BaseViewModel> items) {
        Log.d(LOG_TAG, "onLoadingSuccessD5_DS() - before " + items.size());
        getViewState().setItemsD5DS(items);
        Log.d(LOG_TAG, "onLoadingSuccessD5_DS() - after");
    }

    public void onLoadingError(Throwable throwable) {
        getViewState().showError(throwable.getMessage());
    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
    }
}
