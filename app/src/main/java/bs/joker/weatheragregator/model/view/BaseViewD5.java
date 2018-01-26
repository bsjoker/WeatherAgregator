package bs.joker.weatheragregator.model.view;

import com.arellomobile.mvp.MvpView;

import java.util.List;

/**
 * Created by bakays on 18.01.2018.
 */

public interface BaseViewD5 extends MvpView {
    void showError(String message);
    void setItemsD5WU(List<BaseViewModel> items);
}
