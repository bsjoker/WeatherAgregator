
package bs.joker.weatheragregator.model.accuweather.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DegreeDaySummary extends RealmObject {

    @PrimaryKey
    Integer Id;
    @SerializedName("Heating")
    @Expose
    private Heating heating;
    @SerializedName("Cooling")
    @Expose
    private Cooling cooling;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Heating getHeating() {
        return heating;
    }

    public void setHeating(Heating heating) {
        this.heating = heating;
    }

    public Cooling getCooling() {
        return cooling;
    }

    public void setCooling(Cooling cooling) {
        this.cooling = cooling;
    }

}
