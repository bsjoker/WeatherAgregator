
package bs.joker.weatherforecast.model.wunderground.astronomy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SunPhase {

    @SerializedName("sunrise")
    @Expose
    private Sunrise_ sunrise;
    @SerializedName("sunset")
    @Expose
    private Sunset_ sunset;

    public Sunrise_ getSunrise() {
        return sunrise;
    }

    public void setSunrise(Sunrise_ sunrise) {
        this.sunrise = sunrise;
    }

    public Sunset_ getSunset() {
        return sunset;
    }

    public void setSunset(Sunset_ sunset) {
        this.sunset = sunset;
    }

}
