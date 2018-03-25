
package bs.joker.weatheragregator.model.accuweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WindGust extends RealmObject {

    @PrimaryKey
    Integer Id;
    @SerializedName("Speed")
    @Expose
    private Speed_ speed;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Speed_ getSpeed() {
        return speed;
    }

    public void setSpeed(Speed_ speed) {
        this.speed = speed;
    }

}
