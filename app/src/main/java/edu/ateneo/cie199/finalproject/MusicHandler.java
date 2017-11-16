package edu.ateneo.cie199.finalproject;


import android.content.Context;
import android.media.MediaPlayer;


/**
 * Created by John on 11/12/2017.
 */

public class MusicHandler {

    public static int MUSIC_MAIN = R.raw.main_song;
    public static int MUSIC_BATTLE = R.raw.battle_song;
    public static int MUSIC_TITLE = R.raw.opening;
    public static int SFX_SELECT = R.raw.btn_pressed;
    public static int SFX_WIN = R.raw.win_song;

    private MediaPlayer mMusicPlayer;
    private MediaPlayer mSfxPlayer;

    public void loopMusic(Context ctx, int musicId, boolean MusicEnabler){
        mMusicPlayer = MediaPlayer.create(ctx, musicId);
        mMusicPlayer.setLooping(true); // Set looping
        if (MusicEnabler) {mMusicPlayer.setVolume(20, 20);}
        else {mMusicPlayer.setVolume(0, 0);}
        mMusicPlayer.start();
    }

    public MediaPlayer getMusicPlayer() {
        return mMusicPlayer;
    }

    public void playSfx(Context ctx, int sfxId, boolean SFXEnabler){
        mSfxPlayer = MediaPlayer.create(ctx, sfxId);
        mSfxPlayer.setLooping(false); // Set looping
        if (SFXEnabler) {mSfxPlayer.setVolume(150, 150);}
        else {mSfxPlayer.setVolume(0, 0);}
        mSfxPlayer.start();

    }

    public void stopMusic(){
        mMusicPlayer.release();
        mMusicPlayer = null;
    }

    /*
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
    */
}
