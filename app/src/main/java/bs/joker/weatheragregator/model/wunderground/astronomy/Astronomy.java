
package bs.joker.weatheragregator.model.wunderground.astronomy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Astronomy {

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("moon_phase")
    @Expose
    private MoonPhase moonPhase;
    @SerializedName("sun_phase")
    @Expose
    private SunPhase sunPhase;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public MoonPhase getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(MoonPhase moonPhase) {
        this.moonPhase = moonPhase;
    }

    public SunPhase getSunPhase() {
        return sunPhase;
    }

    public void setSunPhase(SunPhase sunPhase) {
        this.sunPhase = sunPhase;
    }

}
