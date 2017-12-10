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
 * Created by John on 10/8/2017.
 * This class handles the Custom Adapter for the list of Pokémon.
 */

public class PokémonList extends ArrayAdapter<PokémonProfile>{

    private final Activity context;
    private final ArrayList<PokémonProfile> profiles;
    Typeface font;

    /**
     * Initializes the list of Pokémon from an ArrayList of Pokémon.
     * @param context   The context needed to initialize the PokémonList object.
     * @param profiles  The ArrayList that would populate the PokémonList object.
     */
    public PokémonList(Activity context, ArrayList<PokémonProfile> profiles) {
        super(context, R.layout.list_pokemon, profiles);
        this.context = context;
        this.profiles = profiles;
        font = Typeface.createFromAsset(context.getAssets(), "generation6.ttf");
    }

    /**
     * Returns the specific view in the PokémonList given the current position of the index.
     * @param position  The index in the ListView.
     * @param view  The object in the layout.
     * @param parent    The Overhead holder.
     * @return  Specific view in the PokémonList.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_pokemon, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.txv_pokemon_name);
        TextView txtHp = (TextView) rowView.findViewById(R.id.txv_pokemon_hp);
        ImageView imageIcon = (ImageView) rowView.findViewById(R.id.img_pokemon_icon);
        ProgressBar barHp = (ProgressBar) rowView.findViewById(R.id.bar_pokemon_hp);

        txtName.setTypeface(font);
        txtHp.setTypeface(font);
        rowView.setBackground(PokemonGoApp.getShape(PokemonGoApp.POKEMON_COLOR));
        PokemonGoApp.updateHpBarColor(
                profiles.get(position).getCurrentHP(),
                profiles.get(position).getHP(),
                barHp
        );
        barHp.setMax(profiles.get(position).getHP());
        barHp.setProgress(profiles.get(position).getCurrentHP());
        txtName.setText(profiles.get(position).getNickname());
        txtHp.setText(
                "Lv"
                + profiles.get(position).getLevel()
                + "\tHP "
                + profiles.get(position).getCurrentHP()
                + "/"
                + profiles.get(position).getHP()
        );
        imageIcon.setImageResource(profiles.get(position).getDexData().getIcon());

        return rowView;
    }
}