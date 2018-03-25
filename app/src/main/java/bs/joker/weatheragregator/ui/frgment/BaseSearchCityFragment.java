package bs.joker.weatheragregator.ui.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.common.adapter.MyItemClickListener;
import bs.joker.weatheragregator.common.adapter.SearchRecyclerViewAdapter;
import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.wunderground.current.CurrentObservation;
import bs.joker.weatheragregator.mvp.presenter.BasePresenter;
import bs.joker.weatheragregator.mvp.view.BaseView;
import bs.joker.weatheragregator.ui.activity.ScrollActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by 1 on 13.03.2018.
 */

public abstract class BaseSearchCityFragment extends BaseFragmentSearch implements BaseView {
    @BindView(R.id.rv_city_search_list)
    RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private List<Geoname> data;

    protected BasePresenter mBasePresenter;

    SearchRecyclerViewAdapter mSearchRecyclerViewAdapter;
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
    public void showError(String message) {

    }

    @Override
    public void setCurrentCond(CurrentObservation currentCond, Time currentTime) {

    }

    @Override
    public void setItems(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsAW(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsDS(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsD5WU(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsD5AW(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsD5DS(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsWeeklyWU(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsWeeklyAW(List<BaseViewModel> items) {

    }

    @Override
    public void setItemsWeeklyDS(List<BaseViewModel> items) {

    }
    protected abstract BasePresenter onCreateBasePresenter();
}
