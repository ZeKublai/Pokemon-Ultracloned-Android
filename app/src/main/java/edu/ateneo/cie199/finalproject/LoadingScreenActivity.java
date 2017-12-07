package edu.ateneo.cie199.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

public class LoadingScreenActivity extends AppCompatActivity {
    private boolean dataLoaded = false;
    private boolean movesApiState = false;
    private boolean pokemonApiState = false;

    private class APIData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            final PokemonGoApp app = (PokemonGoApp) getApplication();
            app.loadAllPokemonTypes();
            try {
                if(app.loadAllMovesApi()){
                    Log.d("Load Moves", "LOADING MOVES..");
                    movesApiState = true;
                }
                else{
                    movesApiState= false;
                }
            } catch (JSONException e) {
                Log.e("PARSE ERROR", "FAILED TO PARSE JSON MOVE DATA");
                e.printStackTrace();
            }
            return null;
        }
    }

    private class PokemonAPI extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            final PokemonGoApp app = (PokemonGoApp) getApplication();
            try {
                if(app.loadAllPokemonApi()){
                    Log.d("Load Pokemons", "LOADING PKMN...");
                    pokemonApiState = true;
                }
                else{
                    pokemonApiState = false;
                }
            } catch (JSONException e) {
                Log.e("Parsing err", "Failed to save json Pokemon data");
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) throws Error {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loading_screen);
        final Dialog dialog = new Dialog(LoadingScreenActivity.this);
        dialog.setContentView(R.layout.activity_loading_screen);


        final ProgressBar loadBar = (ProgressBar) dialog.findViewById(R.id.progBar_load);

        final PokemonGoApp app = (PokemonGoApp) getApplication();

        Intent recvdIntent = getIntent();
        final boolean loadData = recvdIntent.getBooleanExtra("Continue?", false);
        if (app.isNetworkConnected()) {
            try {
                APIData data = new APIData();
                data.execute();
                Log.e("BAR PROG", Double.toString(loadBar.getMax() / 4));
                loadBar.setMax(100);
                loadBar.setProgress((int) Math.floor(loadBar.getMax() / 4));
                PokemonAPI pokeData = new PokemonAPI();
                pokeData.execute();
                loadBar.setMax(76);
                loadBar.setProgress((int) Math.floor(loadBar.getMax() * 2 / 4));
                app.loadAllItems();
                loadBar.setMax(56);
                loadBar.setProgress((int) Math.floor(loadBar.getMax() * 3 / 4));
            } catch (Error e) {
                Log.e("Error", "Unable to load moves");
            }


            final Handler handler = new Handler();
            final Runnable delay = new Runnable() {
                @Override
                public void run() {

                    if(pokemonApiState && movesApiState) {
                        if (loadData) {
                            app.loadPlayerDate();
                            loadBar.setMax(100);
                            loadBar.setProgress(loadBar.getMax());
                        } else {
                            loadBar.setMax(75);
                            loadBar.setProgress(loadBar.getMax());
                            app.initPlayer();
                        }

                        if (loadBar.getProgress() == loadBar.getMax()) {
                            Intent beginMainActivity = new Intent(LoadingScreenActivity.this, MainActivity.class);
                            setResult(RESULT_OK);
                            startActivity(beginMainActivity);
                        } else {
                            Log.e("Error Loading", "There was an error loading the data");
                            setResult(RESULT_CANCELED, new Intent().putExtra("MSG", "Data was not properly loaded, please retry"));
                            finish();
                        }
                    }
                    else{
                        setResult(RESULT_CANCELED, new Intent().putExtra("MSG", "Data was not properly loaded, please retry"));
                        finish();
                    }
                }
            };
            handler.postDelayed(delay, 10000);
        }
        else{
            Toast.makeText(LoadingScreenActivity.this, "Your device is not connected to the internet", Toast.LENGTH_LONG).show();
        }
    }
}