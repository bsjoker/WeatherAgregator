package bs.joker.weatheragregator.rest.api;

import bs.joker.weatheragregator.model.PreferencesHelper;

/**
 * Created by bakays on 05.12.2017.
 */

public class ApiMethods {
    public static final String GEONAMES_SEARCH = "http://api.geonames.org/searchJSON?q={query}&maxRows=5&fuzzy=0.8&featureCode=PPLC&featureCode=PPL&featureCode=PPLA&featureCode=PPLA2&featureCode=PPLA3&featureCode=PPLA4&lang={lang}&username=bsjoker";

    public static final String ASTRONOMY_WUNDERGROUND = "http://api.wunderground.com/api/f4405e44881d62b7/astronomy/lang:{lang}/q/{lat},{lng}.json";
    public static final String CURRENT_WUNDERGROUND = "http://api.wunderground.com/api/f4405e44881d62b7/conditions/lang:{lang}/q/{lat},{lng}.json";
    public static final String HOURLY_WUNDERGROUND = "http://api.wunderground.com/api/f4405e44881d62b7/hourly/lang:{lang}/q/{lat},{lng}.json";
    public static final String DAILY_5_WEEKLY_WUNDERGROUND = "http://api.wunderground.com/api/f4405e44881d62b7/forecast10day/lang:{lang}/q/{lat},{lng}.json";

    //public static final String CURRENT_CONDITIONS_IO = "https://api.weatherbit.io/v2.0/current?lat=54.74306&lon=55.96779&lang=ru&units=M&key={apiKey}";
    public static final String CURRENT_CONDITIONS_IO = "https://api.weatherbit.io/v2.0/current?lat={lat}&lon={lng}&lang={lang}&units={unit}&key={apiKey}"; // M - [DEFAULT] Metric (Celcius, m/s, mm)    //I - Fahrenheit (F, mph, in)
    public static final String HOURLY_WEATHERBIT_IO = "https://api.weatherbit.io/v2.0/forecast/hourly?lat={lat}&lon={lng}&lang={lang}&units={unit}&key={apiKey}&hours=12";

    //public static final String DAILY_5_ACCUWEATHER = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/{cityKey}?apikey=fgL5zwzEG7007DJ375AF2zQdY0tLKDU5&language={lang}&details=true&metric{unit}";
    //public static final String HOURLY_ACCUWEATHER = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/{cityKey}?apikey=fgL5zwzEG7007DJ375AF2zQdY0tLKDU5&language={lang}&details=true&metric{unit}";
//    public static final String DAILY_5_ACCUWEATHER = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/{cityKey}?apikey=Yx8bmNySr0Vgyw8LmG6FAfGBhQnXEXZk&language={lang}&details=true&metric{unit}";
//    public static final String HOURLY_ACCUWEATHER = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/{cityKey}?apikey=Yx8bmNySr0Vgyw8LmG6FAfGBhQnXEXZk&language={lang}&details=true&metric{unit}";
//    public static final String CITY_KEY_ACCUWEATHER = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=Yx8bmNySr0Vgyw8LmG6FAfGBhQnXEXZk&q={lat},{lng}";
    public static final String DAILY_5_ACCUWEATHER = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/{cityKey}?apikey={apiKey}&language={lang}&details=true&metric{unit}";
    public static final String HOURLY_ACCUWEATHER = "http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/{cityKey}?apikey={apiKey}&language={lang}&details=true&metric{unit}";
    public static final String CITY_KEY_ACCUWEATHER = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey={apiKey}&q={lat},{lng}";
    //public static final String CITY_KEY_ACCUWEATHER = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=fgL5zwzEG7007DJ375AF2zQdY0tLKDU5&q={lat},{lng}";
    //public static final String CITY_KEY_ACCUWEATHER = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=Yx8bmNySr0Vgyw8LmG6FAfGBhQnXEXZk&q=54.74306,55.96779";

    public static final String HOURLY_DARKSKY = "https://api.darksky.net/forecast/b734ec44db2437d8e3e89daed0c53a93/{lat},{lng}?exclude=daily,currently,alerts,flags&lang={lang}&units={unit}";
    //public static final String DAILY_5_DARKSKY = "https://api.darksky.net/forecast/b734ec44db2437d8e3e89daed0c53a93/60.939716,76.569628?exclude=hourly,currently,alerts,flags&lang=ru&units=auto";
    public static final String DAILY_5_DARKSKY = "https://api.darksky.net/forecast/b734ec44db2437d8e3e89daed0c53a93/{lat},{lng}?exclude=hourly,currently,alerts,flags&lang={lang}&units={unit}";//(si/us)
}