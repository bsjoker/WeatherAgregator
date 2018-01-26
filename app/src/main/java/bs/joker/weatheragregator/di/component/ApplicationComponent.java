package bs.joker.weatheragregator.di.component;

import javax.inject.Singleton;

import bs.joker.weatheragregator.common.manager.NetworkManager;
import bs.joker.weatheragregator.di.module.ApplicationModule;
import bs.joker.weatheragregator.di.module.ManagerModule;
import bs.joker.weatheragregator.di.module.RestModule;
import bs.joker.weatheragregator.mvp.presenter.ForecastPresenter;
import bs.joker.weatheragregator.ui.activity.ScrollingActivity;
import bs.joker.weatheragregator.ui.frgment.Daily5ForecastFragment;
import bs.joker.weatheragregator.ui.frgment.HourlyForecastFragment;
import bs.joker.weatheragregator.ui.holder.ForecastDaily5ViewHolder;
import bs.joker.weatheragregator.ui.holder.ForecastHourlyViewHolder;
import dagger.Component;

/**
 * Created by bakays on 06.12.2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ManagerModule.class, RestModule.class})
public interface ApplicationComponent {

    //Activities
    void inject(ScrollingActivity activity);

    //Fragments
    void inject(HourlyForecastFragment fragment);
    void inject(Daily5ForecastFragment fragment);

    //Holders
    void inject(ForecastHourlyViewHolder holder);
    void inject(ForecastDaily5ViewHolder holder);

    //Presenters
    void inject(ForecastPresenter presenter);

    //Managers
    void inject(NetworkManager manager);
}
