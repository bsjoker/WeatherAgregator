package bs.joker.weatheragregator.mvp.view;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import bs.joker.weatheragregator.model.view.BaseViewModel;

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
