package bs.joker.weatheragregator.mvp.view;

import android.text.format.Time;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.wunderground.current.CurrentObservation;

/**
 * Created by bakays on 24.10.2017.
 */

public interface BaseView extends MvpView {
//    void showListProgress();
//    void hideListProgress();
    void showError(String message);
    void setCurrentCond(CurrentObservation currentCond, Time currentTime);
    void setCityKeyAWToDatabase(String key);
    void setItems(List<BaseViewModel> items);
    void setItemsAW(List<BaseViewModel> items);
    void setItemsDS(List<BaseViewModel> items);
    void setItemsD5WU(List<BaseViewModel> items);
    void setItemsD5AW(List<BaseViewModel> items);
    void setItemsD5DS(List<BaseViewModel> items);
    void setItemsWeeklyWU(List<BaseViewModel> items);
    void setItemsWeeklyAW(List<BaseViewModel> items);
    void setItemsWeeklyDS(List<BaseViewModel> items);
    void setItemsCIty(List<Geoname> items);
}
