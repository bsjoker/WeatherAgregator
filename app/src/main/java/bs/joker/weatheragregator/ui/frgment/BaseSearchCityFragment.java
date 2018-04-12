package bs.joker.weatheragregator.ui.frgment;

import android.text.format.Time;
import android.util.Log;

import java.util.List;

import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.wunderground.current.CurrentObservation;
import bs.joker.weatheragregator.mvp.presenter.BasePresenter;
import bs.joker.weatheragregator.mvp.view.BaseView;

/**
 * Created by 1 on 13.03.2018.
 */

public abstract class BaseSearchCityFragment extends BaseFragmentSearch implements BaseView {

    protected BasePresenter mBasePresenter;





    @Override
    public void showError(String message) {

    }

    @Override
    public void setCurrentCond(CurrentObservation currentCond, Time currentTime) {

    }

    @Override
    public void setItems(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsAW(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsDS(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsD5WU(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsD5AW(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsD5DS(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsWeeklyWU(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsWeeklyAW(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsWeeklyDS(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsCIty(List<Geoname> items) {

    }



    protected abstract BasePresenter onCreateBasePresenter();
}
