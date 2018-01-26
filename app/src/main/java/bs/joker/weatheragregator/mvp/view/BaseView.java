package bs.joker.weatheragregator.mvp.view;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import bs.joker.weatheragregator.model.view.BaseViewModel;

/**
 * Created by bakays on 24.10.2017.
 */

public interface BaseView extends MvpView {
//    void showListProgress();
//    void hideListProgress();
    void showError(String message);
    void setItems(List<BaseViewModel> items);
    void setItemsAW(List<BaseViewModel> items);
    void setItemsDS(List<BaseViewModel> items);
    void setItemsD5WU(List<BaseViewModel> items);
    void setItemsD5AW(List<BaseViewModel> items);
    void setItemsD5DS(List<BaseViewModel> items);
}
