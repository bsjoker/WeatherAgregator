package bs.joker.weatheragregator.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 1 on 02.03.2018.
 */

public class City extends RealmObject{
    @PrimaryKey
    Integer ID;
    String name;
    double lat, lon;
}
