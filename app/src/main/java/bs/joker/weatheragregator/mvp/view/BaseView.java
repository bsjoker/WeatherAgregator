package bs.joker.weatheragregator.mvp.view;

import android.text.format.Time;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import bs.joker.weatheragregator.model.geonames.Geoname;
import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.weatherbitio.currentConditions.DatumCurWeatherbitio;
import bs.joker.weatheragregator.model.wunderground.current.CurrentObservation;

/**
 * Created by bakays on 24.10.2017.
 */

public interface BaseView extends MvpView {
    void showError(String message);
    void setCurrentCond(DatumCurWeatherbitio currentCond, Time currentTime);
    void setCityKeyAWToDatabase(String key);
    void setItemsCIty(List<Geoname> items);
}
