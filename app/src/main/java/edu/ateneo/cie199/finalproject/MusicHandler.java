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
    public static int MUSIC_MANAGE = R.raw.manage_song;
    public static int SFX_SELECT = R.raw.btn_pressed;
    public static int SFX_WIN = R.raw.win_song;

    private MediaPlayer mMusicPlayer;
    private MediaPlayer mSfxPlayer;
    private MediaPlayer mButtonSfxPlayer;

    public void initMusic(Context ctx, int musicId){
        mMusicPlayer = MediaPlayer.create(ctx, musicId);
        mMusicPlayer.setLooping(true);
    }

    public MediaPlayer getMusicPlayer() {
        return mMusicPlayer;
    }

    public void setMusicVolume(boolean musicEnabler){
        if (musicEnabler) {mMusicPlayer.setVolume(20, 20);}
        else {mMusicPlayer.setVolume(0, 0);}
    }

    public void playMusic(boolean musicEnabler){
        if(musicEnabler){
            mMusicPlayer.start();
        }
    }

    public void setButtonVolume(boolean sfxEnabler){
        if (sfxEnabler) {mButtonSfxPlayer.setVolume(150, 150);}
        else {mButtonSfxPlayer.setVolume(0, 0);}
    }

    public void playSfx(Context ctx, int sfxId, boolean sfxEnabler){
        if(sfxEnabler){
            mSfxPlayer = MediaPlayer.create(ctx, sfxId);
            mSfxPlayer.setLooping(false);
            mSfxPlayer.start();
        }
    }

    public void stopMusic(){
        mMusicPlayer.release();
        mMusicPlayer = null;
    }

    public void initButtonSfx(Context ctx){
        mButtonSfxPlayer = MediaPlayer.create(ctx, R.raw.btn_pressed);
        mButtonSfxPlayer.setLooping(false); // Set looping
    }

    public void playButtonSfx(boolean sfxEnabler){
        if(sfxEnabler){
            mButtonSfxPlayer.start();
        }
    }
    /*
    public static void MainSong(Context ctx, int raw_id) {
        bg_player = MediaPlayer.create(ctx, raw_id);
        bg_player.setLooping(true); // Set looping
        bg_player.setMusicVolume(30, 30);

        //player.release();
        bg_player.start();
    }

    public static void BattleSong(Context ctx, int raw_id) {
        battle_player = MediaPlayer.create(ctx, raw_id);
        battle_player.setLooping(true); // Set looping
        battle_player.setMusicVolume(30, 30);

        //player.release();
        battle_player.start();
    }

    public static void ButtonPressed(Context ctx) {
        btn_player = MediaPlayer.create(ctx, R.raw.btn_pressed);
        btn_player.setLooping(false); // Set looping
        btn_player.setMusicVolume(100, 100);

        //player.release();
        btn_player.start();
    }

    public static void OpeningSong(Context ctx) {
        opening_player = MediaPlayer.create(ctx, R.raw.opening);
        opening_player.setLooping(true); // Set looping
        opening_player.setMusicVolume(50, 50);
        //player.release();
        opening_player.start();
    }
    */
}
