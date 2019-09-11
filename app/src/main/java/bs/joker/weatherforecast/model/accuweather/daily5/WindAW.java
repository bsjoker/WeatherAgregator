
package bs.joker.weatherforecast.model.accuweather.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class WindAW extends RealmObject {

    @SerializedName("Speed")
    @Expose
    private SpeedAW mSpeedAW;
    @SerializedName("Direction")
    @Expose
    private DirectionAW mDirectionAW;

    public SpeedAW getSpeedAW() {
        return mSpeedAW;
    }

    public void setSpeedAW(SpeedAW speedAW) {
        this.mSpeedAW = speedAW;
    }

    public DirectionAW getDirectionAW() {
        return mDirectionAW;
    }

    public void setDirectionAW(DirectionAW directionAW) {
        this.mDirectionAW = directionAW;
    }

}
