package bs.joker.weatheragregator.common.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bs.joker.weatheragregator.model.wunderground.daily5.Forecast_ok;

/**
 * Created by BakayS on 11.12.2017.
 */

//public class ListHelper {
//    public static final String LOG_TAG = "ListHelper";
//    public static List<HourlyForecastWunderground> getForecastList(BaseItemResponse<HourlyForecastWunderground> hourly_forecast){
//        Log.d(LOG_TAG, "1");
//        List<HourlyForecastWunderground> hourlyForecastWundergrounds = hourly_forecast.hourly_forecast;
//        Log.d(LOG_TAG, "2");
//        for (HourlyForecastWunderground hourlyForecastWunderground : hourlyForecastWundergrounds) {
//            hourlyForecastWunderground.getFeelslike().getMetric();
//            Log.d(LOG_TAG, "3 ");
//        }
//        return hourlyForecastWundergrounds;
//    }
//}
public class ListHelper {
    public static final String LOG_TAG = "ListHelper";
    public static List<Forecast_ok> getConditionListWU(List<Forecast_ok> hourly_forecast_simple, List<Forecast_ok> hourly_forecast_txt_forecast){
        Log.d(LOG_TAG, "1");
        //Log.d(LOG_TAG, "Simple: " + hourly_forecast_simple.get(0).getIcon());
        Log.d(LOG_TAG, "Txt_forecast: " + hourly_forecast_txt_forecast.get(1).getIcon());

        List<Forecast_ok> forecast_oks = new ArrayList<Forecast_ok>();
        Log.d(LOG_TAG, "2");
        for (Forecast_ok forecast_ok : hourly_forecast_simple) {
            //Log.d(LOG_TAG, "Simple: " + forecast_ok.getDate().getPretty().substring(0,5).replace(" ", "."));
            Forecast_ok mForecast_ok = new Forecast_ok();
            //String date = new java.text.SimpleDateFormat("dd.MM Ee").format(new java.util.Date(Integer.valueOf(forecast_ok.getDate().getEpoch())*1000L));
            mForecast_ok.setDate_readable(new java.text.SimpleDateFormat("dd.MM EE").format(new java.util.Date(Integer.valueOf(forecast_ok.getDate().getEpoch())*1000L)));
            mForecast_ok.setTemp_max(forecast_ok.getLow().getCelsius().concat("°C"));
            mForecast_ok.setTemp_min(forecast_ok.getHigh().getCelsius().concat("°C"));
            mForecast_ok.setIcon_day(hourly_forecast_txt_forecast.get(forecast_ok.getPeriod()-1).getIcon());
            mForecast_ok.setIcon_night(hourly_forecast_txt_forecast.get(forecast_ok.getPeriod()).getIcon());
            String description_day = hourly_forecast_txt_forecast.get(forecast_ok.getPeriod()-1).getFcttextMetric();
            String description_night = hourly_forecast_txt_forecast.get(forecast_ok.getPeriod()).getFcttextMetric();
            mForecast_ok.setDescription_day(description_day.substring(0, description_day.lastIndexOf(".")));
            mForecast_ok.setDescription_night(description_night.substring(0, description_night.lastIndexOf(".")));

            forecast_oks.add(mForecast_ok);
            Log.d(LOG_TAG, "3 ");
        }
        return forecast_oks;
    }
}
