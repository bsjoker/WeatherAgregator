package bs.joker.weatherforecast.model.view;

import android.util.Log;
import android.view.View;

import bs.joker.weatherforecast.common.utils.ConvertDescriptionCode;
import bs.joker.weatherforecast.model.accuweather.daily5.Daily5ForecastAW;
import bs.joker.weatherforecast.model.darksky.DailyForecastDarksky;
import bs.joker.weatherforecast.model.weatherbitio.daily.DatumDailyWeatherbitio;
import bs.joker.weatherforecast.ui.holder.BaseViewHolder;
import bs.joker.weatherforecast.ui.holder.ForecastDaily5ViewHolder;

/**
 * Created by bakays on 15.01.2018.
 */

public class ForecastDaily5ItemViewModel extends BaseViewModel {
    private String date_tv, temp_max, temp_min, description_day, description_night;
    private Integer icon_day, icon_night;


    public ForecastDaily5ItemViewModel(DatumDailyWeatherbitio daily5forecastWBIO){
        Log.d("ForecastDailyIVM", "Date: " + daily5forecastWBIO.getTs());
        this.date_tv = new java.text.SimpleDateFormat("dd.MM EEE").format(new java.util.Date(daily5forecastWBIO.getTs()*1000L));
        this.icon_day = daily5forecastWBIO.getWeatherDaily().getCode();
        this.icon_night =  daily5forecastWBIO.getWeatherDaily().getCode();
        this.description_day = daily5forecastWBIO.getWeatherDaily().getDescription();
        this.description_night = daily5forecastWBIO.getWeatherDaily().getDescription();
        this.temp_max = String.valueOf(Math.round(daily5forecastWBIO.getMaxTemp()));
        this.temp_min = String.valueOf(Math.round(daily5forecastWBIO.getMinTemp()));
    }

    public ForecastDaily5ItemViewModel(Daily5ForecastAW daily5forecastAW){
        this.date_tv = new java.text.SimpleDateFormat("dd.MM EEE").format(new java.util.Date(daily5forecastAW.getEpochDate()*1000L));
        this.icon_day = ConvertDescriptionCode.convertCodeAW(daily5forecastAW.getDay().getIcon());
        this.icon_night = ConvertDescriptionCode.convertCodeAW(daily5forecastAW.getNight().getIcon());
        this.description_day = daily5forecastAW.getDay().getShortPhrase();
        this.description_night = daily5forecastAW.getNight().getShortPhrase();
        this.temp_max = String.valueOf(daily5forecastAW.getTemperatureAW().getMaximum().getValue().intValue());
        this.temp_min = String.valueOf(daily5forecastAW.getTemperatureAW().getMinimum().getValue().intValue());
    }

    public ForecastDaily5ItemViewModel(DailyForecastDarksky daily5forecastDS){
        this.date_tv = new java.text.SimpleDateFormat("dd.MM EEE").format(new java.util.Date(daily5forecastDS.getTime()*1000L));
        this.icon_day = ConvertDescriptionCode.convertCodeDS(daily5forecastDS.getIcon());
        this.icon_night = ConvertDescriptionCode.convertCodeDS(daily5forecastDS.getIcon());
        this.description_day = ConvertDescriptionCode.convertDescriptionDS(daily5forecastDS.getIcon());
        this.description_night = ConvertDescriptionCode.convertDescriptionDS(daily5forecastDS.getIcon());
        int temp_max = (int)Math.round(daily5forecastDS.getTemperatureHigh());
        this.temp_max = String.valueOf(temp_max);
        int temp_min = (int)Math.round(daily5forecastDS.getTemperatureLow());
        this.temp_min = String.valueOf(temp_min);
    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.ForecastDaily;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view) {
        return new ForecastDaily5ViewHolder(view);
    }

    public String getDate_tv() {
        return date_tv;
    }

    public void setDate_tv(String date_tv) {
        this.date_tv = date_tv;
    }

    public Integer getIcon_day() {
        return icon_day;
    }

    public void setIcon_day(Integer icon_day) {
        this.icon_day = icon_day;
    }

    public Integer getIcon_night() {
        return icon_night;
    }

    public void setIcon_night(Integer icon_night) {
        this.icon_night = icon_night;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getDescription_day() {
        return description_day;
    }

    public void setDescription_day(String description_day) {
        this.description_day = description_day;
    }

    public String getDescription_night() {
        return description_night;
    }

    public void setDescription_night(String description_night) {
        this.description_night = description_night;
    }
}
