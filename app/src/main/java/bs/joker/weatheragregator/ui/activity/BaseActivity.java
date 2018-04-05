package bs.joker.weatheragregator.ui.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.common.manager.MyFragmentManager;
import bs.joker.weatheragregator.common.utils.LocalStorage;
import bs.joker.weatheragregator.common.utils.SharedPrefStorage;
import bs.joker.weatheragregator.di.component.DaggerApplicationComponent;

import bs.joker.weatheragregator.di.module.StorageModule;
import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.ui.frgment.BaseFragment;
import bs.joker.weatheragregator.ui.frgment.Daily5ForecastFragment;
import bs.joker.weatheragregator.ui.frgment.HourlyForecastFragment;
import bs.joker.weatheragregator.ui.frgment.WeeklyForecastFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends MvpAppCompatActivity {
    public static final String LOG_TAG = "BaseActivity";

    @Inject
    MyFragmentManager mMyFragmentManager;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsing_toolbar_layout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    TabsAdapter tabsAdapter;
    //Основные данные о погоде
    @BindView(R.id.tv_temperature)
    TextView temperature;
    @BindView(R.id.tv_time_last_update)
    TextView tv_time_last_update;
    @BindView(R.id.icon_cond)
    ImageView icon_cond;
    @BindView(R.id.tv_condition)
    TextView tv_condition;

    String[] name_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Устанавливаем прозрачность StatusBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        MyApplication.getApplicationComponent().inject(this);

        setContentView(R.layout.activity_scrolling);

        ButterKnife.bind(this);

        name_tab = getResources().getStringArray(R.array.names_tab);
        setSupportActionBar(toolbar);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());

        //view_pager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
        view_pager.setAdapter(tabsAdapter);
        mTabLayout.setupWithViewPager(view_pager);

        setAdapter();
    }

    public void setAdapter() {
        tabsAdapter.notifyDataSetChanged();
    }

    public void fragmentOnScreen(BaseFragment currentFragment) {

    }

    private class TabsAdapter extends FragmentStatePagerAdapter {
        private static final int TAB_COUNT = 3;

        TabsAdapter(FragmentManager fm) {
            super(fm);
            //notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(LOG_TAG, Integer.toString(position));
            switch (position) {
                case 0:
                    Log.d(LOG_TAG, "Вкладка: " + Integer.toString(position));
                    return HourlyForecastFragment.newInstance(position);
                case 1:
                    Log.d(LOG_TAG, "Вкладка: " + Integer.toString(position));
                    return Daily5ForecastFragment.newInstance(position);
                case 2:
                    Log.d(LOG_TAG, "Вкладка: " + Integer.toString(position));
                    return WeeklyForecastFragment.newInstance(position);
                default:
                    Log.d(LOG_TAG, "Default.");
                    return null;
            }
        }

        @Override
        public int getItemPosition(@NonNull Object item) {

            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return name_tab[position];
        }

    }
}