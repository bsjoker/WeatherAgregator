package bs.joker.weatheragregator.common.utils;

import android.content.Context;

import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.ui.activity.ScrollActivity;

/**
 * Created by bakays on 27.12.2017.
 */

public class ConvertDescriptionCode {
    public static int convertCodeAW(int input){
        int outputCode = 17;
        switch (input){
            case 1:
            case 2:
            case 33:
            case 34:
                outputCode = 1;
                break;
            case 3:
            case 4:
            case 35:
            case 36:
                outputCode = 2;
                break;
            case 5:
            case 11:
            case 37:
                outputCode = 5;
                break;
            case 6:
            case 38:
                outputCode = 3;
                break;
            case 7:
            case 8:
                outputCode = 4;
                break;
            case 12:
            case 13:
            case 39:
            case 40:
                outputCode = 10;
                break;
            case 18:
            case 26:
                outputCode = 12;
                break;
            case 15:
            case 16:
            case 17:
            case 41:
            case 42:
                outputCode = 14;
                break;
            case 19:
            case 20:
            case 21:
            case 43:
                outputCode = 16;
                break;
            case 22:
            case 24:
            case 44:
                outputCode = 9;
                break;
            case 29:
                outputCode = 25;
                break;
            case 32:
                outputCode = 26;
                break;
            case 31:
                outputCode = 27;
                break;
            case 30:
                outputCode = 28;
                break;
        }
      return outputCode;
    }

    public static int convertCodeDS(String input){
        int outputCode = 17;
        switch (input){
            case "clear-day":
            case "clear-night":
                outputCode = 1;
                break;
            case "partly-cloudy-day":
            case "partly-cloudy-night":
                outputCode = 2;
                break;
            case "cloudy":
                outputCode = 4;
                break;
            case "rain":
                outputCode = 12;
                break;
            case "sleet":
                outputCode = 25;
                break;
            case "snow":
                outputCode = 9;
                break;
            case "wind":
                outputCode = 26;
                break;
            case "fog":
                outputCode = 5;
                break;
            default:
                outputCode = 17;
                break;
        }
        return outputCode;
    }

    public static String convertDescriptionDS(String input){
        Context context = ScrollActivity.link;
        String outputCode;
        switch (input){
            case "clear-day":
            case "clear-night":
                outputCode = context.getResources().getString(R.string.clear);//"Ясно";
                break;
            case "partly-cloudy-day":
            case "partly-cloudy-night":
                outputCode = context.getResources().getString(R.string.partly_cloudy);
                break;
            case "cloudy":
                outputCode = context.getResources().getString(R.string.cloudy);
                break;
            case "rain":
                outputCode = context.getResources().getString(R.string.rainAW);
                break;
            case "sleet":
                outputCode = context.getResources().getString(R.string.sleet);
                break;
            case "snow":
                outputCode = context.getResources().getString(R.string.snowD5AW);
                break;
            case "wind":
                outputCode = context.getResources().getString(R.string.windAW);
                break;
            case "fog":
                outputCode = context.getResources().getString(R.string.fog);
                break;
            default:
                outputCode = context.getResources().getString(R.string.na);
                break;
        }
        return outputCode;
    }

    public static int convertCodeD5WU(String input){
        int outputCode = 17;
        switch (input){
            case "chanceflurries":
            case "chancesleet":
            case "sleet":
            case "flurries":
            case "nt_chanceflurries":
            case "nt_chancesleet":
            case "nt_sleet":
            case "nt_flurries":
                outputCode = 16;
                break;
            case "chancerain":
            case "rain":
            case "nt_chancerain":
            case "nt_rain":
                outputCode = 12;
                break;

            case "chancesnow":
            case "snow":
            case "nt_chancesnow":
            case "nt_snow":
                outputCode = 9;
                break;

            case "chancetstorms":
            case "tstorms":
            case "nt_chancetstorms":
            case "nt_tstorms":
                outputCode = 14;
                break;

            case "cloudy":
            case "nt_cloudy":
                outputCode = 4;
                break;

            case "fog":
            case "hazy":
            case "nt_fog":
            case "nt_hazy":
                outputCode = 5;
                break;

            case "mostlycloudy":
            case "partlysunny":
            case "nt_mostlycloudy":
            case "nt_partlysunny":
                outputCode = 3;
                break;

            case "mostlysunny":
            case "partlycloudy":
            case "nt_mostlysunny":
            case "nt_partlycloudy":
                outputCode = 2;
                break;

            case "clear":
            case "sunny":
            case "nt_clear":
            case "nt_sunny":
                outputCode = 1;
                break;

            default:
                outputCode = 17;
                break;
        }
        return outputCode;
    }
}
