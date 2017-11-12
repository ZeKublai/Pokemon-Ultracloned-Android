package edu.ateneo.cie199.finalproject;


import android.content.Context;
import android.media.MediaPlayer;


/**
 * Created by John on 11/12/2017.
 */

public class MusicBackground {

    public static MediaPlayer bg_player;
    public static MediaPlayer battle_player;
    public static MediaPlayer btn_player;
    public static MediaPlayer opening_player;

    public static void MainSong(Context ctx, int raw_id) {
        bg_player = MediaPlayer.create(ctx, raw_id);
        bg_player.setLooping(true); // Set looping
        bg_player.setVolume(30, 30);

        //player.release();
        bg_player.start();
    }

    public static void BattleSong(Context ctx, int raw_id) {
        battle_player = MediaPlayer.create(ctx, raw_id);
        battle_player.setLooping(true); // Set looping
        battle_player.setVolume(30, 30);

        //player.release();
        battle_player.start();
    }

    public static void ButtonPressed(Context ctx) {
        btn_player = MediaPlayer.create(ctx, R.raw.btn_pressed);
        btn_player.setLooping(false); // Set looping
        btn_player.setVolume(100, 100);

        //player.release();
        btn_player.start();
    }

    public static void OpeningSong(Context ctx) {
        opening_player = MediaPlayer.create(ctx, R.raw.opening);
        opening_player.setLooping(true); // Set looping
        opening_player.setVolume(50, 50);
        //player.release();
        opening_player.start();
    }
}
