package edu.ateneo.cie199.finalproject;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by John on 11/22/2017.
 */

public class ItemList extends ArrayAdapter<Item> {
    public static int NO_ITEM_SELECTED = -1;
    private final Activity context;
    private final ArrayList<Item> items;
    private int itemSelected = NO_ITEM_SELECTED;
    Typeface font;

    public ItemList(Activity context, ArrayList<Item> items) {
        super(context, R.layout.list_item, items);
        this.context = context;
        this.items = items;
        font = Typeface.createFromAsset(context.getAssets(), "generation6.ttf");
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.txv_item_name);
        ImageView imageIcon = (ImageView) rowView.findViewById(R.id.img_item_icon);

        txtName.setTypeface(font);
        if(this.itemSelected == position){
            rowView.setBackground(PokemonGoApp.getShape(PokemonGoApp.BACK_COLOR));
        }
        else{
            rowView.setBackground(PokemonGoApp.getShape(PokemonGoApp.BAG_COLOR));
        }
        txtName.setText(items.get(position).getButtonString());
        imageIcon.setImageResource(items.get(position).getImageIcon());

        return rowView;
    }

    public void itemSelected(int itemPosition) {
        //This method will keep track which position of the List is Selected and the background color of it is inside this.
        this.itemSelected = itemPosition;
        notifyDataSetChanged();
    }
}
