package bs.joker.weatherforecast.common.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import bs.joker.weatherforecast.model.PreferencesHelper;

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

        Date sunRiseD = new Date(sunRise);
        Date sunSetD = new Date(sunSet);
        Date epochTimeD = new Date(epochTime);
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss a");


        isDay = (epochTime >= sunRise && epochTime <= sunSet);
        Log.d(LOG_TAG, "sunRise: " + formatForDateNow.format(sunRiseD) + ". Time: " + formatForDateNow.format(epochTimeD) + ". sunSet: " + formatForDateNow.format(sunSetD) + ". Boolean: " + isDay);
        return isDay;
    }
}
