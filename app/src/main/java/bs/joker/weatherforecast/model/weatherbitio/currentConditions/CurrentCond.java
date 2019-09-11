
package bs.joker.weatherforecast.model.weatherbitio.currentConditions;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentCond {

    @SerializedName("data")
    @Expose
    private List<DatumCurWeatherbitio> data = null;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<DatumCurWeatherbitio> getData() {
        return data;
    }

    public void setData(List<DatumCurWeatherbitio> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
