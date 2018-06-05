package bs.joker.weatheragregator.model.view;

import android.util.Log;
import android.view.View;

import bs.joker.weatheragregator.common.utils.ConvertDescriptionCode;
import bs.joker.weatheragregator.model.PreferencesHelper;
import bs.joker.weatheragregator.model.accuweather.daily5.Daily5ForecastAW;
import bs.joker.weatheragregator.model.darksky.DailyForecastDarksky;
import bs.joker.weatheragregator.model.wunderground.daily5.Forecastday_;
import bs.joker.weatheragregator.ui.holder.BaseViewHolder;
import bs.joker.weatheragregator.ui.holder.ForecastWeeklyViewHolder;

/**
 * Created by bakays on 26.01.2018.
 */

public class ForecastWeeklyItemViewModel extends BaseViewModel {
    private String dayOfWeek, description, tempMax, tempMin;
    private Integer icon;

    public ForecastWeeklyItemViewModel(Forecastday_ items){
        this.dayOfWeek = new java.text.SimpleDateFormat("EEEE").format(new java.util.Date(Integer.valueOf(items.getDate().getEpoch())*1000L));
        this.description = items.getConditions();
        if (PreferencesHelper.getSharedPreferences().getBoolean("metric", true)) {
            this.tempMax = items.getHigh().getCelsius();
            this.tempMin = items.getLow().getCelsius();
        } else {
            this.tempMax = items.getHigh().getFahrenheit();
            this.tempMin = items.getLow().getFahrenheit();
        }
        this.icon = ConvertDescriptionCode.convertCodeD5WU(items.getIcon());
    }

    public ForecastWeeklyItemViewModel(Daily5ForecastAW items) {
        this.dayOfWeek = new java.text.SimpleDateFormat("EEEE").format(new java.util.Date(items.getEpochDate()*1000L));
        this.description = items.getDay().getShortPhrase();
        Log.d("WFVM", "TempLOW_AW: " + items.getTemperatureAW().getMinimum().getValue().intValue() + ". TempHIGH_AW: " + items.getTemperatureAW().getMaximum().getValue().intValue());
        this.tempMax = String.valueOf(items.getTemperatureAW().getMaximum().getValue().intValue());
        this.tempMin = String.valueOf(items.getTemperatureAW().getMinimum().getValue().intValue());
        this.icon = ConvertDescriptionCode.convertCodeAW(items.getDay().getIcon());
    }

    public ForecastWeeklyItemViewModel(DailyForecastDarksky items) {
        this.dayOfWeek = new java.text.SimpleDateFormat("EEEE").format(new java.util.Date(items.getTime()*1000L));
        this.icon = ConvertDescriptionCode.convertCodeDS(items.getIcon());
        this.description = ConvertDescriptionCode.convertDescriptionDS(items.getIcon());
        int temp_max = (int)Math.round(items.getTemperatureHigh());
        this.tempMax = String.valueOf(temp_max);
        Log.d("WFVM", "TempLOW_DS: " + items.getTemperatureLow() + ". TempHIGH_DS: " + items.getTemperatureHigh());
        int temp_min = (int)Math.round(items.getTemperatureLow());
        this.tempMin = String.valueOf(temp_min);
    }


    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.ForecastWeekly;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view) {
        return new ForecastWeeklyViewHolder(view);
    }
}
