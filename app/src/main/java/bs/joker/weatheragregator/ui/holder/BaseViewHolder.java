package bs.joker.weatheragregator.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import bs.joker.weatheragregator.model.view.BaseViewModel;

/**
 * Created by bakays on 07.12.2017.
 */

public abstract class BaseViewHolder<Item extends BaseViewModel> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindViewHolder(Item item);
    public abstract void unBindViewHolder();
}
