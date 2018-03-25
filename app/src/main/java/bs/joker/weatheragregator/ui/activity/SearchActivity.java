package bs.joker.weatheragregator.ui.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.common.manager.MyFragmentManager;
import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.wunderground.current.CurrentObservation;
import bs.joker.weatheragregator.mvp.presenter.ForecastPresenter;
import bs.joker.weatheragregator.mvp.view.BaseView;
import bs.joker.weatheragregator.rest.model.RxSearchObservable;
import bs.joker.weatheragregator.ui.frgment.SearchCityFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 1 on 13.03.2018.
 */

public class SearchActivity extends MvpAppCompatActivity implements BaseView {
    public static final String TAG = "SearchActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    SearchCityFragment searchCityFragment;

    @InjectPresenter
    ForecastPresenter mForecastPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getApplicationComponent().inject(this);

        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        searchCityFragment = (SearchCityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (searchCityFragment == null){
            searchCityFragment = SearchCityFragment.newInstance();
            MyFragmentManager.addFragmentToActivity(getSupportFragmentManager(), searchCityFragment, R.id.fragment_container);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        List<Geoname> geonameList = new ArrayList<>();
        if (id == R.id.action_search){
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
            RxSearchObservable.fromView(searchView)
                    .debounce(500, TimeUnit.MILLISECONDS)
//                    .filter(new Predicate<String>() {
//                        @Override
//                        public boolean test(String s) throws Exception {
//                            if (s.isEmpty()){
//                                Log.d(TAG, "Is_Empty");
//                                //searchCityFragment.setData(null);
//                                return false;
//                            }else {
//                                Log.d(TAG, "Is_NOT_Empty");
//                                return true;
//                            }
//                        }
//                    })
//                    .distinctUntilChanged()
                    .switchMap(new Function<String, ObservableSource<Geoname>>() {
                        @Override
                        public ObservableSource<Geoname> apply(String query) throws Exception {
                            geonameList.clear();
                            return mForecastPresenter.onCreateLoadDataObservableGeoNames(query);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Geoname>() {
                        @Override
                        public void accept(Geoname geoname) throws Exception {
                            Log.d(TAG, geoname.getName());

                            geonameList.add(geoname);
                            Log.d(TAG, "Quantity: " + geonameList.size());
                            searchCityFragment.setData(geonameList);
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
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
}
