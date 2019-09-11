package bs.joker.weatherforecast.ui.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatherforecast.mvp.presenter.BaseWeeklyPresenter;
import bs.joker.weatherforecast.mvp.presenter.WeeklyForecastPresenter;
import bs.joker.weatherforecast.rest.api.WeatherApi;

/**
 * Created by bakays on 30.01.2018.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklyForecastFragment extends BaseWeeklyForecastFragment {
    int position;

    @Inject
    WeatherApi mWeatherApi;

    @InjectPresenter
    WeeklyForecastPresenter mWeeklyForecastPresenter;

    public static WeeklyForecastFragment newInstance(int pos){
        WeeklyForecastFragment weeklyForecastFragment = new WeeklyForecastFragment();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        weeklyForecastFragment.setArguments(args);
        return weeklyForecastFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position");
        Log.d("WeeklyForecastFragment", "Pos: " + position);
        MyApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected BaseWeeklyPresenter onCreateBasePresenter() {
        return mWeeklyForecastPresenter;
    }
}
