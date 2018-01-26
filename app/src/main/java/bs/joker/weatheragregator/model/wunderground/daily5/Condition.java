package bs.joker.weatheragregator.model.wunderground.daily5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bakays on 17.01.2018.
 */

public class Condition {
    @SerializedName("cond_d")
    @Expose
    private String cond_d;

    @SerializedName("cond_n")
    @Expose
    private String cond_n;

    public String getCond_d() {
        return cond_d;
    }

    public void setCond_d(String cond_d) {
        this.cond_d = cond_d;
    }

    public String getCond_n() {
        return cond_n;
    }

    public void setCond_n(String cond_n) {
        this.cond_n = cond_n;
    }
}
