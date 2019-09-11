package bs.joker.weatherforecast.mvp.view;

import android.text.format.Time;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import bs.joker.weatherforecast.model.geonames.Geoname;
import bs.joker.weatherforecast.model.weatherbitio.currentConditions.DatumCurWeatherbitio;

/**
 * Created by bakays on 24.10.2017.
 */

public interface BaseView extends MvpView {
    void showError(String message);
    void setCurrentCond(DatumCurWeatherbitio currentCond, Time currentTime);
    void setCityKeyAWToDatabase(String key);
    void setItemsCIty(List<Geoname> items);
}
