
package bs.joker.weatherforecast.model.accuweather.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class WindGustAW extends RealmObject {

    @SerializedName("Speed")
    @Expose
    private Speed_AW speed;
    @SerializedName("Direction")
    @Expose
    private Direction_ direction;

    public Speed_AW getSpeed() {
        return speed;
    }

    public void setSpeed(Speed_AW speed) {
        this.speed = speed;
    }

    public Direction_ getDirection() {
        return direction;
    }

    public void setDirection(Direction_ direction) {
        this.direction = direction;
    }

}
