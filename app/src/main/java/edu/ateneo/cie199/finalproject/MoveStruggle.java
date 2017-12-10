package edu.ateneo.cie199.finalproject;

import static java.lang.Math.floor;

/**
 * Created by John on 11/23/2017.
 * A physical move that is used when all of the PokémonProfile's moves have 0 PP.
 */

public class MoveStruggle extends MovePhysical {

    /**
     * Creates the Move struggle with its given values.
     * @param battle    The battle where the struggle move will be use.
     */
    public MoveStruggle(Battle battle){
        this.mName = "Struggle";
        this.mType = battle.getTypeChart().get(Type.NORMAL);
        this.mMaxPP = 9999;
        this.mCurrentPP = 9999;
        this.mPower = 50;
        this.mAccuracy = 200;
    }

    /**
     * Damages the defending PokémonProfile.
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

    /**
     * Damages the attacking PokémonProfile a quarter of its HP.
     * @param attacker  The PokémonProfile that would receive the recoil damage.
     * @return          True by default.
     */
    @Override
    public boolean executeRecoil(PokémonProfile attacker){
        attacker.setCurrentHP(attacker.getCurrentHP() - attacker.getHP()/4);
        if (attacker.getCurrentHP() < PokémonProfile.MIN_HP) {
            attacker.setCurrentHP(PokémonProfile.MIN_HP);
        }
        return true;
    }
}
