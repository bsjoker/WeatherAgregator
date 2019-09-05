package bs.joker.weatheragregator.rest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bakays on 05.12.2017.
 */

public class Full<T> {
    @SerializedName("hourly")
    @Expose
    public T hourly_forecast_ds;

    @SerializedName("daily")
    @Expose
    public T daily5forecastDS;

    @SerializedName("response")
    @Expose
    public T response;

    @SerializedName("current_observation")
    @Expose
    public T currentData;

    @SerializedName("sun_phase")
    @Expose
    public T astronomyData;

    @SerializedName("forecast")
    @Expose
    public T daily5forecastWU;

    @SerializedName("DailyForecasts")
    @Expose
    public List<T> daily5forecastAW;

    @SerializedName("hourly_forecast")
    @Expose
    public List<T> hourly_forecast_wa;

    @SerializedName("geonames")
    @Expose
    public List<T> geonames_list;

    @SerializedName("data")
    @Expose
    public List<T> weatherbitio_list;
}
