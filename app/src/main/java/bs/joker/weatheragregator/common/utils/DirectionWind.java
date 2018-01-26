package bs.joker.weatheragregator.common.utils;

/**
 * Created by bakays on 28.12.2017.
 */

public class DirectionWind {
    public static String getDirectWind(int direct){
        String windDir = "-";
        if (direct>=0 & direct<=22 || direct>=338 & direct<=360) windDir = "С";
        if (direct>=23 & direct<=67) windDir = "СВ";
        if (direct>=68 & direct<=112) windDir = "В";
        if (direct>=113 & direct<=157) windDir = "ЮВ";
        if (direct>=158 & direct<=202) windDir = "Ю";
        if (direct>=203 & direct<=247) windDir = "ЮЗ";
        if (direct>=248 & direct<=292) windDir = "З";
        if (direct>=293 & direct<=337) windDir = "СЗ";
        return windDir;
    }
}
