package bs.joker.weatheragregator.common.utils;

import android.util.Log;

import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.geonames.Geoname;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 1 on 21.02.2018.
 */

public class UrlMaker {
    public static final String LOG_TAG = "UrlMaker";



    public static String getUrl(String mUrl){
        String newUrl;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Geoname> realmResults = realm.where(Geoname.class).equalTo("name", PreferencesHelper.getSharedPreferences().getString("CurrentCity", null)).findAll();
        if (realmResults!=null){
        newUrl = mUrl.replace("{lat},{lng}", realmResults.get(0).getLat() + "," + realmResults.get(0).getLng());
//            newUrl = mUrl.replace("{lat},{lng}", "55.75222,37.61556");
        Log.d(LOG_TAG, "URL: " + newUrl);}
        else {newUrl = mUrl.replace("{lat},{lng}", "55.75222,37.61556");
            Log.d(LOG_TAG, "Not find in base. Default URL: " + newUrl);}
        return newUrl;
    }

    public static String getUrlGeoNames(String mUrl, String query){
        String newUrl;
        newUrl = mUrl.replace("{query}", query);
        Log.d(LOG_TAG, "URL: " + newUrl);
        return newUrl;
    }
}
