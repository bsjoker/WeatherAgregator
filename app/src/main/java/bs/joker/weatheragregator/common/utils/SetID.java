package bs.joker.weatheragregator.common.utils;

import bs.joker.weatheragregator.model.accuweather.HourlyForecastAccuweather;
import bs.joker.weatheragregator.model.accuweather.daily5.Daily5ForecastAW;
import bs.joker.weatheragregator.model.weatherbitio.daily.DatumDailyWeatherbitio;
import bs.joker.weatheragregator.model.weatherbitio.hourly.DatumHourlyWeatherbitio;
import bs.joker.weatheragregator.model.wunderground.HourlyForecastWunderground;
import bs.joker.weatheragregator.model.wunderground.daily5.Forecastday_;

/**
 * Created by 1 on 22.03.2018.
 */

public class SetID {
    public static HourlyForecastWunderground setIDHourlyWU (HourlyForecastWunderground hourlyForecastWU, int i){
        HourlyForecastWunderground mhourlyForecastWU;
        mhourlyForecastWU = hourlyForecastWU;
        mhourlyForecastWU.setId(i);
        mhourlyForecastWU.getFCTTIME().setId(i);
        mhourlyForecastWU.getDewpoint().setId(i);
        mhourlyForecastWU.getFeelslike().setId(i);
        mhourlyForecastWU.getHeatindex().setId(i);
        mhourlyForecastWU.getMslp().setId(i);
        mhourlyForecastWU.getQpf().setId(i);
        mhourlyForecastWU.getSnow().setId(i);
        mhourlyForecastWU.getTemp().setId(i);
        mhourlyForecastWU.getWdir().setId(i);
        mhourlyForecastWU.getWindchill().setId(i);
        mhourlyForecastWU.getWspd().setId(i);
        return mhourlyForecastWU;
    }

    public static DatumHourlyWeatherbitio setIDHourlyWBIO (DatumHourlyWeatherbitio hourlyForecastWBIO, int i){
        DatumHourlyWeatherbitio mhourlyForecastWBIO;
        mhourlyForecastWBIO = hourlyForecastWBIO;
        mhourlyForecastWBIO.setId(i);
        mhourlyForecastWBIO.getWeather().setId(i);
        return mhourlyForecastWBIO;
    }

    public static HourlyForecastAccuweather setIDHourlyAW (HourlyForecastAccuweather hourlyForecastAW, int i){
        HourlyForecastAccuweather mHourlyForecastAW;
        mHourlyForecastAW = hourlyForecastAW;
        mHourlyForecastAW.setId(i);
        mHourlyForecastAW.getCeiling().setId(i);
        mHourlyForecastAW.getDewPointAW().setId(i);
        mHourlyForecastAW.getIce().setId(i);
        mHourlyForecastAW.getRain().setId(i);
        mHourlyForecastAW.getRealFeelTemperature().setId(i);
        mHourlyForecastAW.getSnowAW().setId(i);
        mHourlyForecastAW.getTemperature().setId(i);
        mHourlyForecastAW.getTotalLiquid().setId(i);
        mHourlyForecastAW.getVisibility().setId(i);
        mHourlyForecastAW.getWetBulbTemperature().setId(i);
        mHourlyForecastAW.getWind().setId(i);
        mHourlyForecastAW.getWind().getSpeed().setId(i);
        mHourlyForecastAW.getWind().getDirection().setId(i);
        mHourlyForecastAW.getWindGust().setId(i);
        mHourlyForecastAW.getWindGust().getSpeed().setId(i);
        return mHourlyForecastAW;
    }

//    public static Forecast_ok setIDDaily5WU (Forecast_ok daily5ForecastWU, int i){
//        Forecast_ok mDaily5WU = daily5ForecastWU;
//        mDaily5WU.setId(i);
//        mDaily5WU.getAvewind().setId(i);
//        mDaily5WU.getDate().setId(i);
//        mDaily5WU.getHigh().setId(i);
//        mDaily5WU.getLow().setId(i);
//        mDaily5WU.getMaxwind().setId(i);
//        mDaily5WU.getQpfAllday().setId(i);
//        mDaily5WU.getQpfDay().setId(i);
//        mDaily5WU.getQpfNight().setId(i);
//        mDaily5WU.getSnowAllday().setId(i);
//        mDaily5WU.getSnowDay().setId(i);
//        mDaily5WU.getSnowNight().setId(i);
//        return mDaily5WU;
//    }

    public static DatumDailyWeatherbitio setIDDaily5WBIO (DatumDailyWeatherbitio daily5ForecastWBIO, int i){
        DatumDailyWeatherbitio mDaily5WBIO;
        mDaily5WBIO = daily5ForecastWBIO;
        mDaily5WBIO.setId(i);
        mDaily5WBIO.getWeatherDaily().setId(i);
        return mDaily5WBIO;
    }

    public static Daily5ForecastAW setIDDaily5AW (Daily5ForecastAW daily5ForecastAW, int i){
        Daily5ForecastAW mDaily5AW = daily5ForecastAW;
        mDaily5AW.setId(i);
        mDaily5AW.getDay().setId(i);
        mDaily5AW.getDegreeDaySummary().setId(i);
        mDaily5AW.getMoon().setId(i);
        mDaily5AW.getNight().setId(i);
        mDaily5AW.getRealFeelTemperatureAW().setId(i);
        mDaily5AW.getRealFeelTemperatureShade().setId(i);
        mDaily5AW.getSun().setId(i);
        mDaily5AW.getTemperatureAW().setId(i);
        return mDaily5AW;
    }

    public static Forecastday_ setIDWeekly5WU (Forecastday_ weeklyForecastWU, int i){
        Forecastday_ mWeekly5WU = weeklyForecastWU;
        mWeekly5WU.setId(i);
        mWeekly5WU.getAvewind().setId(i);
        mWeekly5WU.getDate().setId(i);
        mWeekly5WU.getHigh().setId(i);
        mWeekly5WU.getLow().setId(i);
        mWeekly5WU.getMaxwind().setId(i);
        mWeekly5WU.getQpfAllday().setId(i);
        mWeekly5WU.getQpfDay().setId(i);
        mWeekly5WU.getQpfNight().setId(i);
        mWeekly5WU.getSnowAllday().setId(i);
        mWeekly5WU.getSnowDay().setId(i);
        mWeekly5WU.getSnowNight().setId(i);
        return mWeekly5WU;
    }
}
