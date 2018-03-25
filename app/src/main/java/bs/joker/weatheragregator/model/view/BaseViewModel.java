package bs.joker.weatheragregator.model.view;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.ui.holder.BaseViewHolder;

/**
 * Created by bakays on 06.12.2017.
 */

public abstract class BaseViewModel {
    public abstract LayoutTypes getType();

    public BaseViewHolder createViewHolder(ViewGroup parent){
        return onCreateViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(getType().getValue(), parent, false));
    }

    protected abstract BaseViewHolder onCreateViewHolder(View view);

    public enum LayoutTypes {
        ForecastHourly(R.layout.item_forecast),
        ForecastDaily(R.layout.item_forecast_5),
        ForecastWeekly(R.layout.item_forecast_7);

        private final int id;

        LayoutTypes(int resId) {
            this.id = resId;
        }

        @LayoutRes
        public int getValue(){
            return id;
        }
    }
}
