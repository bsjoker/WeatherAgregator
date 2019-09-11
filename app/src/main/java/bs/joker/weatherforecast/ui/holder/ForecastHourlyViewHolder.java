package bs.joker.weatherforecast.ui.holder;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bs.joker.MyApplication;
import bs.joker.weatherforecast.R;
import bs.joker.weatherforecast.model.PreferencesHelper;
import bs.joker.weatherforecast.model.view.ForecastHourlyItemViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bakays on 07.12.2017.
 */

public class ForecastHourlyViewHolder extends BaseViewHolder<ForecastHourlyItemViewModel> {

    @BindView(R.id.time_date_text_view)
    public TextView time_date_text_view;
    @BindView(R.id.description_weather_text_view)
    public TextView description_weather_text_view;
    @BindView(R.id.precipation)
    public TextView precipation;
    @BindView(R.id.wind_direction_text_view)
    public TextView wind_direction_text_view;
    @BindView(R.id.wind_speed_text_view)
    public TextView wind_speed_text_view;
    @BindView(R.id.temp_text_view)
    public TextView temp_text_view;

    @BindView(R.id.weather_icon_image_view)
    public ImageView weather_icon_image_view;

    private Resources mResources;
    private Context mContext;

    public ForecastHourlyViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        MyApplication.getApplicationComponent().inject(this);

        mContext = itemView.getContext();
        mResources = mContext.getResources();
    }

    @Override
    public void bindViewHolder(ForecastHourlyItemViewModel items) {
        Log.d("VH", "bindHourlyVH");
        Log.d("VH", "temp: " + items.getTemp());
        final String notation = "metric";
        int wind_speed;
        time_date_text_view.setText(items.getTimeDate());
        description_weather_text_view.setText(items.getDescriptionWeather());
        precipation.setText(items.getPrecipation());

        switch (items.getDescriptionCode()) {
            case 1:
            case 800:
                if (items.isDay()) {
                    weather_icon_image_view.setImageResource(R.drawable.clear_sky_d_black);
                } else {
                    weather_icon_image_view.setImageResource(R.drawable.clear_sky_n_black);
                }
                break;
            case 2:
            case 7:
            case 8:
            case 801:
            case 802:
                if (items.isDay()) {
                    weather_icon_image_view.setImageResource(R.drawable.mostly_sunny_d_black);
                } else {
                    weather_icon_image_view.setImageResource(R.drawable.mostly_sunny_n_black);
                }
                break;
            case 3:
            case 803:
                if (items.isDay()) {
                    weather_icon_image_view.setImageResource(R.drawable.mostly_cloudy_d_black);
                } else {
                    weather_icon_image_view.setImageResource(R.drawable.mostly_cloudy_n_black);
                }
                break;
            case 4:
            case 804:
                weather_icon_image_view.setImageResource(R.drawable.cloudy_d_black);
                break;
            case 5:
            case 6:
            case 700:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
                weather_icon_image_view.setImageResource(R.drawable.fog_black);
                break;
            case 9:
            case 18:
            case 19:
            case 20:
            case 21:
            case 24:
            case 600:
            case 601:
            case 602:
            case 621:
            case 622:
                weather_icon_image_view.setImageResource(R.drawable.snow_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 10:
            case 11:
            case 502:
            case 520:
            case 521:
            case 522:
                weather_icon_image_view.setImageResource(R.drawable.shower_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 12:
            case 13:
            case 300:
            case 301:
            case 302:
            case 500:
            case 501:
                weather_icon_image_view.setImageResource(R.drawable.rain_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 14:
            case 15:
            case 200:
            case 201:
            case 202:
            case 230:
            case 231:
            case 232:
            case 233:
                weather_icon_image_view.setImageResource(R.drawable.thunderstorm_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 16:
            case 511:
            case 611:
            case 612:
                weather_icon_image_view.setImageResource(R.drawable.ice_pellets_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 17:
            case 900:
                weather_icon_image_view.setImageResource(R.drawable.na_black);
                break;
            case 22:
            case 23:
                weather_icon_image_view.setImageResource(R.drawable.ice_pellets_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 25:
            case 610:
                weather_icon_image_view.setImageResource(R.drawable.snow_rain_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 26:
            case 623:
                weather_icon_image_view.setImageResource(R.drawable.windy_black);
                break;
            case 27:
                weather_icon_image_view.setImageResource(R.drawable.low_temp_black);
                break;
            case 28:
                weather_icon_image_view.setImageResource(R.drawable.high_temp_black);
                break;
            default:
                weather_icon_image_view.setImageResource(R.drawable.na_black);
                break;
        }

//        if (PreferencesHelper.getSharedPreferences().getBoolean("metric", true)) {
//            wind_speed_text_view.setText(items.getWindSpeed() + " " + mResources.getString(R.string.speed_metric));
//            wind_direction_text_view.setText(" - " + items.getWindDirection());
//            temp_text_view.setText(items.getTemp() + mResources.getString(R.string.degrees));
//        } else {
//            //Double wSpd = Double.valueOf(items.getWindSpeed());
//            wind_speed_text_view.setText(items.getWindSpeed() + " " + mResources.getString(R.string.speed_english));
//            wind_direction_text_view.setText(" - " + items.getWindDirection());
//            temp_text_view.setText(items.getTemp() + mResources.getString(R.string.degrees));
//        }

        if (PreferencesHelper.getSharedPreferences().getBoolean("metric", true)) {
        wind_speed = (int) (Double.valueOf(items.getWindSpeed()) * 0.28);
        wind_speed_text_view.setText(wind_speed + " " + mResources.getString(R.string.speed_metric));
            wind_direction_text_view.setText(" - " + items.getWindDirection());
            temp_text_view.setText(items.getTemp() + mResources.getString(R.string.degrees));
        } else {
            //Double wSpd = Double.valueOf(items.getWindSpeed());
            wind_speed = (int)Math.round (Double.valueOf(items.getWindSpeed()));
            wind_speed_text_view.setText(wind_speed + " " + mResources.getString(R.string.speed_english));
            wind_direction_text_view.setText(" - " + items.getWindDirection());
            temp_text_view.setText(items.getTemp() + mResources.getString(R.string.degrees));
        }
    }

    @Override
    public void unBindViewHolder() {
        Log.d("VH", "UnbinderHourlyVH");
        time_date_text_view.setText(null);
        description_weather_text_view.setText(null);
        wind_speed_text_view.setText(null);
        temp_text_view.setText(null);
//      weather_icon_image_view = null;
    }
}
