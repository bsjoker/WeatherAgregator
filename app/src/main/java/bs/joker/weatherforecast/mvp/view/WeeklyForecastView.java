package bs.joker.weatherforecast.mvp.view;

import java.util.List;

import bs.joker.weatherforecast.model.view.BaseViewModel;

/**
 * Created by 1 on 07.05.2018.
 */

public interface WeeklyForecastView extends MainView {
    void setItemsWeeklyWBIO(List<BaseViewModel> items);
    void setItemsWeeklyAW(List<BaseViewModel> items);
    void setItemsWeeklyDS(List<BaseViewModel> items);
    void showListProgressWeekly(int n);
    void hideListProgressWeekly(int n);
}
