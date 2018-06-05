package bs.joker.weatheragregator.ui.frgment;

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

import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.common.adapter.HourlyForecastAdapter;
import bs.joker.weatheragregator.common.adapter.HourlyForecastAdapterAW;
import bs.joker.weatheragregator.common.adapter.HourlyForecastAdapterDS;
import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.mvp.presenter.BaseDailyPresenter;
import bs.joker.weatheragregator.mvp.view.DailyForecastView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bakays on 15.01.2018.
 */

public abstract class BaseDaily5ForecastFragment extends BaseFragment implements DailyForecastView {
    public static final String TAG = "BaseDaily5Fragment";
    @BindView(R.id.forecast_weather_recycler_view_wu)
    RecyclerView mRecyclerViewWU;
    @BindView(R.id.forecast_weather_recycler_view_aw)
    RecyclerView mRecyclerViewAW;
    @BindView(R.id.forecast_weather_recycler_view_ds)
    RecyclerView mRecyclerViewDS;

    @BindView(R.id.logo_wundeground)
    ImageView logo_wu;
    @BindView(R.id.logo_accuweather)
    ImageView logo_aw;
    @BindView(R.id.logo_darksky)
    ImageView logo_ds;
    @BindView(R.id.divider_wu)
    ImageView divider;

    @BindView(R.id.progressBarWU)
    ProgressBar mProgressBarWU;
    @BindView(R.id.progressBarAW)
    ProgressBar mProgressBarAW;
    @BindView(R.id.progressBarDS)
    ProgressBar mProgressBarDS;

    private Unbinder mUnbinder;

    HourlyForecastAdapter mHourlyForecastAdapter;
    HourlyForecastAdapterAW mHourlyForecastAdapterAW;
    HourlyForecastAdapterDS mHourlyForecastAdapterDS;

    protected BaseDailyPresenter mBaseDailyPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this,view);
        mUnbinder = ButterKnife.bind(this,view);

        Time curTime = new Time(Time.getCurrentTimezone());
        curTime.setToNow();

        Long last_up = PreferencesHelper.getSharedPreferences().getLong("lastUpdateDaily", 0l);

        boolean update = ((curTime.toMillis(false) - last_up)>3600000l);
        Log.d(TAG, "State before (daily): " + update);
        mBaseDailyPresenter = onCreateBasePresenter();

        if (PreferencesHelper.getSharedPreferences().getBoolean("ChangeCityDaily", false)) {
            update = true;
        }
        Log.d(TAG, "State (daily): " + update);
        mBaseDailyPresenter.loadStartDaily(update);

        logo_wu.setImageResource(R.drawable.wunderground_logo);
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
    public void setItemsD5WU(List<BaseViewModel> items) {
        Log.d(TAG, "Size: " + items.size());
        mHourlyForecastAdapter.setItems(items);
    }

    @Override
    public void setItemsD5AW(List<BaseViewModel> items) {
        Log.d(TAG, "Size: " + items.size());
        mHourlyForecastAdapterAW.setItems(items);
    }

    @Override
    public void setItemsD5DS(List<BaseViewModel> items) {
        Log.d(TAG, "Size: " + items.size());
        mHourlyForecastAdapterDS.setItems(items);
    }

    @Override
    public void showListProgressD5(int n) {
        switch (n){
            case 1:
                mProgressBarWU.setVisibility(View.VISIBLE);
                Log.d(TAG, "ShowProgressWU");
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
    public void hideListProgressD5(int n) {
        switch (n) {
            case 1:
                mProgressBarWU.setVisibility(View.GONE);
                Log.d(TAG, "HideProgressWU");
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

    protected abstract BaseDailyPresenter onCreateBasePresenter();
}

