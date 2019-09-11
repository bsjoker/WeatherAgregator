
package bs.joker.weatherforecast.model.accuweather.daily5;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("Headline")
    @Expose
    private Headline headline;
    @SerializedName("DailyForecasts")
    @Expose
    private List<Daily5ForecastAW> mDaily5ForecastAWs = null;

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<Daily5ForecastAW> getDaily5ForecastAWs() {
        return mDaily5ForecastAWs;
    }

    public void setDaily5ForecastAWs(List<Daily5ForecastAW> daily5ForecastAWs) {
        this.mDaily5ForecastAWs = daily5ForecastAWs;
    }

}
