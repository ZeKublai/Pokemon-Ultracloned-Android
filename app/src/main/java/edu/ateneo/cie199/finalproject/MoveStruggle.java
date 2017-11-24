package edu.ateneo.cie199.finalproject;

import static java.lang.Math.floor;

/**
 * Created by John on 11/23/2017.
 */

public class MoveStruggle extends MovePhysical {

    public MoveStruggle(Battle battle){
        this.mName = "Struggle";
        this.mType = battle.getTypeChart().get(Type.NORMAL);
        this.mMaxPP = 9999;
        this.mCurrentPP = 9999;
        this.mPower = 50;
        this.mAccuracy = 200;
    }

    @Override
    public void execute(int accuracyResult,
                        int criticalResult,
                        PokemonProfile attacker,
                        PokemonProfile defender){
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

    @Override
    public boolean executeRecoil(PokemonProfile attacker){
        attacker.setCurrentHP(attacker.getCurrentHP() - attacker.getHP()/4);
        if (attacker.getCurrentHP() < PokemonProfile.MIN_HP) {
            attacker.setCurrentHP(PokemonProfile.MIN_HP);
        }
        return true;
    }
}
