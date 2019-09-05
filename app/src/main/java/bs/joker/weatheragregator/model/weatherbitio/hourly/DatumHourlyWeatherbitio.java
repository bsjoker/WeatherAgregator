
package bs.joker.weatheragregator.model.weatherbitio.hourly;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DatumHourlyWeatherbitio extends RealmObject {

    @PrimaryKey
    Integer Id;
    @SerializedName("wind_cdir")
    @Expose
    private String windCdir;
    @SerializedName("rh")
    @Expose
    private Integer rh;
    @SerializedName("pod")
    @Expose
    private String pod;
    @SerializedName("timestamp_utc")
    @Expose
    private String timestampUtc;
    @SerializedName("pres")
    @Expose
    private Double pres;
    @SerializedName("solar_rad")
    @Expose
    private Double solarRad;
    @SerializedName("ozone")
    @Expose
    private Double ozone;
    @SerializedName("weather")
    @Expose
    private Weather weather;
    @SerializedName("wind_gust_spd")
    @Expose
    private Double windGustSpd;
    @SerializedName("timestamp_local")
    @Expose
    private String timestampLocal;
    @SerializedName("snow_depth")
    @Expose
    private Integer snowDepth;
    @SerializedName("clouds")
    @Expose
    private Integer clouds;
    @SerializedName("ts")
    @Expose
    private Integer ts;
    @SerializedName("wind_spd")
    @Expose
    private Double windSpd;
    @SerializedName("pop")
    @Expose
    private Integer pop;
    @SerializedName("wind_cdir_full")
    @Expose
    private String windCdirFull;
    @SerializedName("slp")
    @Expose
    private Double slp;
    @SerializedName("dni")
    @Expose
    private Double dni;
    @SerializedName("dewpt")
    @Expose
    private Double dewpt;
    @SerializedName("snow")
    @Expose
    private Integer snow;
    @SerializedName("uv")
    @Expose
    private Double uv;
    @SerializedName("wind_dir")
    @Expose
    private Integer windDir;
    @SerializedName("clouds_hi")
    @Expose
    private Integer cloudsHi;
    @SerializedName("precip")
    @Expose
    private Double precip;
    @SerializedName("vis")
    @Expose
    private Double vis;
    @SerializedName("dhi")
    @Expose
    private Double dhi;
    @SerializedName("app_temp")
    @Expose
    private Double appTemp;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("ghi")
    @Expose
    private Double ghi;
    @SerializedName("clouds_mid")
    @Expose
    private Integer cloudsMid;
    @SerializedName("clouds_low")
    @Expose
    private Integer cloudsLow;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getWindCdir() {
        return windCdir;
    }

    public void setWindCdir(String windCdir) {
        this.windCdir = windCdir;
    }

    public Integer getRh() {
        return rh;
    }

    public void setRh(Integer rh) {
        this.rh = rh;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public String getTimestampUtc() {
        return timestampUtc;
    }

    public void setTimestampUtc(String timestampUtc) {
        this.timestampUtc = timestampUtc;
    }

    public Double getPres() {
        return pres;
    }

    public void setPres(Double pres) {
        this.pres = pres;
    }

    public Double getSolarRad() {
        return solarRad;
    }

    public void setSolarRad(Double solarRad) {
        this.solarRad = solarRad;
    }

    public Double getOzone() {
        return ozone;
    }

    public void setOzone(Double ozone) {
        this.ozone = ozone;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Double getWindGustSpd() {
        return windGustSpd;
    }

    public void setWindGustSpd(Double windGustSpd) {
        this.windGustSpd = windGustSpd;
    }

    public String getTimestampLocal() {
        return timestampLocal;
    }

    public void setTimestampLocal(String timestampLocal) {
        this.timestampLocal = timestampLocal;
    }

    public Integer getSnowDepth() {
        return snowDepth;
    }

    public void setSnowDepth(Integer snowDepth) {
        this.snowDepth = snowDepth;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public Double getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(Double windSpd) {
        this.windSpd = windSpd;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public String getWindCdirFull() {
        return windCdirFull;
    }

    public void setWindCdirFull(String windCdirFull) {
        this.windCdirFull = windCdirFull;
    }

    public Double getSlp() {
        return slp;
    }

    public void setSlp(Double slp) {
        this.slp = slp;
    }

    public Double getDni() {
        return dni;
    }

    public void setDni(Double dni) {
        this.dni = dni;
    }

    public Double getDewpt() {
        return dewpt;
    }

    public void setDewpt(Double dewpt) {
        this.dewpt = dewpt;
    }

    public Integer getSnow() {
        return snow;
    }

    public void setSnow(Integer snow) {
        this.snow = snow;
    }

    public Double getUv() {
        return uv;
    }

    public void setUv(Double uv) {
        this.uv = uv;
    }

    public Integer getWindDir() {
        return windDir;
    }

    public void setWindDir(Integer windDir) {
        this.windDir = windDir;
    }

    public Integer getCloudsHi() {
        return cloudsHi;
    }

    public void setCloudsHi(Integer cloudsHi) {
        this.cloudsHi = cloudsHi;
    }

    public Double getPrecip() {
        return precip;
    }

    public void setPrecip(Double precip) {
        this.precip = precip;
    }

    public Double getVis() {
        return vis;
    }

    public void setVis(Double vis) {
        this.vis = vis;
    }

    public Double getDhi() {
        return dhi;
    }

    public void setDhi(Double dhi) {
        this.dhi = dhi;
    }

    public Double getAppTemp() {
        return appTemp;
    }

    public void setAppTemp(Double appTemp) {
        this.appTemp = appTemp;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getGhi() {
        return ghi;
    }

    public void setGhi(Double ghi) {
        this.ghi = ghi;
    }

    public Integer getCloudsMid() {
        return cloudsMid;
    }

    public void setCloudsMid(Integer cloudsMid) {
        this.cloudsMid = cloudsMid;
    }

    public Integer getCloudsLow() {
        return cloudsLow;
    }

    public void setCloudsLow(Integer cloudsLow) {
        this.cloudsLow = cloudsLow;
    }

}
