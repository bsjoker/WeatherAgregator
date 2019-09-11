package bs.joker.weatherforecast.ui.frgment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import bs.joker.weatherforecast.R;
import bs.joker.weatherforecast.common.adapter.HourlyForecastAdapter;
import bs.joker.weatherforecast.common.adapter.HourlyForecastAdapterAW;
import bs.joker.weatherforecast.common.adapter.HourlyForecastAdapterDS;
import bs.joker.weatherforecast.model.PreferencesHelper;
import bs.joker.weatherforecast.model.view.BaseViewModel;
import bs.joker.weatherforecast.mvp.presenter.BaseWeeklyPresenter;
import bs.joker.weatherforecast.mvp.view.WeeklyForecastView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bakays on 30.01.2018.
 */

public abstract class BaseWeeklyForecastFragment extends BaseFragment implements WeeklyForecastView {
    public static final String TAG = "BaseWeeklyFragment";

    @BindView(R.id.forecast_weather_recycler_view_wu)
    RecyclerView mRecyclerViewWU;
    @BindView(R.id.forecast_weather_recycler_view_aw)
    RecyclerView mRecyclerViewAW;
    @BindView(R.id.forecast_weather_recycler_view_ds)
    RecyclerView mRecyclerViewDS;

    @BindView(R.id.logo_weatherbit)
    ImageView logo_wbio;
    @BindView(R.id.logo_accuweather)
    ImageView logo_aw;
    @BindView(R.id.logo_darksky)
    ImageView logo_ds;
    @BindView(R.id.divider_wbio)
    ImageView divider;

    @BindView(R.id.progressBarWBIO)
    ProgressBar mProgressBarWBIO;
    @BindView(R.id.progressBarAW)
    ProgressBar mProgressBarAW;
    @BindView(R.id.progressBarDS)
    ProgressBar mProgressBarDS;

    private Unbinder mUnbinder;

    HourlyForecastAdapter mHourlyForecastAdapter;
    HourlyForecastAdapterAW mHourlyForecastAdapterAW;
    HourlyForecastAdapterDS mHourlyForecastAdapterDS;

    protected BaseWeeklyPresenter mBaseWeeklyPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this,view);

        Time curTime = new Time(Time.getCurrentTimezone());
        curTime.setToNow();

        Long last_up = PreferencesHelper.getSharedPreferences().getLong("lastUpdateWeekly", 0l);

        boolean update = ((curTime.toMillis(false) - last_up)>3600000l);
        Log.d(TAG, "State before(weekly): " + update);
        mBaseWeeklyPresenter = onCreateBasePresenter();
        if (PreferencesHelper.getSharedPreferences().getBoolean("ChangeCityWeekly", false)) {
            update = true;
        }
        Log.d(TAG, "State (weekly): " + update);
        mBaseWeeklyPresenter.loadStartWeekly(update);

        logo_wbio.setImageResource(R.drawable.weatherbitio_logo);
        logo_aw.setImageResource(R.drawable.aweather_logo);
        logo_ds.setImageResource(R.drawable.darksky_logo);

        mRecyclerViewWU.setHasFixedSize(true);
        mRecyclerViewAW.setHasFixedSize(true);
        mRecyclerViewDS.setHasFixedSize(true);

        setUpRecyclerView(view);
        setUpAdapret();
    }

    private void setUpRecyclerView(View view) {
        mRecyclerViewWU.setNestedScrollingEnabled(false);
        mRecyclerViewWU.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewWU.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAW.setNestedScrollingEnabled(false);
        mRecyclerViewAW.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewAW.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewDS.setNestedScrollingEnabled(false);
        mRecyclerViewDS.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewDS.setItemAnimator(new DefaultItemAnimator());
    }

    private void setUpAdapret() {
        mHourlyForecastAdapter = new HourlyForecastAdapter();
        mRecyclerViewWU.setAdapter(mHourlyForecastAdapter);
        mHourlyForecastAdapterAW = new HourlyForecastAdapterAW();
        mRecyclerViewAW.setAdapter(mHourlyForecastAdapterAW);
        mHourlyForecastAdapterDS = new HourlyForecastAdapterDS();
        mRecyclerViewDS.setAdapter(mHourlyForecastAdapterDS);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void setItemsWeeklyWBIO(List<BaseViewModel> items) {
        Log.d(TAG, "Size: " + items.size());
        mHourlyForecastAdapter.setItems(items);
    }

    @Override
    public void setItemsWeeklyAW(List<BaseViewModel> items) {
        Log.d(TAG, "Size: " + items.size());
        mHourlyForecastAdapterAW.setItems(items);
    }

    @Override
    public void setItemsWeeklyDS(List<BaseViewModel> items) {
        Log.d(TAG, "Size: " + items.size());
        mHourlyForecastAdapterDS.setItems(items);
    }

    @Override
    public void showListProgressWeekly(int n) {
        switch (n){
            case 1:
                mProgressBarWBIO.setVisibility(View.VISIBLE);
                Log.d(TAG, "ShowProgressWBIO");
                break;
            case 2:
                mProgressBarAW.setVisibility(View.VISIBLE);
                Log.d(TAG, "ShowProgressAW");
                break;
            case 3:
                mProgressBarDS.setVisibility(View.VISIBLE);
                Log.d(TAG, "ShowProgressDS");
                break;
            default:
                Log.d(TAG, "Default.");
        }
    }

    @Override
    public void hideListProgressWeekly(int n) {
        switch (n) {
            case 1:
                mProgressBarWBIO.setVisibility(View.GONE);
                Log.d(TAG, "HideProgressWBIO");
                break;
            case 2:
                mProgressBarAW.setVisibility(View.GONE);
                Log.d(TAG, "HideProgressAW");
                break;
            case 3:
                mProgressBarDS.setVisibility(View.GONE);
                Log.d(TAG, "HideProgressDS");
                break;
            default:
                Log.d(TAG, "Default.");
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    protected abstract BaseWeeklyPresenter onCreateBasePresenter();
}
