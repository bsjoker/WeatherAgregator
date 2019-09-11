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
import bs.joker.weatherforecast.model.view.ForecastDaily5ItemViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bakays on 15.01.2018.
 */

public class ForecastDaily5ViewHolder extends BaseViewHolder<ForecastDaily5ItemViewModel> {

    @BindView(R.id.date_tv)
    public TextView date_tv;
    @BindView(R.id.temp_max)
    public TextView temp_max;
    @BindView(R.id.temp_min)
    public TextView temp_min;
    @BindView(R.id.description_weather_day_text_view)
    public TextView description_day;
    @BindView(R.id.description_weather_night_text_view)
    public TextView description_night;

    @BindView(R.id.icon_day)
    public ImageView icon_day;
    @BindView(R.id.icon_night)
    public ImageView icon_night;

    Integer icon;

    private Context mContext;
    private Resources mResources;
    private Boolean metric;

    public ForecastDaily5ViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        MyApplication.getApplicationComponent().inject(this);

        metric = PreferencesHelper.getSharedPreferences().getBoolean("metric", true);

        String sp = PreferencesHelper.getSharedPreferences().getString("111", "Error");
        Log.d("FD5VH", "SharedPrefs: " + sp);
        mContext = itemView.getContext();
        mResources = mContext.getResources();
    }

    @Override
    public void bindViewHolder(ForecastDaily5ItemViewModel items) {
        date_tv.setText(items.getDate_tv());
        if (metric) {
            temp_max.setText(items.getTemp_max() + mResources.getString(R.string.degrees) + mResources.getString(R.string.Celcius));
            temp_min.setText(items.getTemp_min() + mResources.getString(R.string.degrees) + mResources.getString(R.string.Celcius));
        }else {
            temp_max.setText(items.getTemp_max() + mResources.getString(R.string.degrees) + mResources.getString(R.string.Farengeit));
            temp_min.setText(items.getTemp_min() + mResources.getString(R.string.degrees) + mResources.getString(R.string.Farengeit));
        }
        description_day.setText(items.getDescription_day());
        description_night.setText(items.getDescription_night());
        icon_day.setImageResource(getIcon(items.getIcon_day(), true));
        icon_night.setImageResource(getIcon(items.getIcon_night(), false));
    }

    public int getIcon(int code, boolean isDay){
        switch (code) {
            case 1:
            case 800:
                if(isDay) {
                    icon = R.drawable.clear_sky_d_black;
                }else {
                icon = R.drawable.clear_sky_n_black;}
                break;
            case 2:
            case 7:
            case 8:
            case 801:
            case 802:
                if(isDay) {
                    icon = R.drawable.mostly_sunny_d_black;
                }else {
                icon = R.drawable.mostly_sunny_n_black;}
                break;
            case 3:
            case 803:
                if(isDay) {
                    icon = R.drawable.mostly_cloudy_d_black;
                }else {
                icon = R.drawable.mostly_cloudy_n_black;}
                break;
            case 4:
            case 804:
                if(isDay) {
                    icon = R.drawable.cloudy_d_black;
                }else {
                icon = R.drawable.cloudy_d_black;}
                break;
            case 5:
            case 6:
            case 700:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
                if(isDay) {
                    icon = R.drawable.fog_black;
                }else {
                icon = R.drawable.fog_black;}
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
                if(isDay) {
                    icon = R.drawable.snow_black;
                }else {
                icon = R.drawable.snow_black;}
                break;
            case 10:
            case 11:
            case 502:
            case 520:
            case 521:
            case 522:
                if(isDay) {
                    icon = R.drawable.shower_black;
                }else {
                icon = R.drawable.shower_black;}
                break;
            case 12:
            case 13:
            case 300:
            case 301:
            case 302:
            case 500:
            case 501:
                if(isDay) {
                    icon = R.drawable.rain_black;
                }else {
                icon = R.drawable.rain_black;}
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
                if(isDay) {
                    icon = R.drawable.thunderstorm_black;
                }else {
                icon = R.drawable.thunderstorm_black;}
                break;
            case 16:
            case 511:
            case 611:
            case 612:
                if(isDay) {
                    icon = R.drawable.ice_pellets_black;
                }else {
                icon = R.drawable.ice_pellets_black;}
                break;
            case 17:
            case 900:
                if(isDay) {
                    icon = R.drawable.na_black;
                }else {
                icon = R.drawable.na_black;}
                break;
            case 22:
            case 23:
                if(isDay) {
                    icon = R.drawable.ice_pellets_black;
                }else {
                icon = R.drawable.ice_pellets_black;}
                break;
            case 25:
            case 610:
                if(isDay) {
                    icon = R.drawable.snow_rain_black;
                }else {
                icon = R.drawable.snow_rain_black;}
                break;
            case 26:
            case 623:
                if(isDay) {
                    icon = R.drawable.windy_black;
                }else {
                icon = R.drawable.windy_black;}
                break;
            case 27:
                if(isDay) {
                    icon = R.drawable.low_temp_black;
                }else {
                icon = R.drawable.low_temp_black;}
                break;
            case 28:
                if(isDay) {
                    icon = R.drawable.high_temp_black;
                }else {
                icon = R.drawable.high_temp_black;}
                break;
            default:
                if(isDay) {
                    icon = R.drawable.na_black;
                }else {
                icon = R.drawable.na_black;}
                break;
        }
        return icon;
    }

    @Override
    public void unBindViewHolder() {
        Log.d("VH", "UnbinderDailyVH");
        date_tv.setText(null);
        description_day.setText(null);
        description_night.setText(null);
        temp_max.setText(null);
        temp_min.setText(null);
    }
}
