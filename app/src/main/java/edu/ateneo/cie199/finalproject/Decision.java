package edu.ateneo.cie199.finalproject;

import java.util.ArrayList;

/**
 * Created by John, Duke and JV on 11/20/2017.
 * This class is an object that gathers the needed data to make a decision and executes it.
 */

public class Decision {


    /**
     * does nothing
     */
    public Decision() {
    }

    /**
     * check if there is error.
     * @return boolean value with default false for no error.
     */
    public boolean isError(){
        return false;
    }

    /**
     * gets the error message if an error is triggered
     * @return
     */
    public Message getErrorMessage(){
        return new Message();
    }

    /**
     * does nothing
     * @param battle default filler
     */
    public void execute(Battle battle){}

    /**
     * does nothing
     * @param battle default filler
     */
    public void updateResults(Battle battle){

    }

    /**
     * checks if there is no decision
     * @return boolean value with a default value true
     */
    public boolean isEmpty(){
        return true;
    }
}

