package bs.joker.weatherforecast.rest.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bakays on 17.01.2018.
 */

public class InnerItemResponse<T> {
    public List<T> forecastday = new ArrayList<>();
    //public List<T> inner_forecastday = new ArrayList<>();
}
