package edu.ateneo.cie199.finalproject;

import java.util.ArrayList;

/**
 * Created by John on 11/20/2017.
 */

public class Decision {


    public Decision() {
    }

    public boolean isError(){
        return false;
    }

    public Message getErrorMessage(){
        return new Message();
    }

    public void execute(Battle battle){}

    public void updateResults(Battle battle){

    }

    public boolean isEmpty(){
        return true;
    }
}

