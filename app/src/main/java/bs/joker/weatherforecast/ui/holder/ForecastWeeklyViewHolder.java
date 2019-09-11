package bs.joker.weatherforecast.ui.holder;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bs.joker.MyApplication;
import bs.joker.weatherforecast.R;
import bs.joker.weatherforecast.model.view.ForecastWeeklyItemViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bakays on 26.01.2018.
 */

public class ForecastWeeklyViewHolder extends BaseViewHolder<ForecastWeeklyItemViewModel> {

    private Resources mResources;
    private Context mContext;
    private Boolean metric;

    @BindView(R.id.day_of_week_tv)
    public TextView dayOfWeek;
    @BindView(R.id.description_tv)
    public TextView description;
    @BindView(R.id.temp_max_tv)
    public TextView tempMax;
    @BindView(R.id.temp_min_tv)
    public TextView tempMin;

    @BindView(R.id.weather_icon_iv)
    public ImageView icon;

    public ForecastWeeklyViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        MyApplication.getApplicationComponent().inject(this);

        mContext = itemView.getContext();
        mResources = mContext.getResources();
    }

    @Override
    public void bindViewHolder(ForecastWeeklyItemViewModel items) {
        dayOfWeek.setText(items.getDayOfWeek());
        description.setText(items.getDescription());
        tempMax.setText(items.getTempMax() + mContext.getString(R.string.degrees));
        tempMin.setText(items.getTempMin() + mContext.getString(R.string.degrees));
        switch (items.getIcon()) {
            case 1:
            case 800:
                icon.setImageResource(R.drawable.clear_sky_d_black);
                break;
            case 2:
            case 7:
            case 8:
            case 801:
            case 802:
                    icon.setImageResource(R.drawable.mostly_sunny_d_black);
                break;
            case 3:
            case 803:
                icon.setImageResource(R.drawable.mostly_cloudy_d_black);
                break;
            case 4:
            case 804:
                icon.setImageResource(R.drawable.cloudy_d_black);
                break;
            case 5:
            case 6:
            case 700:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
                icon.setImageResource(R.drawable.fog_black);
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
                icon.setImageResource(R.drawable.snow_black);
                break;
            case 10:
            case 11:
            case 502:
            case 520:
            case 521:
            case 522:
                icon.setImageResource(R.drawable.shower_black);
                break;
            case 12:
            case 13:
            case 300:
            case 301:
            case 302:
            case 500:
            case 501:
                icon.setImageResource(R.drawable.rain_black);
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
                icon.setImageResource(R.drawable.thunderstorm_black);
                break;
            case 16:
            case 511:
            case 611:
            case 612:
                icon.setImageResource(R.drawable.ice_pellets_black);
                break;
            case 17:
            case 900:
                icon.setImageResource(R.drawable.na_black);
                break;
            case 22:
            case 23:
                icon.setImageResource(R.drawable.ice_pellets_black);
                break;
            case 25:
            case 610:
                icon.setImageResource(R.drawable.snow_rain_black);
                break;
            case 26:
            case 623:
                icon.setImageResource(R.drawable.windy_black);
                break;
            case 27:
                icon.setImageResource(R.drawable.low_temp_black);
                break;
            case 28:
                icon.setImageResource(R.drawable.high_temp_black);
                break;
            default:
                icon.setImageResource(R.drawable.na_black);
                break;
        }
    }


    @Override
    public void unBindViewHolder() {
        Log.d("VH", "UnbinderWeeklyVH");
        dayOfWeek.setText(null);
        description.setText(null);
        tempMax.setText(null);
        tempMin.setText(null);
//        icon = null;
    }
}
