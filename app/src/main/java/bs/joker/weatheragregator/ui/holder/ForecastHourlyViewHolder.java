package bs.joker.weatheragregator.ui.holder;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.model.view.ForecastHourlyItemViewModel;
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

//    TextView time_date_text_view, description_weather_text_view, precipation, wind_speed_text_view, wind_direction_text_view, temp_text_view;
//    ImageView weather_icon_image_view;

    private Resources mResources;
    private Context mContext;

    public ForecastHourlyViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        //time_date_text_view = (TextView) itemView.findViewById(R.id.time_date_text_view);
        //description_weather_text_view = (TextView) itemView.findViewById(R.id.description_weather_text_view);
        //weather_icon_image_view = (ImageView) itemView.findViewById(R.id.weather_icon_image_view);
        //precipation = (TextView) itemView.findViewById(R.id.precipation);
        //wind_speed_text_view = (TextView) itemView.findViewById(R.id.wind_speed_text_view);
        //wind_direction_text_view = (TextView) itemView.findViewById(R.id.wind_direction_text_view);
        //temp_text_view = (TextView) itemView.findViewById(R.id.temp_text_view);

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
                if (items.isDay()) {
                    weather_icon_image_view.setImageResource(R.drawable.clear_sky_d_black);
                } else {
                    weather_icon_image_view.setImageResource(R.drawable.clear_sky_n_black);
                }
                break;
            case 2:
            case 7:
            case 8:
                if (items.isDay()) {
                    weather_icon_image_view.setImageResource(R.drawable.mostly_sunny_d_black);
                } else {
                    weather_icon_image_view.setImageResource(R.drawable.mostly_sunny_n_black);
                }
                break;
            case 3:
                if (items.isDay()) {
                    weather_icon_image_view.setImageResource(R.drawable.mostly_cloudy_d_black);
                } else {
                    weather_icon_image_view.setImageResource(R.drawable.mostly_cloudy_n_black);
                }
                break;
            case 4:
                weather_icon_image_view.setImageResource(R.drawable.cloudy_d_black);
                break;
            case 5:
            case 6:
                weather_icon_image_view.setImageResource(R.drawable.fog_black);
                break;
            case 9:
            case 18:
            case 19:
            case 20:
            case 21:
            case 24:
                weather_icon_image_view.setImageResource(R.drawable.snow_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 10:
            case 11:
                weather_icon_image_view.setImageResource(R.drawable.shower_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 12:
            case 13:
                weather_icon_image_view.setImageResource(R.drawable.rain_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 14:
            case 15:
                weather_icon_image_view.setImageResource(R.drawable.thunderstorm_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 16:
                weather_icon_image_view.setImageResource(R.drawable.ice_pellets_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 17:
                weather_icon_image_view.setImageResource(R.drawable.na_black);
                break;
            case 22:
            case 23:
                weather_icon_image_view.setImageResource(R.drawable.ice_pellets_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 25:
                weather_icon_image_view.setImageResource(R.drawable.snow_rain_black);
                precipation.setVisibility(View.VISIBLE);
                break;
            case 26:
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

        if (notation.contains("metric")) {
            wind_speed = (int) (Double.valueOf(items.getWindSpeed()) * 0.28);
            wind_speed_text_view.setText(wind_speed + " " + mResources.getString(R.string.speed_metric));
            wind_direction_text_view.setText(" - " + items.getWindDirection());
            temp_text_view.setText(items.getTemp() + mResources.getString(R.string.degrees));
        } else {
            wind_speed_text_view.setText(items.getWindSpeed() + " " + mResources.getString(R.string.speed_english));
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
