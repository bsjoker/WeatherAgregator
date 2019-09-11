
package bs.joker.weatherforecast.model.accuweather.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealFeelTemperatureShade extends RealmObject {

    @PrimaryKey
    Integer Id;
    @SerializedName("Minimum")
    @Expose
    private Minimum__ minimum;
    @SerializedName("Maximum")
    @Expose
    private Maximum__ maximum;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Minimum__ getMinimum() {
        return minimum;
    }

    public void setMinimum(Minimum__ minimum) {
        this.minimum = minimum;
    }

    public Maximum__ getMaximum() {
        return maximum;
    }

    public void setMaximum(Maximum__ maximum) {
        this.maximum = maximum;
    }

}
