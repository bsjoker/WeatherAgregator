package bs.joker.weatheragregator.ui.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.mvp.presenter.BaseHourlyPresenter;
import bs.joker.weatheragregator.mvp.presenter.BasePresenter;
import bs.joker.weatheragregator.mvp.presenter.ForecastPresenter;
import bs.joker.weatheragregator.mvp.presenter.HourlyForecastPresenter;
import bs.joker.weatheragregator.rest.api.WeatherApi;

/**
 * Created by BakayS on 11.12.2017.
 */


public class HourlyForecastFragment extends BaseHourlyForecastFragment {
    Integer position;

    @Inject
    WeatherApi mWeatherApi;

    @InjectPresenter
    HourlyForecastPresenter mHourlyForecastPresenter;

//    public HourlyForecastFragment(){
//    }

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

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        savedInstanceState = null;
//        super.onActivityCreated(savedInstanceState);
//    }

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

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        //savedInstanceState = null;
//        super.onViewStateRestored(savedInstanceState);
//
//        if(savedInstanceState!=null) {
//            Log.d("HourlyForecastFragment", "onViewStateRestored");
//        } else{
//            Log.d("HourlyForecastFragment", "onViewStateRestored - empty");
//        }
//    }

    @Override
    protected BaseHourlyPresenter onCreateBasePresenter() {
        return mHourlyForecastPresenter;
    }
}
