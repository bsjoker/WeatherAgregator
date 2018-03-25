package bs.joker.weatheragregator.model.view;

import android.view.View;

import bs.joker.weatheragregator.common.utils.ConvertDescriptionCode;
import bs.joker.weatheragregator.model.accuweather.daily5.Daily5ForecastAW;
import bs.joker.weatheragregator.model.darksky.DailyForecastDarksky;
import bs.joker.weatheragregator.model.wunderground.daily5.Forecast_ok;
import bs.joker.weatheragregator.ui.holder.BaseViewHolder;
import bs.joker.weatheragregator.ui.holder.ForecastDaily5ViewHolder;

/**
 * Created by bakays on 15.01.2018.
 */

public class ForecastDaily5ItemViewModel extends BaseViewModel {
    private String date_tv, temp_max, temp_min, description_day, description_night;
    private Integer icon_day, icon_night;


    public ForecastDaily5ItemViewModel(Forecast_ok daily5forecastWU){
        this.date_tv = daily5forecastWU.getDate_readable();
        this.icon_day = ConvertDescriptionCode.convertCodeD5WU(daily5forecastWU.getIcon_day());
        this.icon_night =  ConvertDescriptionCode.convertCodeD5WU(daily5forecastWU.getIcon_night());;
        this.description_day = daily5forecastWU.getDescription_day();
        this.description_night = daily5forecastWU.getDescription_night();
        this.temp_max = daily5forecastWU.getTemp_max();
        this.temp_min = daily5forecastWU.getTemp_min();
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
