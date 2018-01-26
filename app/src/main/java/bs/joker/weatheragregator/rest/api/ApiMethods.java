package bs.joker.weatheragregator.rest.api;

/**
 * Created by bakays on 05.12.2017.
 */

public class ApiMethods {
    //public static final String HOURLY_WUNDERGROUND = "f4405e44881d62b7/hourly/lang:RU/q/CA/Nizhnevartovsk.json";
    //public static final String HOURLY_WUNDERGROUND = "f4405e44881d62b7/hourly/lang:RU/q/CA/{cityID}.json";

    public static final String HOURLY_WUNDERGROUND = "http://api.wunderground.com/api/f4405e44881d62b7/hourly/lang:RU/q/CA/Nizhnevartovsk.json";
    public static final String DAILY_5_WUNDERGROUND = "http://api.wunderground.com/api/f4405e44881d62b7/forecast10day/lang:RU/q/CA/Nizhnevartovsk.json";

    public static final String DAILY_5_ACCUWEATHER = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/288458?apikey=fgL5zwzEG7007DJ375AF2zQdY0tLKDU5&language=ru-ru&details=true&metric=true";
    public static final String HOURLY_ACCUWEATHER = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/288458?apikey=fgL5zwzEG7007DJ375AF2zQdY0tLKDU5&language=ru-ru&details=true&metric=true";
    //public static final String DAILY_5_ACCUWEATHER = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/288458?apikey=Yx8bmNySr0Vgyw8LmG6FAfGBhQnXEXZk&language=ru-ru&details=true&metric=true";
    //public static final String HOURLY_ACCUWEATHER = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/288458?apikey=Yx8bmNySr0Vgyw8LmG6FAfGBhQnXEXZk&language=ru-ru&details=true&metric=true";

    public static final String HOURLY_DARKSKY = "https://api.darksky.net/forecast/b734ec44db2437d8e3e89daed0c53a93/60.939716,76.569628?exclude=daily,currently,alerts,flags&lang=ru&units=auto";
    public static final String DAILY_5_DARKSKY = "https://api.darksky.net/forecast/b734ec44db2437d8e3e89daed0c53a93/60.939716,76.569628?exclude=hourly,currently,alerts,flags&lang=ru&units=auto";

}