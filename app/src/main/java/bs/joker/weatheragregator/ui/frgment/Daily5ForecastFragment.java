package bs.joker.weatheragregator.ui.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.mvp.presenter.BaseDailyPresenter;
import bs.joker.weatheragregator.mvp.presenter.DailyForecastPresenter;
import bs.joker.weatheragregator.rest.api.WeatherApi;

/**
 * Created by bakays on 15.01.2018.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class Daily5ForecastFragment extends BaseDaily5ForecastFragment {
    Integer position;

    @Inject
    WeatherApi mWeatherApi;

    @InjectPresenter
    DailyForecastPresenter mDailyForecastPresenter;

    public static Daily5ForecastFragment newInstance(int pos){
        Daily5ForecastFragment daily5ForecastFragment = new Daily5ForecastFragment();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        daily5ForecastFragment.setArguments(args);
        return daily5ForecastFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position");
        Log.d("Daily5ForecastFragment", "Pos: " + position);

        MyApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected BaseDailyPresenter onCreateBasePresenter() {
        return mDailyForecastPresenter;
    }
}


