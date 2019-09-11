package bs.joker.weatherforecast.mvp.view;

import java.util.List;

import bs.joker.weatherforecast.model.view.BaseViewModel;

/**
 * Created by 1 on 04.05.2018.
 */

public interface HourlyForecastView extends MainView {
    void setItems(List<BaseViewModel> items);
    void setItemsAW(List<BaseViewModel> items);
    void setItemsDS(List<BaseViewModel> items);
    void showListProgress(int n);
    void hideListProgress(int n);
}
