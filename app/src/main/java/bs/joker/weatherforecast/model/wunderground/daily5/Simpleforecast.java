
package bs.joker.weatherforecast.model.wunderground.daily5;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Simpleforecast {

    @SerializedName("forecastday")
    @Expose
    private List<Forecastday_> forecastday = null;

    public List<Forecastday_> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<Forecastday_> forecastday) {
        this.forecastday = forecastday;
    }

}
