
package bs.joker.weatheragregator.model.accuweather.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Sun extends RealmObject {

    @PrimaryKey
    Integer Id;
    @SerializedName("Rise")
    @Expose
    private String rise;
    @SerializedName("EpochRise")
    @Expose
    private Integer epochRise;
    @SerializedName("Set")
    @Expose
    private String set;
    @SerializedName("EpochSet")
    @Expose
    private Integer epochSet;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    public Integer getEpochRise() {
        return epochRise;
    }

    public void setEpochRise(Integer epochRise) {
        this.epochRise = epochRise;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public Integer getEpochSet() {
        return epochSet;
    }

    public void setEpochSet(Integer epochSet) {
        this.epochSet = epochSet;
    }

}
