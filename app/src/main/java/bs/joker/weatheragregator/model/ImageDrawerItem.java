package bs.joker.weatheragregator.model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.BaseDescribeableDrawerItem;
import com.mikepenz.materialdrawer.model.BaseViewHolder;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.List;

import bs.joker.weatheragregator.R;

/**
 * Created by 1 on 28.02.2018.
 */

public class ImageDrawerItem extends BaseDescribeableDrawerItem<ImageDrawerItem, ImageDrawerItem.ViewHolder> {

    @Override
    public int getType() {
        return R.id.material_drawer_item_image;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.material_drawer_item_image;
    }

    @Override
    public void bindView(final ImageDrawerItem.ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);

        Context ctx = viewHolder.itemView.getContext();

        //bind the basic view parts
        bindViewHelper(viewHolder);

        //handle the switch
        viewHolder.imageView.setImageResource(android.R.drawable.ic_delete);//setOnCheckedChangeListener(null);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = viewHolder.itemView.getId();

                Toast.makeText(ctx, String.valueOf(id), Toast.LENGTH_LONG).show();
                //Need to delete current Item????
            }
        });
        viewHolder.getItemId();

        //add a onDrawerItemClickListener here to be able to check / uncheck if the drawerItem can't be selected
        withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                if (!isSelectable()) {
//                    checked = !checked;
//                    viewHolder.checkBox.setChecked(checked);
//                }

                return false;
            }
        });

        //call the onPostBindView method to trigger post bind view actions (like the listener to modify the item if required)
        onPostBindView(this, viewHolder.itemView);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    public static class ViewHolder extends BaseViewHolder {
        private ImageView imageView;

        private ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.material_drawer_iv);
        }
    }
}
