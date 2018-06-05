package bs.joker.weatheragregator.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;


import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.common.utils.ConvertDescriptionCode;
import bs.joker.weatheragregator.common.utils.DayNight;
import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.wunderground.current.CurrentObservation;
import bs.joker.weatheragregator.mvp.presenter.BasePresenter;
import bs.joker.weatheragregator.mvp.presenter.ForecastPresenter;
import bs.joker.weatheragregator.mvp.presenter.HourlyForecastPresenter;
import bs.joker.weatheragregator.mvp.view.BaseView;
import bs.joker.weatheragregator.mvp.view.HourlyForecastView;
import bs.joker.weatheragregator.ui.frgment.BaseHourlyForecastFragment;
import bs.joker.weatheragregator.ui.frgment.HourlyForecastFragment;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bakays on 01.02.2018.
 */

public class ScrollActivity extends BaseActivity implements BaseView {
    public static final String TAG = "ScrollActivity";

    @InjectPresenter
    ForecastPresenter mForecastPresenter;

    private Drawer mDrawer;
    private AccountHeader mAccountHeader;
    private List<Geoname> cities;
    public static ScrollActivity link;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        link = this;
        MyApplication.getApplicationComponent().inject(this);

        checkLangChange();

        setUpDrawer();
        mForecastPresenter.loadStartAstronomy();
        loadCurrentData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_unit:
                if (PreferencesHelper.getSharedPreferences().getBoolean("metric", true)){
                    try {
                        Log.d(TAG, "Metric: " + true);
                        PreferencesHelper.savePreference("metric", false);
                        PreferencesHelper.savePreference("ChangeCityHourly", true);
                        PreferencesHelper.savePreference("ChangeCityDaily", true);
                        PreferencesHelper.savePreference("ChangeCityWeekly", true);
                    } catch (InvalidClassException e) {
                        e.printStackTrace();
                    }
                    mForecastPresenter.loadStartCurrent(true);
                    setAdapter();
                } else {
                    try {
                        Log.d(TAG, "Metric: " + false);
                        PreferencesHelper.savePreference("metric", true);
                        PreferencesHelper.savePreference("ChangeCityHourly", true);
                        PreferencesHelper.savePreference("ChangeCityDaily", true);
                        PreferencesHelper.savePreference("ChangeCityWeekly", true);
                    } catch (InvalidClassException e) {
                        e.printStackTrace();
                    }
                    mForecastPresenter.loadStartCurrent(true);
                    setAdapter();
                }
                return true;
            case R.id.action_refresh:
                mForecastPresenter.loadStartCurrent(true);
                setAdapter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkLangChange() {
        String langOld = PreferencesHelper.getSharedPreferences().getString("langOld", "en");
        Log.d(TAG, langOld + "/" + this.getResources().getConfiguration().locale.getLanguage());
        if (langOld.contains(this.getResources().getConfiguration().locale.getLanguage())) {
            Log.d(TAG, "Lang don't change!");
        } else {
            Log.d(TAG, "Name changed!");
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(inRealm -> {
                String currentCity = PreferencesHelper.getSharedPreferences().getString("CurrentCity", "");
                Log.d(TAG, "CurCity: " + currentCity);
                RealmResults<Geoname> realmResults = realm.where(Geoname.class).equalTo("name", currentCity).findAll();
                try {
                    PreferencesHelper.savePreference("CurrentCity", realmResults.get(0).getToponymName());
                } catch (InvalidClassException e) {
                    e.printStackTrace();
                }
            });
            changeNameAndToponymName();
            try {
                PreferencesHelper.savePreference("langOld", this.getResources().getConfiguration().locale.getLanguage());
                Log.d(TAG, "LangOld: " + this.getResources().getConfiguration().locale.getLanguage());
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeNameAndToponymName() {
        List<Geoname> newList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(inRealm -> {

            RealmResults<Geoname> realmResults = realm.where(Geoname.class).findAll();
            for (Geoname geoname : realmResults) {
                String name = geoname.getName();
                String toponymName = geoname.getToponymName();
                geoname.setName(toponymName);
                geoname.setToponymName(name);
                newList.add(geoname);
            }
        });

        for (Geoname geoname : newList) {
            mForecastPresenter.saveToDb(geoname);
        }
    }

    public void loadCurrentData() {
        Time curTime = new Time(Time.getCurrentTimezone());
        curTime.setToNow();

        Long last_up = PreferencesHelper.getSharedPreferences().getLong("lastUpdate", 0l);

        boolean update = ((curTime.toMillis(false) - last_up) > 600000l);
        Log.d(TAG, "State: " + update + ". CurTime: " + curTime.toMillis(false) + ". Last_up: " + last_up + ". Minus: " + (curTime.toMillis(false) - last_up));
        mForecastPresenter.loadStartCurrent(update);
    }

    public void setUpDrawer() {
        ProfileSettingDrawerItem item1 = new ProfileSettingDrawerItem().withIdentifier(1111).withName(R.string.screen_name_news)
                .withIcon(GoogleMaterial.Icon.gmd_add);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Geoname> realmResults = realm.where(Geoname.class).findAll();
        cities = realm.copyFromRealm(realmResults);
        realm.commitTransaction();
        realm.close();

        int s = 0;

        String currentCity = PreferencesHelper.getSharedPreferences().getString("CurrentCity", "");

        for (Geoname geoname : cities) {
                if (currentCity.contains(geoname.getName())) {
                    long pos = s;
                    try {
                        PreferencesHelper.savePreference("SelectCity", pos);
                    } catch (InvalidClassException e) {
                        e.printStackTrace();
                    }
                }
            s++;
        }

        IProfile profile = new ProfileDrawerItem().withName(PreferencesHelper.getSharedPreferences().getString("CurrentCity", "Нижневартовск")).withIdentifier(1);
        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.bg_drawer)
                .withProfileImagesVisible(false)
                .withSelectionListEnabled(false)
                .addProfiles(profile)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withFooter(R.layout.footer)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(mAccountHeader)
                .build();

        for (int i = 0; i < cities.size(); i++) {
                mDrawer.addItem(new PrimaryDrawerItem().withIdentifier(i).withName(cities.get(i).getName()));
        }

        mDrawer.addItems(new DividerDrawerItem(), item1);

        Long selectedCity = PreferencesHelper.getSharedPreferences().getLong("SelectCity", 100L);

        if (selectedCity != 100) {
            mDrawer.setSelection(selectedCity);
        } else {
            Log.d(TAG, "SelectedCity: " + selectedCity);
        }
        mDrawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (drawerItem != null && drawerItem.getIdentifier() == 1111) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent);
                } else {
                    //Log.d(TAG, "Name: " + String.valueOf(((PrimaryDrawerItem) drawerItem).getName()));
                    mAccountHeader.clear();
                    mAccountHeader.setActiveProfile(new ProfileDrawerItem().withName(String.valueOf(((Nameable) drawerItem).getName())));//ActiveProfile(profile.withName("eee"));

                    try {
                        PreferencesHelper.savePreference("CurrentCity", String.valueOf(((Nameable) drawerItem).getName()));
                        PreferencesHelper.savePreference("ChangeCityHourly", true);
                        PreferencesHelper.savePreference("ChangeCityDaily", true);
                        PreferencesHelper.savePreference("ChangeCityWeekly", true);
                        PreferencesHelper.savePreference("SelectCity", drawerItem.getIdentifier());
                    } catch (InvalidClassException e) {
                        e.printStackTrace();
                    }

                    checkLangChange();
                    mForecastPresenter.loadStartCurrent(true);
                    setAdapter();
                    Log.d(TAG, "Name: " + String.valueOf(((PrimaryDrawerItem) drawerItem).getName()));
                }
                return false;
            }
        });

        mDrawer.setOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                if (drawerItem != null && drawerItem.getIdentifier() != 1111) {
                    mDrawer.getItemAdapter().remove(position);
                    realm.beginTransaction();
                    RealmResults<Geoname> results = realm.where(Geoname.class).equalTo("name", String.valueOf(((Nameable) drawerItem).getName())).findAll();
                    results.deleteAllFromRealm();
                    realm.commitTransaction();
                }
                return false;
            }
        });
    }

    @Override
    public void showError(String message) {
        Log.d(TAG, "Error: " + message);
    }

    @Override
    public void setCurrentCond(CurrentObservation currentCond, Time currentTime) {
        Log.d(TAG, "Current cond: " + currentTime);
        if (PreferencesHelper.getSharedPreferences().getBoolean("metric", true)) {
            int temp = (int) Math.round(currentCond.getTempC());
            int windspeed = (int) Math.round(currentCond.getWindKph() * 0.28);
            temperature.setText(String.valueOf(temp) + getString(R.string.degrees));
            tv_time_last_update.setText(getString(R.string.lastUpdate) + " " + currentTime.format("%H:%M"));
            wind.setText(getString(R.string.windSpeed) + " " + windspeed + " " + getString(R.string.speed_metric));
        } else {
            int temp = (int) Math.round(currentCond.getTempF());
            int windspeed = (int) Math.round(currentCond.getWindMph());
            temperature.setText(String.valueOf(temp) + getString(R.string.degrees));
            tv_time_last_update.setText(getString(R.string.lastUpdate) + " " + currentTime.format("%H:%M"));
            wind.setText(getString(R.string.windSpeed) + " " + windspeed + " " + getString(R.string.speed_english));
        }
        boolean isDay = DayNight.isDay(currentTime.toMillis(false));

        collapsing_toolbar_layout.setTitle(mAccountHeader.getActiveProfile().getName().getText());
        collapsing_toolbar_layout.setExpandedTitleTextAppearance(R.style.AppTheme_ExpandedAppBar);

        tv_condition.setText(currentCond.getWeather());

        switch (ConvertDescriptionCode.convertCodeD5WU(currentCond.getIcon())) {
            case 1:
                if (isDay) {
                    icon_cond.setImageResource(R.drawable.clear_sky_d);
                } else {
                    icon_cond.setImageResource(R.drawable.clear_sky_n);
                }
                break;
            case 2:
            case 7:
            case 8:
                if (isDay) {
                    icon_cond.setImageResource(R.drawable.mostly_sunny_d);
                } else {
                    icon_cond.setImageResource(R.drawable.mostly_sunny_n);
                }
                break;
            case 3:
                if (isDay) {
                    icon_cond.setImageResource(R.drawable.mostly_cloudy_d);
                } else {
                    icon_cond.setImageResource(R.drawable.mostly_cloudy_n);
                }
                break;
            case 4:
                icon_cond.setImageResource(R.drawable.cloudy_d);
                break;
            case 5:
            case 6:
                icon_cond.setImageResource(R.drawable.fog);
                break;
            case 9:
            case 18:
            case 19:
            case 20:
            case 21:
            case 24:
                icon_cond.setImageResource(R.drawable.snow);
                break;
            case 10:
            case 11:
                icon_cond.setImageResource(R.drawable.shower);
                break;
            case 12:
            case 13:
                icon_cond.setImageResource(R.drawable.rain);
                break;
            case 14:
            case 15:
                icon_cond.setImageResource(R.drawable.thunderstorm);
                break;
            case 16:
                icon_cond.setImageResource(R.drawable.ice_pellets);
                break;
            case 17:
                icon_cond.setImageResource(R.drawable.na);
                break;
            case 22:
            case 23:
                icon_cond.setImageResource(R.drawable.ice_pellets);
                break;
            case 25:
                icon_cond.setImageResource(R.drawable.snow_rain);
                break;
            case 26:
                icon_cond.setImageResource(R.drawable.windy);
                break;
            case 27:
                icon_cond.setImageResource(R.drawable.low_temp);
                break;
            case 28:
                icon_cond.setImageResource(R.drawable.high_temp);
                break;
            default:
                icon_cond.setImageResource(R.drawable.na);
                break;
        }
    }

    @Override
    public void setItemsCIty(List<Geoname> items) {
        Log.d(TAG, "Size list of geoname: " + items.size());
    }

    @Override
    public void setCityKeyAWToDatabase(String key) {
        Log.d(TAG, "CityKey: " + key);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //checkLangChange();
        setUpDrawer();

        Log.d(TAG, "onResume");
        mForecastPresenter.loadStartCurrent(true);
    }
}
