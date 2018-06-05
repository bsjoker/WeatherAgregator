package bs.joker.weatheragregator.mvp.view;

import com.arellomobile.mvp.MvpView;

/**
 * Created by 1 on 16.05.2018.
 */

public interface MainView extends MvpView {
    void showError(String message);
}
