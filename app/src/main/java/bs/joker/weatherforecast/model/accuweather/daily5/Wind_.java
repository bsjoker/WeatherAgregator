
package bs.joker.weatherforecast.model.accuweather.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Wind_ extends RealmObject {

    @SerializedName("Speed")
    @Expose
    private Speed__ speed;
    @SerializedName("Direction")
    @Expose
    private Direction__ direction;

    public Speed__ getSpeed() {
        return speed;
    }

    public void setSpeed(Speed__ speed) {
        this.speed = speed;
    }

    public Direction__ getDirection() {
        return direction;
    }

    public void setDirection(Direction__ direction) {
        this.direction = direction;
    }

}
