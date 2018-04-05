package bs.joker.weatheragregator.ui.frgment;

/**
 * Created by 1 on 13.03.2018.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.common.adapter.MyItemClickListener;
import bs.joker.weatheragregator.common.adapter.SearchRecyclerViewAdapter;
import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.mvp.presenter.BasePresenter;
import bs.joker.weatheragregator.mvp.presenter.ForecastPresenter;
import bs.joker.weatheragregator.rest.api.WeatherApi;
import bs.joker.weatheragregator.ui.activity.ScrollActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCityFragment extends BaseSearchCityFragment {
    @BindView(R.id.rv_city_search_list)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private List<Geoname> data;

    protected BasePresenter mBasePresenter;

    public SearchRecyclerViewAdapter mSearchRecyclerViewAdapter;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this,view);
        mUnbinder = ButterKnife.bind(this,view);

        mBasePresenter = onCreateBasePresenter();

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<>();
        mSearchRecyclerViewAdapter = new SearchRecyclerViewAdapter(data, new MyItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d("BaseSearch", "Clicked: " + data.get(position).getName());
                try {
                    PreferencesHelper.savePreference("CurrentCity", data.get(position).getName());
                } catch (InvalidClassException e) {
                    e.printStackTrace();
                }
                saveToDb(data.get(position));
                getActivity().finish();
            }
        });
        mRecyclerView.setAdapter(mSearchRecyclerViewAdapter);
    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
        Log.d("BaseSearch", "saveToDb");
    }

    public void setData(List<Geoname> cityList){
        this.data = cityList;
        Log.d("BaseSearch", "BaseSearch: " + cityList.size());
        mSearchRecyclerViewAdapter.setItems(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    protected BasePresenter onCreateBasePresenter() {
        return mForecastPresenter;
    }
}
