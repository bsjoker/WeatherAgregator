
package bs.joker.weatherforecast.model.wunderground.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("forecast")
    @Expose
    private Daily5ForecastWU daily5ForecastWU;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Daily5ForecastWU getDaily5ForecastWU() {
        return daily5ForecastWU;
    }

    public void setDaily5ForecastWU(Daily5ForecastWU daily5ForecastWU) {
        this.daily5ForecastWU = daily5ForecastWU;
    }

}
