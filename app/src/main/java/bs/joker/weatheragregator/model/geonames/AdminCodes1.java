
package bs.joker.weatheragregator.model.geonames;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class AdminCodes1 extends RealmObject {

    @SerializedName("ISO3166_2")
    @Expose
    private String iSO31662;

    public String getISO31662() {
        return iSO31662;
    }

    public void setISO31662(String iSO31662) {
        this.iSO31662 = iSO31662;
    }

}
