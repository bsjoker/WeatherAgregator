
package bs.joker.weatherforecast.model.accuweather.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Direction__ extends RealmObject {

    @SerializedName("Degrees")
    @Expose
    private Integer degrees;
    @SerializedName("Localized")
    @Expose
    private String localized;
    @SerializedName("English")
    @Expose
    private String english;

    public Integer getDegrees() {
        return degrees;
    }

    public void setDegrees(Integer degrees) {
        this.degrees = degrees;
    }

    public String getLocalized() {
        return localized;
    }

    public void setLocalized(String localized) {
        this.localized = localized;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

}
