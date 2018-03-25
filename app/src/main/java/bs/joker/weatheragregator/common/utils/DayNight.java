package bs.joker.weatheragregator.common.utils;

import android.util.Log;

import bs.joker.weatheragregator.model.PreferencesHelper;

/**
 * Created by 1 on 19.02.2018.
 */

public class DayNight {
    public static final String LOG_TAG = "DayNigth";
    public static boolean isDay(Long epochTime) {
        boolean isDay = false;
        Long sunRise = PreferencesHelper.getSharedPreferences().getLong("sunRise", 0l);
        Long sunSet = PreferencesHelper.getSharedPreferences().getLong("sunSet", 0l);
        if (sunRise == 0l || sunSet == 0l) {
            return true;
        }
        isDay = (epochTime >= sunRise && epochTime <= sunSet);
        Log.d(LOG_TAG, "sunRise: " + sunRise + ". Time: " + epochTime + ". sunSet: " + sunSet + ". Boolean: " + isDay);
        return isDay;
    }
}
