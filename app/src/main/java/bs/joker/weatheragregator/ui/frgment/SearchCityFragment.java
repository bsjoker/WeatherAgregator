package bs.joker.weatheragregator.ui.frgment;

/**
 * Created by 1 on 13.03.2018.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.mvp.presenter.BasePresenter;
import bs.joker.weatheragregator.mvp.presenter.ForecastPresenter;
import bs.joker.weatheragregator.rest.api.WeatherApi;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCityFragment extends BaseSearchCityFragment {
    @Inject
    WeatherApi mWeatherApi;

    @InjectPresenter
    ForecastPresenter mForecastPresenter;

    public static SearchCityFragment newInstance(){
        SearchCityFragment searchCityFragment = new SearchCityFragment();
        return searchCityFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected BasePresenter onCreateBasePresenter() {
        return mForecastPresenter;
    }
}
