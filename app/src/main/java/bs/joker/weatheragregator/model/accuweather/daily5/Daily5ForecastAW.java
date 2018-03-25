
package bs.joker.weatheragregator.model.accuweather.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Daily5ForecastAW extends RealmObject {

    @PrimaryKey
    Integer Id;

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("EpochDate")
    @Expose
    private Integer epochDate;
    @SerializedName("Sun")
    @Expose
    private Sun sun;
    @SerializedName("Moon")
    @Expose
    private Moon moon;
    @SerializedName("Temperature")
    @Expose
    private TemperatureAW mTemperatureAW;
    @SerializedName("RealFeelTemperature")
    @Expose
    private RealFeelTemperatureAW mRealFeelTemperatureAW;
    @SerializedName("RealFeelTemperatureShade")
    @Expose
    private RealFeelTemperatureShade realFeelTemperatureShade;
    @SerializedName("HoursOfSun")
    @Expose
    private Double hoursOfSun;
    @SerializedName("DegreeDaySummary")
    @Expose
    private DegreeDaySummary degreeDaySummary;
//    @SerializedName("AirAndPollen")
//    @Expose
//    private RealmList<AirAndPollen> airAndPollen = null;
    @SerializedName("Day")
    @Expose
    private Day day;
    @SerializedName("Night")
    @Expose
    private Night night;
    @SerializedName("Sources")
    @Expose
    private RealmList<String> sources = null;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(Integer epochDate) {
        this.epochDate = epochDate;
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    public Moon getMoon() {
        return moon;
    }

    public void setMoon(Moon moon) {
        this.moon = moon;
    }

    public TemperatureAW getTemperatureAW() {
        return mTemperatureAW;
    }

    public void setTemperatureAW(TemperatureAW temperatureAW) {
        this.mTemperatureAW = temperatureAW;
    }

    public RealFeelTemperatureAW getRealFeelTemperatureAW() {
        return mRealFeelTemperatureAW;
    }

    public void setRealFeelTemperatureAW(RealFeelTemperatureAW realFeelTemperatureAW) {
        this.mRealFeelTemperatureAW = realFeelTemperatureAW;
    }

    public RealFeelTemperatureShade getRealFeelTemperatureShade() {
        return realFeelTemperatureShade;
    }

    public void setRealFeelTemperatureShade(RealFeelTemperatureShade realFeelTemperatureShade) {
        this.realFeelTemperatureShade = realFeelTemperatureShade;
    }

    public Double getHoursOfSun() {
        return hoursOfSun;
    }

    public void setHoursOfSun(Double hoursOfSun) {
        this.hoursOfSun = hoursOfSun;
    }

    public DegreeDaySummary getDegreeDaySummary() {
        return degreeDaySummary;
    }

    public void setDegreeDaySummary(DegreeDaySummary degreeDaySummary) {
        this.degreeDaySummary = degreeDaySummary;
    }

//    public RealmList<AirAndPollen> getAirAndPollen() {
//        return airAndPollen;
//    }
//
//    public void setAirAndPollen(RealmList<AirAndPollen> airAndPollen) {
//        this.airAndPollen = airAndPollen;
//    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Night getNight() {
        return night;
    }

    public void setNight(Night night) {
        this.night = night;
    }

    public RealmList<String> getSources() {
        return sources;
    }

    public void setSources(RealmList<String> sources) {
        this.sources = sources;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
