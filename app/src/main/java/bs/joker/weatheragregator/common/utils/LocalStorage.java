package bs.joker.weatheragregator.common.utils;

import io.reactivex.Observable;

/**
 * Created by 1 on 15.02.2018.
 */

public interface LocalStorage {
    void writeMessage(String message);
    String readMessage();

}
