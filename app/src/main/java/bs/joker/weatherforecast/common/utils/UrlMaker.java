package bs.joker.weatherforecast.common.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import bs.joker.weatherforecast.R;
import bs.joker.weatherforecast.model.PreferencesHelper;
import bs.joker.weatherforecast.model.geonames.Geoname;
import bs.joker.weatherforecast.ui.activity.ScrollActivity;
import bs.joker.weatherforecast.ui.activity.SplashActivity;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 1 on 21.02.2018.
 */

public class UrlMaker {
    public static final String LOG_TAG = "UrlMaker";
    static public String lat, lng, cityKey, lang, name;
    static private Context context = ScrollActivity.link;
    static private Context contextSplash = SplashActivity.linkSplash;

    public static String getUrl(String mUrl) {
        String newUrl;

        lang = PreferencesHelper.getSharedPreferences().getString("langOld", "en");

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(inRealm -> {
            RealmResults<Geoname> realmResults = realm.where(Geoname.class).equalTo("name", PreferencesHelper.getSharedPreferences().getString("CurrentCity", "Нижневартовск")).findAll();
            lat = realmResults.get(0).getLat();
            lng = realmResults.get(0).getLng();
        });

        if (!lat.isEmpty() && !lng.isEmpty()) {
            newUrl = mUrl.replace("{lat},{lng}", lat + "," + lng);
            newUrl = newUrl.replace("{lang}", lang);
//            if (realmResults!=null){
//            newUrl = mUrl.replace("{lat},{lng}", realmResults.get(0).getLat() + "," + realmResults.get(0).getLng());
//            newUrl = mUrl.replace("{lat},{lng}", "55.75222,37.61556");
            Log.d(LOG_TAG, "URL_Astronomy_CurWA: " + newUrl);
        } else {
            newUrl = mUrl.replace("{lat},{lng}", "55.75222,37.61556");
            newUrl = newUrl.replace("{lang}", lang);
            Log.d(LOG_TAG, "Not find in base. Default URL_Geoname: " + newUrl);
        }
        return newUrl;
    }

    public static String getUrlWeatherbitio(String mUrl) {
        String newUrl, unit;

        lang = PreferencesHelper.getSharedPreferences().getString("langOld", "en");
        if(PreferencesHelper.getSharedPreferences().getBoolean("metric", true)){
            unit = "M";
        } else {
            unit = "I";
        }

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(inRealm -> {
            RealmResults<Geoname> realmResults = realm.where(Geoname.class).equalTo("name", PreferencesHelper.getSharedPreferences().getString("CurrentCity", "Нижневартовск")).findAll();
            lat = realmResults.get(0).getLat();
            lng = realmResults.get(0).getLng();
        });

        List<String> apiKey = Arrays.asList(contextSplash.getResources().getStringArray(R.array.weatherbitioApiKey));
        Random random = new Random();

        if (!lat.isEmpty() && !lng.isEmpty()) {
            newUrl = mUrl.replace("{lat}", lat);
            newUrl = newUrl.replace("{lng}", lng);
            newUrl = newUrl.replace("{lang}", lang);
//            if (realmResults!=null){
//            newUrl = mUrl.replace("{lat},{lng}", realmResults.get(0).getLat() + "," + realmResults.get(0).getLng());
//            newUrl = mUrl.replace("{lat},{lng}", "55.75222,37.61556");
            //Log.d(LOG_TAG, "URL: " + newUrl + ". Unit: " + unit);
        } else {
            newUrl = mUrl.replace("{lat}", "55.75222");
            newUrl = newUrl.replace("{lng}", "37.61556");
            newUrl = newUrl.replace("{lang}", lang);
            //Log.d(LOG_TAG, "Not find in base. Default URL: " + newUrl);
        }

        newUrl = newUrl.replace("{apiKey}", apiKey.get(random.nextInt(2)));
        newUrl = newUrl.replace("{unit}", unit);

        Log.d(LOG_TAG, "URL_WeatherbitIO: " + newUrl);

        return newUrl;
    }

    public static String getUrlAW(String mUrl) {

        String newUrl;
        lang = PreferencesHelper.getSharedPreferences().getString("langOld", "en");

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(inRealm -> {
            RealmResults<Geoname> realmResults = realm.where(Geoname.class).equalTo("name", PreferencesHelper.getSharedPreferences().getString("CurrentCity", "Нижневартовск")).findAll();
            try {
                cityKey = realmResults.get(0).getCityKeyAW().toString();
            }catch (NullPointerException e){
                Toast.makeText(contextSplash, contextSplash.getResources().getString(R.string.error_AW), Toast.LENGTH_LONG).show();
            }
            //Log.d(LOG_TAG, "City: " + realmResults.get(0).getName() + ". Key: " + realmResults.get(0).getCityKeyAW() + "Lang: " + lang);
        });
        List<String> apiKey = Arrays.asList(contextSplash.getResources().getStringArray(R.array.accuweatherApiKey));
        Random random = new Random();

        if (!cityKey.isEmpty()) {
            newUrl = mUrl.replace("{cityKey}", cityKey);
            newUrl = newUrl.replace("{lang}", lang);
            newUrl = newUrl.replace("{unit}", "=" + PreferencesHelper.getSharedPreferences().getBoolean("metric", true));
            newUrl = newUrl.replace("{apiKey}", apiKey.get(random.nextInt(9)));
//            if (realmResults != null) {
//                newUrl = mUrl.replace("{cityKey}", realmResults.get(0).getCityKeyAW().toString());
//            newUrl = mUrl.replace("{lat},{lng}", "55.75222,37.61556");
            Log.d(LOG_TAG, "URL_AW: " + newUrl);
        } else {
            newUrl = mUrl.replace("{cityKey}", "XXXXXX");
            Log.d(LOG_TAG, "Not find in base. Default URL_AW: " + newUrl);
        }
        return newUrl;
    }

    public static String getUrlCityKeyAW(String mUrl, String mLat, String mLng) {
        String newUrl;

        List<String> apiKey = Arrays.asList(contextSplash.getResources().getStringArray(R.array.accuweatherApiKey));
        Random random = new Random();

        //newUrl = mUrl.replace("{lat},{lng}", "55.75222,37.61556");
        newUrl = mUrl.replace("{lat},{lng}", mLat + "," + mLng);
        newUrl = newUrl.replace("{apiKey}", apiKey.get(random.nextInt(9)));
        Log.d(LOG_TAG, "URL_CityKey_AW: " + newUrl);
        return newUrl;
    }

    public static String getUrlDS(String mUrl) {
        String newUrl, unit;

        lang = PreferencesHelper.getSharedPreferences().getString("langOld", "en");

        if(PreferencesHelper.getSharedPreferences().getBoolean("metric", true)){
            unit = "si";
        } else {
            unit = "us";
        }
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(inRealm -> {
            RealmResults<Geoname> realmResults = realm.where(Geoname.class).equalTo("name", PreferencesHelper.getSharedPreferences().getString("CurrentCity", "Нижневартовск")).findAll();
            lat = realmResults.get(0).getLat();
            lng = realmResults.get(0).getLng();
        });

        if (!lat.isEmpty() && !lng.isEmpty()) {
            newUrl = mUrl.replace("{lat},{lng}", lat + "," + lng);
            newUrl = newUrl.replace("{lang}", lang);
            newUrl = newUrl.replace("{unit}", unit);
//            if (realmResults!=null){
//            newUrl = mUrl.replace("{lat},{lng}", realmResults.get(0).getLat() + "," + realmResults.get(0).getLng());
//            newUrl = mUrl.replace("{lat},{lng}", "55.75222,37.61556");
            Log.d(LOG_TAG, "UR_DSL: " + newUrl);
        } else {
            newUrl = mUrl.replace("{lat},{lng}", "55.75222,37.61556");
            newUrl = newUrl.replace("{lang}", lang);
            Log.d(LOG_TAG, "Not find in base. Default URL_DS: " + newUrl);
        }
        return newUrl;
    }

    public static String getUrlGeoNames(String mUrl, String query) {
        String newUrl;
        lang = PreferencesHelper.getSharedPreferences().getString("langOld", "en");
        newUrl = mUrl.replace("{query}", query);
        newUrl = newUrl.replace("{lang}", lang);
        Log.d(LOG_TAG, "URL_Geomname: " + newUrl);
        return newUrl;
    }
}
