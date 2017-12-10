package edu.ateneo.cie199.finalproject;

import static java.lang.Math.floor;

/**
 * Created by John on 11/21/2017.
 * This subclass's primary function contains the functions specific for damage
 * dealing Moves. It mainly reduces the defending PokémonProfile's HP.
 */

public abstract class MoveDamage extends Move {

    /**
     * Reduces the HP of the defending PokémonProfile given
     * the Move's values and decrements the Move's PP by 1.
     * @param accuracyResult    Usually a random number generated from 0 to 100.
     * @param criticalResult    Usually a random number generated from 0 to 16.
     * @param attacker          The PokémonProfile that would perform the Move.
     * @param defender          The defending PokémonProfile.
     */
    @Override
    public void execute(int accuracyResult,
                        int criticalResult,
                        PokémonProfile attacker,
                        PokémonProfile defender){
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
            if (defender.getCurrentHP() < PokémonProfile.MIN_HP) {
                defender.setCurrentHP(PokémonProfile.MIN_HP);
            }
        }
        setCurrentPP(mCurrentPP - 1);
    }

    /**
     * Returns false by default.
     * @param attacker  The PokémonProfile that would receive the recoil damage.
     * @return          False by default.
     */
    @Override
    public boolean executeRecoil(PokémonProfile attacker){
        return false;
    };

    /**
     * Returns the total attack stat value of the given PokémonProfile.
     * @param profile   The PokémonProfile where the total attack stat value is obtained.
     * @return          The total attack stat value of the given PokémonProfile.
     */
    public abstract int getAttackStat(PokémonProfile profile);

    /**
     * Returns the total defense stat value of the given PokémonProfile.
     * @param profile   The PokémonProfile where the total defense stat value is obtained.
     * @return          The total defense stat value of the given PokémonProfile.
     */
    public abstract int getDefenseStat(PokémonProfile profile);
}
