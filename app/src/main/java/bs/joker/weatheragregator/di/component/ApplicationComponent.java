package bs.joker.weatheragregator.di.component;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.common.manager.NetworkManager;
import bs.joker.weatheragregator.common.utils.LocalStorage;
import bs.joker.weatheragregator.di.module.ApplicationModule;
import bs.joker.weatheragregator.di.module.ManagerModule;
import bs.joker.weatheragregator.di.module.RestModule;
import bs.joker.weatheragregator.di.module.StorageModule;
import bs.joker.weatheragregator.mvp.presenter.DailyForecastPresenter;
import bs.joker.weatheragregator.mvp.presenter.ForecastPresenter;
import bs.joker.weatheragregator.mvp.presenter.HourlyForecastPresenter;
import bs.joker.weatheragregator.mvp.presenter.WeeklyForecastPresenter;
import bs.joker.weatheragregator.ui.activity.BaseActivity;
import bs.joker.weatheragregator.ui.activity.ScrollActivity;
import bs.joker.weatheragregator.ui.activity.SearchActivity;
import bs.joker.weatheragregator.ui.frgment.Daily5ForecastFragment;
import bs.joker.weatheragregator.ui.frgment.HourlyForecastFragment;
import bs.joker.weatheragregator.ui.frgment.SearchCityFragment;
import bs.joker.weatheragregator.ui.frgment.WeeklyForecastFragment;
import bs.joker.weatheragregator.ui.holder.ForecastDaily5ViewHolder;
import bs.joker.weatheragregator.ui.holder.ForecastHourlyViewHolder;
import bs.joker.weatheragregator.ui.holder.ForecastWeeklyViewHolder;
import dagger.Component;

/**
 * Created by bakays on 06.12.2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class,  ManagerModule.class, RestModule.class, StorageModule.class})
public interface ApplicationComponent {

    //LocalStorage mLocalStorage();

    //Activities
    void inject(BaseActivity activity);
    void inject(ScrollActivity activity);
    void inject(SearchActivity activity);

    //Fragments
    void inject(HourlyForecastFragment fragment);
    void inject(Daily5ForecastFragment fragment);
    void inject(WeeklyForecastFragment fragment);
    void inject(SearchCityFragment fragment);

    //Holders
    void inject(ForecastHourlyViewHolder holder);
    void inject(ForecastDaily5ViewHolder holder);
    void inject(ForecastWeeklyViewHolder holder);

    //Presenters
    void inject(ForecastPresenter presenter);
    void inject(HourlyForecastPresenter presenter);
    void inject(DailyForecastPresenter presenter);
    void inject(WeeklyForecastPresenter presenter);

    //Managers
    void inject(NetworkManager manager);
}
