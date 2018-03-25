
package bs.joker.weatheragregator.model.wunderground.current;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Features extends RealmObject {

    @SerializedName("conditions")
    @Expose
    private Integer conditions;

    public Integer getConditions() {
        return conditions;
    }

    public void setConditions(Integer conditions) {
        this.conditions = conditions;
    }

}
