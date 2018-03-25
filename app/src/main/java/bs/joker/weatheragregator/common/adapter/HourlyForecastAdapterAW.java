package bs.joker.weatheragregator.common.adapter;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bs.joker.weatheragregator.model.view.BaseViewModel;
import bs.joker.weatheragregator.model.view.ForecastHourlyItemViewModel;
import bs.joker.weatheragregator.ui.holder.BaseViewHolder;

/**
 * Created by BakayS on 30.10.2017.
 */

public class HourlyForecastAdapterAW extends RecyclerView.Adapter<BaseViewHolder<BaseViewModel>>{
    public static final String TAG = "AdapterAW";
    private List<BaseViewModel> list = new ArrayList<>();
    private List<ForecastHourlyItemViewModel> listHolder = new ArrayList<>();
    private ArrayMap<Integer, BaseViewModel> mTypeInstance = new ArrayMap<>();

//    public HourlyForecastAdapter(List<ForecastHourlyItemViewModel> listHolder) {
//        this.listHolder = listHolder;
//    }

    @Override
    public BaseViewHolder<BaseViewModel> onCreateViewHolder(ViewGroup parent, int viewType) {
        return mTypeInstance.get(viewType).createViewHolder(parent);

    }

    @Override
    public void onBindViewHolder(BaseViewHolder<BaseViewModel> holder, int position) {
        holder.bindViewHolder(getItem(position));
    }

    @Override
    public void onViewRecycled(BaseViewHolder<BaseViewModel> holder) {
        super.onViewRecycled(holder);
        holder.unBindViewHolder();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType().getValue();
    }

    public void registerTypeInstance(BaseViewModel item){
        if (!mTypeInstance.containsKey(item.getType().getValue())){
            Log.d(TAG, "Adapter type: " + item.getType().getValue());
            mTypeInstance.put(item.getType().getValue(), item);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private BaseViewModel getItem(int position) {
        return list.get(position);
    }

    public void setItems(List<BaseViewModel> items){
        for (BaseViewModel item : items){
            registerTypeInstance(item);
        }
        Log.d(TAG, "Adapter: " + items.size());
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }
}
