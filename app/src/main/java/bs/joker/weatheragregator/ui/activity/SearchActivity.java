package bs.joker.weatheragregator.ui.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;

import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;


import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.common.manager.MyFragmentManager;
import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.weatherbitio.currentConditions.DatumCurWeatherbitio;
import bs.joker.weatheragregator.model.wunderground.current.CurrentObservation;
import bs.joker.weatheragregator.mvp.presenter.ForecastPresenter;
import bs.joker.weatheragregator.mvp.view.BaseView;
import bs.joker.weatheragregator.ui.frgment.SearchCityFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by 1 on 13.03.2018.
 */

public class SearchActivity extends MvpAppCompatActivity implements BaseView {
    public static final String TAG = "SearchActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvNote)
    TextView mNote;

    private Unbinder mUnbinder;

    SearchCityFragment searchCityFragment;

    @InjectPresenter
    ForecastPresenter mForecastPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication.getApplicationComponent().inject(this);

        setContentView(R.layout.search_activity);

        try {
            PreferencesHelper.savePreference("lang", this.getResources().getConfiguration().locale.getLanguage());
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }

        mUnbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
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
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
            RxSearchView.queryTextChanges(searchView)
                    .map(charSequence -> charSequence == null ? null : charSequence.toString().trim())
                    //.throttleLast(100, TimeUnit.MILLISECONDS)
                    .debounce(200, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(searchText -> {
                        mNote.setVisibility(View.GONE);
                        mForecastPresenter.loadStartCity(searchText);
                    });
            return super.onOptionsItemSelected(item);
        }

    @Override
    public void showError(String message) {

    }

    @Override
    public void setCurrentCond(DatumCurWeatherbitio currentCond, Time currentTime) {

    }

    @Override
    public void setCityKeyAWToDatabase(String key) {
        Log.d(TAG, "City Key AW To Database: " + key);
        searchCityFragment.setCityKeyAW(key);
    }

    @Override
    public void setItemsCIty(List<Geoname> items) {
        Log.d(TAG, "Size list of geoname: " + items.size());
        searchCityFragment.setData(items);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
