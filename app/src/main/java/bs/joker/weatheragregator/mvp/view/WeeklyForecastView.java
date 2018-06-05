package bs.joker.weatheragregator.mvp.view;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import bs.joker.weatheragregator.model.view.BaseViewModel;

/**
 * Created by 1 on 07.05.2018.
 */

public interface WeeklyForecastView extends MainView {
    void setItemsWeeklyWU(List<BaseViewModel> items);
    void setItemsWeeklyAW(List<BaseViewModel> items);
    void setItemsWeeklyDS(List<BaseViewModel> items);
    void showListProgressWeekly(int n);
    void hideListProgressWeekly(int n);
}
