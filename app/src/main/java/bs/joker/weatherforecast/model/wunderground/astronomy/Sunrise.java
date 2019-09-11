
package bs.joker.weatherforecast.model.wunderground.astronomy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sunrise {

    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("minute")
    @Expose
    private String minute;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

}
