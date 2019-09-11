package bs.joker.weatherforecast.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.InvalidClassException;
import java.util.List;

import bs.joker.weatherforecast.R;
import bs.joker.weatherforecast.model.PreferencesHelper;
import bs.joker.weatherforecast.model.geonames.Geoname;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;

public class Policity extends BaseActivity {
    public static final String TAG = "Policity";
    TextView mTextView;
    private List<Geoname> cities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policity);
        mTextView = findViewById(R.id.tvPolicity);
        mTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void onClickAgree(View view) {
        Realm realm = Realm.getDefaultInstance();
        try {
            PreferencesHelper.savePreference("agreePolicity", true);
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }
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

    public void onClickClose(View view) {
        try {
            PreferencesHelper.savePreference("agreePolicity", false);
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }
        finish();
    }
}
