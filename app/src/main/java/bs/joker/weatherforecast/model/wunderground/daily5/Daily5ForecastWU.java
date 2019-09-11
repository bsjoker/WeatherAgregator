
package bs.joker.weatherforecast.model.wunderground.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Daily5ForecastWU {

    @SerializedName("txt_forecast")
    @Expose
    private TxtForecast txtForecast;
    @SerializedName("simpleforecast")
    @Expose
    private Simpleforecast simpleforecast;

    public TxtForecast getTxtForecast() {
        return txtForecast;
    }

    public void setTxtForecast(TxtForecast txtForecast) {
        this.txtForecast = txtForecast;
    }

    public Simpleforecast getSimpleforecast() {
        return simpleforecast;
    }

    public void setSimpleforecast(Simpleforecast simpleforecast) {
        this.simpleforecast = simpleforecast;
    }

}
