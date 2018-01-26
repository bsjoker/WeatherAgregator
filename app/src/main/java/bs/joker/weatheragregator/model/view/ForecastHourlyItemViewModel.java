package bs.joker.weatheragregator.model.view;

import android.util.Log;
import android.view.View;

import java.text.ParseException;

import bs.joker.weatheragregator.common.utils.ConvertDescriptionCode;
import bs.joker.weatheragregator.common.utils.DirectionWind;
import bs.joker.weatheragregator.model.accuweather.HourlyForecastAccuweather;
import bs.joker.weatheragregator.model.darksky.HourlyForecastDarksky;
import bs.joker.weatheragregator.model.wunderground.HourlyForecastWunderground;
import bs.joker.weatheragregator.ui.holder.BaseViewHolder;
import bs.joker.weatheragregator.ui.holder.ForecastHourlyViewHolder;

/**
 * Created by bakays on 07.12.2017.
 */

public class ForecastHourlyItemViewModel extends BaseViewModel {

    private String mTimeDate;
    private String mDescriptionWeather;
    private String mWindDirection;
    private String mWindSpeed;
    private String mTemp;
    private int mDescriptionCode, mId;
    private String precipation;
    private boolean mDay = true;

    public ForecastHourlyItemViewModel(HourlyForecastWunderground hourlyForecastWunderground) {
        this.mTimeDate = hourlyForecastWunderground.getFCTTIME().getHour() + ":" + hourlyForecastWunderground.getFCTTIME().getMin();
        this.mDescriptionWeather = hourlyForecastWunderground.getCondition();
        this.mWindDirection = hourlyForecastWunderground.getWdir().getDir();
        this.mDescriptionCode = (Integer.parseInt(hourlyForecastWunderground.getFctcode()));
        this.precipation = hourlyForecastWunderground.getPop() + "%";

        this.mWindSpeed = hourlyForecastWunderground.getWspd().getMetric();
        this.mTemp = hourlyForecastWunderground.getTemp().getMetric();
        if (Integer.valueOf(hourlyForecastWunderground.getFCTTIME().getHour()) >= 18
                && Integer.valueOf(hourlyForecastWunderground.getFCTTIME().getHour()) <= 6) {
            this.mDay = false;
        }
    }

    public ForecastHourlyItemViewModel(HourlyForecastAccuweather hourlyForecastAccuweather) throws ParseException {
        this.mTimeDate = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(hourlyForecastAccuweather.getEpochDateTime()*1000L));
        this.mDescriptionWeather = hourlyForecastAccuweather.getIconPhrase();
        this.mWindDirection = hourlyForecastAccuweather.getWind().getDirection().getLocalized();
        Log.d("ForecastHourlyIVM", "Num: " + hourlyForecastAccuweather.getWeatherIcon());
        this.mDescriptionCode = ConvertDescriptionCode.convertCodeAW(hourlyForecastAccuweather.getWeatherIcon());
        this.precipation = hourlyForecastAccuweather.getPrecipitationProbability() + "%";

        this.mWindSpeed = String.valueOf(hourlyForecastAccuweather.getWind().getSpeed().getValue());
        this.mTemp = String.valueOf(hourlyForecastAccuweather.getTemperature().getValue().intValue());
        this.mDay = hourlyForecastAccuweather.getIsDaylight();
    }

    public ForecastHourlyItemViewModel(HourlyForecastDarksky hourlyForecastDarksky) {
        this.mTimeDate = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date((hourlyForecastDarksky.getTime()+3600L)*1000L));
        this.mDescriptionWeather = hourlyForecastDarksky.getSummary();
        this.mWindDirection = DirectionWind.getDirectWind(hourlyForecastDarksky.getWindBearing());
        double prec = hourlyForecastDarksky.getPrecipProbability().intValue()*100;
        this.precipation = String.valueOf(prec);
        this.mDescriptionCode = ConvertDescriptionCode.convertCodeDS(hourlyForecastDarksky.getIcon());

        this.mWindSpeed = String.valueOf(hourlyForecastDarksky.getWindSpeed());
        this.mTemp = String.valueOf(hourlyForecastDarksky.getTemperature().intValue());
        this.mDay = true;
    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.ForecastHourly;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view) {
        return new ForecastHourlyViewHolder(view);
    }

    public int getId() {
        return mId;
    }

    public String getTimeDate() {
        return mTimeDate;
    }

    public void setTimeDate(String timeDate) {
        mTimeDate = timeDate;
    }

    public String getDescriptionWeather() {
        return mDescriptionWeather;
    }

    public void setDescriptionWeather(String descriptionWeather) {
        mDescriptionWeather = descriptionWeather;
    }

    public String getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        mWindSpeed = windSpeed;
    }

    public String getTemp() {
        return mTemp;
    }

    public void setTemp(String tempMin) {
        mTemp = tempMin;
    }

    public String getWindDirection() {
        return mWindDirection;
    }

    public void setWindDirection(String windDirection) {
        mWindDirection = windDirection;
    }

    public Integer getDescriptionCode() {
        return mDescriptionCode;
    }

    public void setDescriptionCode(Integer decriptionCode) {
        mDescriptionCode = decriptionCode;
    }

    public boolean isDay() {
        return mDay;
    }

    public String getPrecipation() {
        return precipation;
    }

    public void setPrecipation(String precipation) {
        this.precipation = precipation;
    }
}
