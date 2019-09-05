
package bs.joker.weatheragregator.model.weatherbitio.daily;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyForecastWeatherbitIO {

    @SerializedName("data")
    @Expose
    private List<DatumDailyWeatherbitio> data = null;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("state_code")
    @Expose
    private String stateCode;

    public List<DatumDailyWeatherbitio> getData() {
        return data;
    }

    public void setData(List<DatumDailyWeatherbitio> data) {
        this.data = data;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

}
