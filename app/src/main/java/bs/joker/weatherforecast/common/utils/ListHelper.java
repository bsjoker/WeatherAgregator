package bs.joker.weatherforecast.common.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bs.joker.weatherforecast.model.PreferencesHelper;
import bs.joker.weatherforecast.model.wunderground.daily5.Forecast_ok;

/**
 * Created by BakayS on 11.12.2017.
 */

public class ListHelper {
    public static final String LOG_TAG = "ListHelper";
    public static List<Forecast_ok> getListDaily5WU(List<Forecast_ok> hourly_forecast_simple, List<Forecast_ok> hourly_forecast_txt_forecast){
        Log.d(LOG_TAG, "1");
        //Log.d(LOG_TAG, "Simple: " + hourly_forecast_simple.get(0).getIcon());
        Log.d(LOG_TAG, "Txt_forecast: " + hourly_forecast_txt_forecast.get(1).getIcon());

        List<Forecast_ok> forecast_oks = new ArrayList<Forecast_ok>();
        Log.d(LOG_TAG, "2");
        if (PreferencesHelper.getSharedPreferences().getBoolean("metric", true)) {
            for (Forecast_ok forecast_ok : hourly_forecast_simple) {
                //Log.d(LOG_TAG, "Simple: " + forecast_ok.getDate().getPretty().substring(0,5).replace(" ", "."));
                Forecast_ok mForecast_ok = new Forecast_ok();
                mForecast_ok.setDate_readable(new java.text.SimpleDateFormat("dd.MM EE").format(new java.util.Date(Integer.valueOf(forecast_ok.getDate().getEpoch()) * 1000L)));
                mForecast_ok.setTemp_max(forecast_ok.getHigh().getCelsius());
                mForecast_ok.setTemp_min(forecast_ok.getLow().getCelsius());
                mForecast_ok.setIcon_day(hourly_forecast_txt_forecast.get(forecast_ok.getPeriod() - 1).getIcon());
                mForecast_ok.setIcon_night(hourly_forecast_txt_forecast.get(forecast_ok.getPeriod()).getIcon());
                String description_day = hourly_forecast_txt_forecast.get(forecast_ok.getPeriod() - 1).getFcttextMetric();
                String description_night = hourly_forecast_txt_forecast.get(forecast_ok.getPeriod()).getFcttextMetric();
                mForecast_ok.setDescription_day(description_day.substring(0, description_day.lastIndexOf(".")));
                mForecast_ok.setDescription_night(description_night.substring(0, description_night.lastIndexOf(".")));

                forecast_oks.add(mForecast_ok);
                Log.d(LOG_TAG, "3 ");
            }
        } else {
            for (Forecast_ok forecast_ok : hourly_forecast_simple) {
                //Log.d(LOG_TAG, "Simple: " + forecast_ok.getDate().getPretty().substring(0,5).replace(" ", "."));
                Forecast_ok mForecast_ok = new Forecast_ok();
                mForecast_ok.setDate_readable(new java.text.SimpleDateFormat("dd.MM EE").format(new java.util.Date(Integer.valueOf(forecast_ok.getDate().getEpoch()) * 1000L)));
                mForecast_ok.setTemp_max(forecast_ok.getHigh().getFahrenheit());
                mForecast_ok.setTemp_min(forecast_ok.getLow().getFahrenheit());
                mForecast_ok.setIcon_day(hourly_forecast_txt_forecast.get(forecast_ok.getPeriod() - 1).getIcon());
                mForecast_ok.setIcon_night(hourly_forecast_txt_forecast.get(forecast_ok.getPeriod()).getIcon());
                String description_day = hourly_forecast_txt_forecast.get(forecast_ok.getPeriod() - 1).getFcttextMetric();
                String description_night = hourly_forecast_txt_forecast.get(forecast_ok.getPeriod()).getFcttextMetric();
                mForecast_ok.setDescription_day(description_day.substring(0, description_day.lastIndexOf(".")));
                mForecast_ok.setDescription_night(description_night.substring(0, description_night.lastIndexOf(".")));

                forecast_oks.add(mForecast_ok);
                Log.d(LOG_TAG, "3 ");
            }
        }
        return forecast_oks;
    }
}
