package bs.joker.weatheragregator.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.InvalidClassException;
import java.util.List;

import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.geonames.Geoname;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;

/**
 * Created by 1 on 08.05.2018.
 */

public class SplashActivity extends BaseActivity {
    public static final String TAG = "SplashActivity";
    private List<Geoname> cities;
    public static SplashActivity linkSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash);

        linkSplash = this;

        if (PreferencesHelper.getSharedPreferences().getBoolean("firstStart", true) || !PreferencesHelper.getSharedPreferences().getBoolean("agreePolicity", true)){
            try {
                PreferencesHelper.savePreference("langOld", getApplicationContext().getResources().getConfiguration().locale.getLanguage());
                PreferencesHelper.savePreference("firstStart", false);
                Intent intent = new Intent(this, Policity.class);
                startActivity(intent);
                finish();
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
        } else {
            Realm realm = Realm.getDefaultInstance();
            try {
                final RealmResults<Geoname> realmResults = realm.where(Geoname.class).findAll();
                realm.executeTransaction(inRealm -> {
                    cities = realm.copyFromRealm(realmResults);
                    if (cities.size() > 0) {
                        try {
                            PreferencesHelper.savePreference("ChangeCityHourly", true);
                            PreferencesHelper.savePreference("ChangeCityDaily", true);
                            PreferencesHelper.savePreference("ChangeCityWeekly", true);
                        } catch (InvalidClassException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(this, ScrollActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(this, SearchActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Log.d(TAG, "Количество городов: " + cities.size());
                });
            } catch (IOException e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }
}
