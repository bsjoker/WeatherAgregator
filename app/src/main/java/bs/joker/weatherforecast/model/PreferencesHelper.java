package bs.joker.weatherforecast.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.InvalidClassException;
import java.util.Set;

import bs.joker.MyApplication;

/**
 * Created by 1 on 18.02.2018.
 */

public class PreferencesHelper {
    public static final String LOG_TAG = "Prefs";
    private static final String SETTING_FILE = MyApplication.class.getPackage().getName();

    private PreferencesHelper() {
        super();

    }

    public static SharedPreferences getSharedPreferences() {
        return MyApplication.getInstance().getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
    }

    public static void savePreference(String key, Object value)throws InvalidClassException{
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            //The object is not of the appropriate type
            String msg = String.format("%s: %s", key, value.getClass().getName());
            Log.e(LOG_TAG, String.format("Configuration error. InvalidClassException: %s", msg));
            throw new InvalidClassException(msg);
        }
        editor.apply();
        //Log.d("Prefs", "Prefs: " + sp.getString(key, "Error"));
    }
}

