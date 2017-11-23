package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/21/2017.
 */

public class MovePhysical extends MoveDamage {
    public MovePhysical(){

    }

    public MovePhysical(String mName, Type mType, int mMaxPP, int mCurrentPP, int mPower,
                        int mAccuracy) {
        this.mName = mName;
        this.mType = mType;
        this.mMaxPP = mMaxPP;
        this.mCurrentPP = mCurrentPP;
        this.mPower = mPower;
        this.mAccuracy = mAccuracy;
    }

    @Override
    public Move generateCopy(){
        return new MovePhysical(this.mName, this.mType, this.mMaxPP, this.mCurrentPP, this.mPower, this.mAccuracy);
    }

    @Override
    public int getAttackStat(PokemonProfile profile){
        return profile.getAttack();
    }

    @Override
    public int getDefenseStat(PokemonProfile profile){
        return profile.getDefense();
    }
}
