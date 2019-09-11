package bs.joker.weatherforecast.ui.frgment;

import android.text.format.Time;

import java.util.List;

import bs.joker.weatherforecast.model.geonames.Geoname;
import bs.joker.weatherforecast.model.weatherbitio.currentConditions.DatumCurWeatherbitio;
import bs.joker.weatherforecast.mvp.presenter.BasePresenter;
import bs.joker.weatherforecast.mvp.view.BaseView;

/**
 * Created by 1 on 13.03.2018.
 */

public abstract class BaseSearchCityFragment extends BaseFragmentSearch implements BaseView {

    protected BasePresenter mBasePresenter;

    @Override
    public void showError(String message) {

    }

    @Override
    public void setCurrentCond(DatumCurWeatherbitio currentCond, Time currentTime) {

    }

    @Override
    public void setItemsCIty(List<Geoname> items) {

    }

    protected abstract BasePresenter onCreateBasePresenter();
}
