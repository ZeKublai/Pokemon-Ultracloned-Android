package edu.ateneo.cie199.finalproject;

import static java.lang.Math.floor;

/**
 * Created by John on 11/21/2017.
 */

public abstract class MoveDamage extends Move {

    @Override
    public void execute(int accuracyResult,
                        int criticalResult,
                        PokemonProfile attacker,
                        PokemonProfile defender){
        if(accuracyResult < mAccuracy) {
            int attackStat = getAttackStat(attacker);
            int defenseStat = getDefenseStat(defender);

            double damage = floor(floor(floor(2 * attacker.getLevel() / 5 + 2) *
                    attackStat * mPower / defenseStat) / 50) + 2;

            Type defenderType1 = defender.getDexData().getType1();
            Type defenderType2 = defender.getDexData().getType2();

            double typeMultiplier1 = mType.getMultiplier()[defenderType1.getId()];
            double typeMultiplier2 = mType.getMultiplier()[defenderType2.getId()];
            double totalTypeMultiplier = typeMultiplier1 * typeMultiplier2;

            if (damage < MIN_DAMAGE) {
                damage = MIN_DAMAGE;
            }

            damage = damage * totalTypeMultiplier;

            if (criticalResult < 1) {
                damage = damage * 2;
            }

            defender.setCurrentHP(defender.getCurrentHP() - (int) (damage));
            if (defender.getCurrentHP() < PokemonProfile.MIN_HP) {
                defender.setCurrentHP(PokemonProfile.MIN_HP);
            }
        }
        setCurrentPP(mCurrentPP - 1);
    }

    public boolean executeRecoil(PokemonProfile profile){
        return false;
    };

    public abstract int getAttackStat(PokemonProfile profile);
    public abstract int getDefenseStat(PokemonProfile profile);
}
