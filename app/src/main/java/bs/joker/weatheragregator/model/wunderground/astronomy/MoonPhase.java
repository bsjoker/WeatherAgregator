
package bs.joker.weatheragregator.model.wunderground.astronomy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoonPhase {

    @SerializedName("percentIlluminated")
    @Expose
    private String percentIlluminated;
    @SerializedName("ageOfMoon")
    @Expose
    private String ageOfMoon;
    @SerializedName("phaseofMoon")
    @Expose
    private String phaseofMoon;
    @SerializedName("hemisphere")
    @Expose
    private String hemisphere;
    @SerializedName("current_time")
    @Expose
    private CurrentTime currentTime;
    @SerializedName("sunrise")
    @Expose
    private Sunrise sunrise;
    @SerializedName("sunset")
    @Expose
    private Sunset sunset;
    @SerializedName("moonrise")
    @Expose
    private Moonrise moonrise;
    @SerializedName("moonset")
    @Expose
    private Moonset moonset;

    public String getPercentIlluminated() {
        return percentIlluminated;
    }

    public void setPercentIlluminated(String percentIlluminated) {
        this.percentIlluminated = percentIlluminated;
    }

    public String getAgeOfMoon() {
        return ageOfMoon;
    }

    public void setAgeOfMoon(String ageOfMoon) {
        this.ageOfMoon = ageOfMoon;
    }

    public String getPhaseofMoon() {
        return phaseofMoon;
    }

    public void setPhaseofMoon(String phaseofMoon) {
        this.phaseofMoon = phaseofMoon;
    }

    public String getHemisphere() {
        return hemisphere;
    }

    public void setHemisphere(String hemisphere) {
        this.hemisphere = hemisphere;
    }

    public CurrentTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(CurrentTime currentTime) {
        this.currentTime = currentTime;
    }

    public Sunrise getSunrise() {
        return sunrise;
    }

    public void setSunrise(Sunrise sunrise) {
        this.sunrise = sunrise;
    }

    public Sunset getSunset() {
        return sunset;
    }

    public void setSunset(Sunset sunset) {
        this.sunset = sunset;
    }

    public Moonrise getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(Moonrise moonrise) {
        this.moonrise = moonrise;
    }

    public Moonset getMoonset() {
        return moonset;
    }

    public void setMoonset(Moonset moonset) {
        this.moonset = moonset;
    }

}
