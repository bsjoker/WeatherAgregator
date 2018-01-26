package bs.joker.weatheragregator.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.common.manager.MyFragmentManager;
import bs.joker.weatheragregator.ui.frgment.BaseFragment;
import bs.joker.weatheragregator.ui.frgment.Daily5ForecastFragment;
import bs.joker.weatheragregator.ui.frgment.HourlyForecastFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollingActivity extends MvpAppCompatActivity {
public static final String LOG_TAG = "ScrollingActivity";
    @Inject
    MyFragmentManager mMyFragmentManager;

    //@BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsing_toolbar_layout;
    //@BindView(R.id.toolbar)
    Toolbar toolbar;
    //@BindView(R.id.view_pager)
    ViewPager view_pager;
    TabLayout mTabLayout;

    //Основные данные о погоде
    @BindView(R.id.tv_temperature)
    TextView temperature;
    @BindView(R.id.tv_time_last_update)
    TextView tv_time_last_update;

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

        collapsing_toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        name_tab= getResources().getStringArray(R.array.names_tab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_pager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(view_pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fragmentOnScreen(BaseFragment currentFragment) {

    }

//    public void setContent(BaseFragment currentFragment) {
//        mMyFragmentManager.setFragment(this, currentFragment, R.id.view_pager);
//    }

    private class TabsAdapter extends FragmentStatePagerAdapter {
        private static final int TAB_COUNT = 3;

        TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(LOG_TAG, Integer.toString(position));
            //return new HourlyForecastFragment(position);
            switch (position) {
                case 0:
                    Log.d(LOG_TAG, "Вкладка: " + Integer.toString(position));
                    return HourlyForecastFragment.newInstance(position);
                case 1:
                    Log.d(LOG_TAG, "Вкладка: " + Integer.toString(position));
                    return Daily5ForecastFragment.newInstance(position);
                default:
                    Log.d(LOG_TAG, "Default.");
                    return Daily5ForecastFragment.newInstance(position);
            }
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