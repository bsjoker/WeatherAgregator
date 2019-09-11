package bs.joker.weatherforecast.di.component;

import javax.inject.Singleton;

import bs.joker.weatherforecast.common.manager.NetworkManager;
import bs.joker.weatherforecast.di.module.ApplicationModule;
import bs.joker.weatherforecast.di.module.ManagerModule;
import bs.joker.weatherforecast.di.module.RestModule;
import bs.joker.weatherforecast.di.module.StorageModule;
import bs.joker.weatherforecast.mvp.presenter.DailyForecastPresenter;
import bs.joker.weatherforecast.mvp.presenter.ForecastPresenter;
import bs.joker.weatherforecast.mvp.presenter.HourlyForecastPresenter;
import bs.joker.weatherforecast.mvp.presenter.WeeklyForecastPresenter;
import bs.joker.weatherforecast.ui.activity.BaseActivity;
import bs.joker.weatherforecast.ui.activity.ScrollActivity;
import bs.joker.weatherforecast.ui.activity.SearchActivity;
import bs.joker.weatherforecast.ui.frgment.Daily5ForecastFragment;
import bs.joker.weatherforecast.ui.frgment.HourlyForecastFragment;
import bs.joker.weatherforecast.ui.frgment.SearchCityFragment;
import bs.joker.weatherforecast.ui.frgment.WeeklyForecastFragment;
import bs.joker.weatherforecast.ui.holder.ForecastDaily5ViewHolder;
import bs.joker.weatherforecast.ui.holder.ForecastHourlyViewHolder;
import bs.joker.weatherforecast.ui.holder.ForecastWeeklyViewHolder;
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
