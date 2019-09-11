
package bs.joker.weatherforecast.model.accuweather.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealFeelTemperatureAW extends RealmObject {

    @PrimaryKey
    Integer Id;
    @SerializedName("Minimum")
    @Expose
    private Minimum_ minimum;
    @SerializedName("Maximum")
    @Expose
    private Maximum_ maximum;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Minimum_ getMinimum() {
        return minimum;
    }

    public void setMinimum(Minimum_ minimum) {
        this.minimum = minimum;
    }

    public Maximum_ getMaximum() {
        return maximum;
    }

    public void setMaximum(Maximum_ maximum) {
        this.maximum = maximum;
    }

}
