package bs.joker.weatheragregator.ui.holder;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bs.joker.MyApplication;
import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.model.view.ForecastWeeklyItemViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bakays on 26.01.2018.
 */

public class ForecastWeeklyViewHolder extends BaseViewHolder<ForecastWeeklyItemViewModel> {

    private Resources mResources;
    private Context mContext;

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

//        dayOfWeek = (TextView)itemView.findViewById(R.id.day_of_week_tv);
//        description = (TextView) itemView.findViewById(R.id.description_tv);
//        tempMax = (TextView) itemView.findViewById(R.id.temp_max_tv);
//        tempMin = (TextView) itemView.findViewById(R.id.temp_min_tv);
//        icon = (ImageView) itemView.findViewById(R.id.weather_icon_iv);

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
                icon.setImageResource(R.drawable.clear_sky_d_black);
                break;
            case 2:
            case 7:
            case 8:
                    icon.setImageResource(R.drawable.mostly_sunny_d_black);
                break;
            case 3:
                icon.setImageResource(R.drawable.mostly_cloudy_d_black);
                break;
            case 4:
                icon.setImageResource(R.drawable.cloudy_d_black);
                break;
            case 5:
            case 6:
                icon.setImageResource(R.drawable.fog_black);
                break;
            case 9:
            case 18:
            case 19:
            case 20:
            case 21:
            case 24:
                icon.setImageResource(R.drawable.snow_black);
                break;
            case 10:
            case 11:
                icon.setImageResource(R.drawable.shower_black);
                break;
            case 12:
            case 13:
                icon.setImageResource(R.drawable.rain_black);
                break;
            case 14:
            case 15:
                icon.setImageResource(R.drawable.thunderstorm_black);
                break;
            case 16:
                icon.setImageResource(R.drawable.ice_pellets_black);
                break;
            case 17:
                icon.setImageResource(R.drawable.na_black);
                break;
            case 22:
            case 23:
                icon.setImageResource(R.drawable.ice_pellets_black);
                break;
            case 25:
                icon.setImageResource(R.drawable.snow_rain_black);
                break;
            case 26:
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
