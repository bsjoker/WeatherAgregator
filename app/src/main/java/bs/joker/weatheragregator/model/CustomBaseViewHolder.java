package bs.joker.weatheragregator.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 1 on 28.02.2018.
 */

public class CustomBaseViewHolder extends RecyclerView.ViewHolder {
    protected View view;
    protected ImageView icon;
    protected TextView name;
    protected TextView description;

    public CustomBaseViewHolder(View view) {
        super(view);

        this.view = view;
        this.icon = (ImageView) view.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_icon);
        this.name = (TextView) view.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_name);
        this.description = (TextView) view.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_description);
    }
}
