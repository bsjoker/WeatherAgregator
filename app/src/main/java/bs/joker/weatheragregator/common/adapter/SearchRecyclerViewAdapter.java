package bs.joker.weatheragregator.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bs.joker.weatheragregator.R;
import bs.joker.weatheragregator.model.geonames.Geoname;

/**
 * Created by 1 on 14.03.2018.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {
    //private String[] mDataSet;
    private List<Geoname> mDataSet;
    MyItemClickListener myItemClickListener;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // наш пункт состоит только из одного TextView
        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView)itemView.findViewById(R.id.city_name_text_view);
        }
    }

    // Конструктор
    public SearchRecyclerViewAdapter(List<Geoname> dataSet, MyItemClickListener listener){
        this.mDataSet = dataSet;
        this.myItemClickListener = listener;
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public SearchRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler_item,parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myItemClickListener.onItemClick(v, vh.getPosition());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(SearchRecyclerViewAdapter.ViewHolder holder, int position) {
        // Заменяет контент отдельного view (вызывается layout manager-ом)
        Geoname city = mDataSet.get(position);
        holder.mTextView.setText(city.getName() + ", " + city.getAdminName1() + ", " + city.getCountryName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setItems(List<Geoname> items){
        Log.d("AdapterSearch", "Adapter: " + items.size());
//        mDataSet.clear();
//        mDataSet.addAll(items);
        mDataSet = items;
        notifyDataSetChanged();
    }
}