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
 * Created by John on 11/22/2017.
 * This class handles the Custom Adapter for the list of Moves.
 */

public class MoveList extends ArrayAdapter<Move> {

    private final Activity context;
    private final ArrayList<Move> moves;
    Typeface font;

    /**
     * Initializes the list of Moves from an ArrayList of Moves.
     * @param context   The context needed to initialize the MoveList object.
     * @param moves The ArrayList that would populate the MoveList object.
     */
    public MoveList(Activity context, ArrayList<Move> moves) {
        super(context, R.layout.list_move, moves);
        this.context = context;
        this.moves = moves;
        font = Typeface.createFromAsset(context.getAssets(), "generation6.ttf");
    }

    /**
     * Returns the specific view in the MoveList given the current position of the index.
     * @param position  The index in the ListView.
     * @param view  The object in the layout.
     * @param parent    The Overhead holder.
     * @return  Specific view in the MoveList.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_move, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.txv_move_name);
        TextView txtPp = (TextView) rowView.findViewById(R.id.txv_move_pp);
        ImageView imageIcon = (ImageView) rowView.findViewById(R.id.img_move_icon);

        txtName.setTypeface(font);
        txtPp.setTypeface(font);
        rowView.setBackground(PokemonApp.getShape(moves.get(position).getType().getColor()));
        txtName.setText(moves.get(position).getName());
        txtPp.setText("PP "
                + moves.get(position).getCurrentPP()
                + "/" + moves.get(position).getMaxPP()
        );
        imageIcon.setImageResource(moves.get(position).getType().getIcon());

        return rowView;
    }
}
