package edu.ateneo.cie199.finalproject;

/**
 * Created by John, Duke and JV on 11/20/2017.
 * This class is an object that gathers the needed data to
 * make a Decision and handles the functions that execute it.
 */

public class Decision {
    /**
     * "The function is empty..."
     */
    public Decision() {
    }

    /**
     * Returns true if there is an error.
     * @return  The boolean value with default false for no error.
     */
    public boolean isError(){
        return false;
    }

    /**
     * Gets the error Message of the Decision.
     * @return  The error Message of the Decision.
     */
    public Message getErrorMessage(){
        return new Message();
    }

    /**
     * Executes the Decision in the battle.
     * @param battle    The Battle object where the Decision is executed.
     */
    public void execute(Battle battle){}

    /**
     * Updates the results of the Decision and adds Messages to the ArrayList in the Battle class.
     * @param battle    The Battle object where the Decision is executed.
     */
    public void updateResults(Battle battle){

    }
}

