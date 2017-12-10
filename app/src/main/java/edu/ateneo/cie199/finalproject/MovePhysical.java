package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 * A subclass of damage dealing moves that only take
 * the physical stats of the attacker and defender.
 */

public class MovePhysical extends MoveDamage {

    /**
     * "The function is empty..."
     */
    public MovePhysical(){

    }

    /**
     * Creates a Physical Move given the name, type, maximum PP, current PP, power and accuracy.
     * @param mName The name of the created Move.
     * @param mType The type of the created Move.
     * @param mMaxPP    The maximum PP value of the created Move.
     * @param mCurrentPP    The current PP value of the created Move.
     * @param mPower    The power value of the created Move.
     * @param mAccuracy The accuracy value of the created Move.
     */
    public MovePhysical(String mName,
                        Type mType,
                        int mMaxPP,
                        int mCurrentPP,
                        int mPower,
                        int mAccuracy) {
        this.mName = mName;
        this.mType = mType;
        this.mMaxPP = mMaxPP;
        this.mCurrentPP = mCurrentPP;
        this.mPower = mPower;
        this.mAccuracy = mAccuracy;
    }

    /**
     * Returns a duplicate of the Move object.
     * @return  A duplicate of the Move object.
     */
    @Override
    public Move generateCopy(){
        return new MovePhysical(this.mName,
                this.mType,
                this.mMaxPP,
                this.mCurrentPP,
                this.mPower,
                this.mAccuracy);
    }

    /**
     * Returns the attack stat of the given PokémonProfile.
     * @param profile   The PokémonProfile where the total attack stat value is obtained.
     * @return  The attack stat of the given PokémonProfile.
     */
    @Override
    public int getAttackStat(PokémonProfile profile){
        return profile.getAttack();
    }

    /**
     * Returns the defense stat of the given PokémonProfile
     * @param profile   The PokémonProfile where the total defense stat value is obtained.
     * @return  The defense stat of the given PokémonProfile.
     */
    @Override
    public int getDefenseStat(PokémonProfile profile){
        return profile.getDefense();
    }
}
