package bs.joker.weatherforecast.ui.frgment;

import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatherforecast.mvp.presenter.BaseHourlyPresenter;
import bs.joker.weatherforecast.mvp.presenter.HourlyForecastPresenter;
import bs.joker.weatherforecast.rest.api.WeatherApi;

/**
 * Created by BakayS on 11.12.2017.
 */


public class HourlyForecastFragment extends BaseHourlyForecastFragment {
    Integer position;

    @Inject
    WeatherApi mWeatherApi;

    @InjectPresenter
    HourlyForecastPresenter mHourlyForecastPresenter;

    public static HourlyForecastFragment newInstance(int pos){
        HourlyForecastFragment hourlyForecastFragment = new HourlyForecastFragment();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        hourlyForecastFragment.setArguments(args);
        return hourlyForecastFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt("position");
        Log.d("HourlyForecastFragment", "onCreate");
        MyApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("HourlyForecastFragment", "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("HourlyForecastFragment", "onDestroyView");
    }

    @Override
    protected BaseHourlyPresenter onCreateBasePresenter() {
        return mHourlyForecastPresenter;
    }
}
