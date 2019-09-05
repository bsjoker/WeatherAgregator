package bs.joker.weatheragregator.rest.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bakays on 05.12.2017.
 */

//Запрос определённых позиций
public class BaseItemResponse<T> {
    public T response;

    public T simpleforecast;
    public T txt_forecast;

    public List<T> item_hourly_forecast = new ArrayList<>();
    public List<T> item_hourly_forecast_aw = new ArrayList<>();
    public List<T> item_hourly_forecast_wbio = new ArrayList<>();
    public List<T> data = new ArrayList<>();

    public List<T> getHourly_forecast_aw() {
        return item_hourly_forecast_aw;
    }

    public List<T> getHourly_forecast() {
        return item_hourly_forecast;
    }

    public List<T> getItem_hourly_forecast_wbio() {
        return item_hourly_forecast_wbio;
    }

    public List<T> getHourly_data() {
        return data;
    }

    public T getResponse() {
        return response;
    }
}
