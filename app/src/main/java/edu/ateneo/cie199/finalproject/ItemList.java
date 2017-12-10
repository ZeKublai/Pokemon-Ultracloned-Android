package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/22/2017.
 * This class handles the Custom Adapter for the list of Items.
 */

public class ItemList extends ArrayAdapter<Item> {
    public static int NO_ITEM_SELECTED = -1;
    private final Activity context;
    private final ArrayList<Item> items;
    private int itemSelected = NO_ITEM_SELECTED;
    Typeface font;

    /**
     * Initialization of the ItemList.
     * @param context   The context needed to initialize the ItemList object.
     * @param items     The ArrayList that would populate the ItemList object.
     */
    public ItemList(Activity context, ArrayList<Item> items) {
        super(context, R.layout.list_item, items);
        this.context = context;
        this.items = items;
        font = Typeface.createFromAsset(context.getAssets(), PokemonApp.RETRO_FONT);
    }

    /**
     * Returns the specific view in the ItemList given the current position of the index.
     * @param position  The index in the ListView.
     * @param view      The object in the layout.
     * @param parent    The Overhead holder.
     * @return          Specific view in the ItemList.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.txv_item_name);
        ImageView imageIcon = (ImageView) rowView.findViewById(R.id.img_item_icon);

        txtName.setTypeface(font);
        if(this.itemSelected == position){
            rowView.setBackground(PokemonApp.getShape(PokemonApp.BACK_COLOR));
        }
        else{
            rowView.setBackground(PokemonApp.getShape(PokemonApp.BAG_COLOR));
        }
        txtName.setText(items.get(position).getButtonString());
        imageIcon.setImageResource(items.get(position).getImageIcon());

        return rowView;
    }

    /**
     * Gets the position of the selected Item.
     * @param itemPosition  The Item index.
     */
    public void itemSelected(int itemPosition) {
        this.itemSelected = itemPosition;
        notifyDataSetChanged();
    }
}
