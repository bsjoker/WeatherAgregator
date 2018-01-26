package bs.joker.weatheragregator.ui.frgment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.common.adapter.HourlyForecastAdapter;
import bs.joker.weatheragregator.common.adapter.HourlyForecastAdapterAW;
import bs.joker.weatheragregator.common.adapter.HourlyForecastAdapterDS;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.view.ForecastDaily5ItemViewModel;
import bs.joker.weatheragregator.mvp.presenter.BasePresenter;
import bs.joker.weatheragregator.mvp.view.BaseView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bakays on 15.01.2018.
 */

public abstract class BaseDaily5ForecastFragment extends BaseFragment implements BaseView {
    public static final String TAG = "BaseDaily5Fragment";
    RecyclerView mRecyclerViewWU, mRecyclerViewAW, mRecyclerViewDS;
    ImageView logo_wu, logo_aw, logo_ds, divider;

    private Unbinder mUnbinder;

    private List<ForecastDaily5ItemViewModel> mDaily5Forecasts;

    //DailyForecastAdapter mDailyForecastAdapter;
    HourlyForecastAdapter mHourlyForecastAdapter;
    HourlyForecastAdapterAW mHourlyForecastAdapterAW;
    HourlyForecastAdapterDS mHourlyForecastAdapterDS;

    protected BasePresenter mBasePresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this,view);

        mBasePresenter = onCreateBasePresenter();
        mBasePresenter.loadStartDaily();

        logo_wu = (ImageView) view.findViewById(R.id.logo_wundeground);
        logo_aw = (ImageView) view.findViewById(R.id.logo_accuweather);
        logo_ds = (ImageView) view.findViewById(R.id.logo_darksky);
        logo_wu.setImageResource(R.drawable.wunderground_logo);
        logo_aw.setImageResource(R.drawable.aweather_logo);
        logo_ds.setImageResource(R.drawable.darksky_logo);

        divider = (ImageView) view.findViewById(R.id.divider_wu);

        mRecyclerViewWU = (RecyclerView) view.findViewById(R.id.forecast_weather_recycler_view_wu);
        mRecyclerViewAW = (RecyclerView) view.findViewById(R.id.forecast_weather_recycler_view_aw);
        mRecyclerViewDS = (RecyclerView) view.findViewById(R.id.forecast_weather_recycler_view_ds);
        mRecyclerViewWU.setHasFixedSize(true);
        mRecyclerViewAW.setHasFixedSize(true);
        mRecyclerViewDS.setHasFixedSize(true);

        setUpRecyclerView(view);
        setUpAdapret(mRecyclerViewWU);
        setUpAdapret(mRecyclerViewAW);
        setUpAdapret(mRecyclerViewDS);
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

    private void setUpAdapret(RecyclerView recyclerView) {
//        mDailyForecastAdapter = new DailyForecastAdapter();
//        mRecyclerViewWU.setAdapter(mDailyForecastAdapter);
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
    public void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setItems(List<BaseViewModel> items) {
        Log.d(TAG, "Size: " + items.size());
        mHourlyForecastAdapter.setItems(items);
    }

    @Override
    public void setItemsAW(List<BaseViewModel> items) {
        Log.d(TAG, "Size AW: " + items.size());
        mHourlyForecastAdapterAW.setItems(items);
    }

    @Override
    public void setItemsDS(List<BaseViewModel> items) {
        Log.d(TAG, "Size DS: " + items.size());
        mHourlyForecastAdapterDS.setItems(items);
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

    protected abstract BasePresenter onCreateBasePresenter();
}

