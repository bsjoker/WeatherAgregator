package bs.joker.weatheragregator.mvp.presenter;

import android.content.SharedPreferences;
import android.renderscript.Sampler;
import android.text.format.Time;
import android.util.Log;
import android.widget.TimePicker;

import com.arellomobile.mvp.MvpPresenter;

import java.io.InvalidClassException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import bs.joker.weatheragregator.common.manager.NetworkManager;
import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.wunderground.astronomy.SunPhase;
import bs.joker.weatheragregator.model.wunderground.current.CurrentObservation;
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

    public void loadDataAstronomy() {
        onCreateLoadDataObservableAstronomy()
                .retry()
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

    public void loadDataCurrent(boolean update) {
        onLoadingCurrentFromPrefs();

        if (update) {
            onCreateLoadDataObservableCurrent()
                    .retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> {
                        onLoadStart();
                    })
                    .doFinally(() -> {
                        onLoadFinish();
                    })
                    .subscribe(repositorCurrentObservation -> {
                        onLoadingSuccessCurrent(repositorCurrentObservation);
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
                .retry()
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
//    }

    public void loadData(boolean update) {
        onCreateRestoreDataObservable()
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

        if (update) {
            onCreateLoadDataObservable()
                    .toList()
                    .retry()
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
    }

//    public void loadData() {
//        if (mIsInLoading) {
//            return;
//        }
//
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
//        mIsInLoading = true;
//        mNetworkManager.getNetworkObservable()
//                .flatMap(aBoolean -> {
//                    if (!aBoolean) {
//                        return Observable.empty();
//                    }
//                    return aBoolean
//                            ? onCreateLoadDataObservable()
//                            : Observable.empty();
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
//    }

    public void loadDataAW(boolean update) {
//        if (mIsInLoading) {
//            return;
//        }

        onCreateRestoreDataObservableHourlyAW()
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

        if (update) {
            onCreateLoadDataObservableHourlyAW()

                    .toList()
                    .retry()
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
    }

    public void loadDataDS(boolean update) {
        onCreateRestoreDataObservableHourlyDS()
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
        if (update) {
            onCreateLoadDataObservableHourlyDS()
                    .toList()
                    .retry()
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
    }

    public void loadDataDaily5WU(boolean update) {
        onCreateRestoreDataObservableDaily5WU()
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

        if (update) {
            onCreateLoadDataObservableDaily5WU()
                    .toList()
                    .retry()
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
    }

    public void loadDataDaily5AW(boolean update) {
        onCreateRestoreDataObservableDaily5AW()
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

        if (update) {
            onCreateLoadDataObservableDaily5AW()
                    .toList()
                    .retry()
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
    }

    public void loadDataDaily5DS(boolean update) {
        onCreateRestoreDataObservableDaily5DS()
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

        if (update) {
            onCreateLoadDataObservableDaily5DS()
                    .toList()
                    .retry()
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
    }

    public void loadDataWeeklyWU(boolean update) {
        onCreateRestoreDataObservableWeeklyWU()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposableWeeklyWU -> {
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
                })
                .subscribe(repositoriesWeeklyWU -> {
                    onLoadingSuccessWeeklyWU(repositoriesWeeklyWU);
                }, error -> {
                    error.printStackTrace();
                    onLoadingError(error);
                });
        if (update) {
            onCreateLoadDataObservableWeeklyWU()
                    .toList()
                    .retry()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposableWeeklyWU -> {
                        onLoadStart();
                    })
                    .doFinally(() -> {
                        onLoadFinish();
                    })
                    .subscribe(repositoriesWeeklyWU -> {
                        onLoadingSuccessWeeklyWU(repositoriesWeeklyWU);
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
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
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
                        onLoadStart();
                    })
                    .doFinally(() -> {
                        onLoadFinish();
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
                    onLoadStart();
                })
                .doFinally(() -> {
                    onLoadFinish();
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
                        onLoadStart();
                    })
                    .doFinally(() -> {
                        onLoadFinish();
                    })
                    .subscribe(repositoriesWeeklyDS -> {
                        onLoadingSuccessWeeklyDS(repositoriesWeeklyDS);
                    }, error -> {
                        error.printStackTrace();
                        onLoadingError(error);
                    });
        }
    }

    public abstract Observable<SunPhase> onCreateLoadDataObservableAstronomy();

    public abstract Observable<CurrentObservation> onCreateLoadDataObservableCurrent();

    public abstract Observable<Geoname> onCreateLoadDataObservableGeoNames(String query);

    public abstract Observable<BaseViewModel> onCreateLoadDataObservable();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableHourlyAW();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableHourlyDS();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableDaily5WU();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableDaily5AW();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableDaily5DS();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableWeeklyWU();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableWeeklyAW();

    public abstract Observable<BaseViewModel> onCreateLoadDataObservableWeeklyDS();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservable();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableHourlyAW();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableHourlyDS();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableDaily5WU();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableDaily5AW();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableDaily5DS();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyWU();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyAW();

    public abstract Observable<BaseViewModel> onCreateRestoreDataObservableWeeklyDS();

    public void loadStartAstronomy() {
        loadDataAstronomy();
    }

    public void loadStartCurrent(boolean update) {
        loadDataCurrent(update);
    }

    public void loadStartCity(String query) {
        loadCity(query);
    }

    public void loadStart(boolean update) {
        loadData(update);
        loadDataAW(update);
        loadDataDS(update);

        if (update) {
            try {
                PreferencesHelper.savePreference("lastUpdateHourly", getCurrentTime().toMillis(false));
                Log.d(LOG_TAG, "Time hourly: " + getCurrentTime() + ". " + update);
                PreferencesHelper.savePreference("ChangeCityHourly", false);
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadStartDaily(boolean update) {
        loadDataDaily5WU(update);
        loadDataDaily5AW(update);
        loadDataDaily5DS(update);

        if (update) {
            try {
                PreferencesHelper.savePreference("lastUpdateDaily", getCurrentTime().toMillis(false));
                Log.d(LOG_TAG, "Time daily: " + getCurrentTime() + ". " + update);
                PreferencesHelper.savePreference("ChangeCityDaily", false);
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadStartWeekly(boolean update) {
        loadDataWeeklyWU(update);
        loadDataWeeklyAW(update);
        loadDataWeeklyDS(update);

        if (update) {
            try {
                PreferencesHelper.savePreference("lastUpdateWeekly", getCurrentTime().toMillis(false));
                Log.d(LOG_TAG, "Time weekly: " + getCurrentTime() + ". " + update);
                PreferencesHelper.savePreference("ChangeCityWeekly", false);
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
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

    public void onLoadingSuccessAstronomy(SunPhase item) {

        Time curTime = new Time(Time.getCurrentTimezone());
        curTime.setToNow();

        Time sunRise = new Time(Time.getCurrentTimezone());
        Time sunSet = new Time(Time.getCurrentTimezone());
        sunRise.set(0, Integer.parseInt(item.getSunrise().getMinute()), Integer.parseInt(item.getSunrise().getHour()), curTime.monthDay, curTime.month, curTime.year);
        sunSet.set(0, Integer.parseInt(item.getSunset().getMinute()), Integer.parseInt(item.getSunset().getHour()), curTime.monthDay, curTime.month, curTime.year);

        try {
            PreferencesHelper.savePreference("sunRise", sunRise.toMillis(false));
            PreferencesHelper.savePreference("sunSet", sunSet.toMillis(false));
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "onLoadingSuccessCurrentAstronomy() - before " + item.getSunrise().getHour());
        //Boolean day = sunRise.before(curTime) && sunSet.after(curTime);
    }

    public void onLoadingCurrentFromPrefs() {
        CurrentObservation item = new CurrentObservation();

        item.setTempC(Double.longBitsToDouble(PreferencesHelper.getSharedPreferences().getLong("curTemp", 0)));
        item.setWeather(PreferencesHelper.getSharedPreferences().getString("curCond", "Н/Д"));
        item.setIcon(PreferencesHelper.getSharedPreferences().getString("curIcon", "Н/Д"));

        Time time = new Time(Time.getCurrentTimezone());
        time.set(PreferencesHelper.getSharedPreferences().getLong("lastUpdate", 0l));
        getViewState().setCurrentCond(item, time);
    }

    public void onLoadingSuccessCurrent(CurrentObservation item) {
        Log.d(LOG_TAG, "onLoadingSuccessCurrentWU() - before ");

        getViewState().setCurrentCond(item, getCurrentTime());

        try {
            PreferencesHelper.savePreference("lastUpdate", getCurrentTime().toMillis(false));
            PreferencesHelper.savePreference("curTemp", Double.doubleToRawLongBits(item.getTempC()));
            PreferencesHelper.savePreference("curCond", item.getWeather());
            PreferencesHelper.savePreference("curIcon", item.getIcon());
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "onLoadingSuccessCurrentWU() - after " + getCurrentTime());
    }

    public void onLoadingCitySuccess(List<Geoname> items) {
        Log.d(LOG_TAG, "onLoadingCitySuccess() - before " + items.size());
        if (items.size() > 0) {
            Log.d(LOG_TAG, "onLoadingCitySuccess() - name: " + items.get(0).getName());
        }
        getViewState().setItemsCIty(items);
        Log.d(LOG_TAG, "onLoadingCitySuccess() - after");
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

    private void onLoadingSuccessWeeklyWU(List<BaseViewModel> items) {
        Log.d(LOG_TAG, "onLoadingSuccessWeekly_WU() - before " + items.size());
        getViewState().setItemsWeeklyWU(items);
        Log.d(LOG_TAG, "onLoadingSuccessWeekly_WU() - after");
    }

    private void onLoadingSuccessWeeklyAW(List<BaseViewModel> items) {
        Log.d(LOG_TAG, "onLoadingSuccessWeekly_AW() - before " + items.size());
        getViewState().setItemsWeeklyAW(items);
        Log.d(LOG_TAG, "onLoadingSuccessWeekly_AW() - after");
    }

    private void onLoadingSuccessWeeklyDS(List<BaseViewModel> items) {
        Log.d(LOG_TAG, "onLoadingSuccessWeekly_DS() - before " + items.size());
        getViewState().setItemsWeeklyDS(items);
        Log.d(LOG_TAG, "onLoadingSuccessWeekly_DS() - after");
    }

    public void onLoadingError(Throwable throwable) {
        getViewState().showError(throwable.getMessage());
    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
        Log.d(LOG_TAG, "saveToDb");
    }

    public Time getCurrentTime() {
        Time curTime = new Time(Time.getCurrentTimezone());
        curTime.setToNow();
        return curTime;
    }
}
