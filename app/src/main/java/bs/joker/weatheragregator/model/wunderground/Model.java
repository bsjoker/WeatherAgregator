
package bs.joker.weatheragregator.model.wunderground;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("hourly_forecast")
    @Expose
    private List<HourlyForecastWunderground> hourlyForecastWundergrounds = null;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<HourlyForecastWunderground> getHourlyForecastWundergrounds() {
        return hourlyForecastWundergrounds;
    }

    public void setHourlyForecastWundergrounds(List<HourlyForecastWunderground> hourlyForecastWundergrounds) {
        this.hourlyForecastWundergrounds = hourlyForecastWundergrounds;
    }
}
