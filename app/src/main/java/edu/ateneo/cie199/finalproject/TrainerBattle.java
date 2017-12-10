package edu.ateneo.cie199.finalproject;

/**
 * Created by John on 11/30/2017.
 */

public class TrainerBattle extends Battle {

    private Trainer mTrainer = new Trainer();

    public TrainerBattle(PokemonGoApp app, DisplayInfoSetBuddy mBuddyInfo, DisplayInfoSet mEnemyInfo){
        this.mBuddyInfo = mBuddyInfo;
        this.mEnemyInfo = mEnemyInfo;

        this.mBuddy = app.getPlayer().getBuddy();
        this.mPlayer = app.getPlayer();
        this.mTypeChart = app.getAllTypes();

        //TODO CHANGE WHEN THERE IS TIME
        this.mTrainer = app.getTrainer(app.getCurrentGoal().getTitle()).generateTrainer();

        for(int index = 0; index < mTrainer.getTier()/2; index ++){
            this.mTrainer.getPokemons().add(new PokémonProfile(app.getSpawnCount(), app.getAllPokemons().get(app.getIntegerRNG(app.getAllPokemons().size())), PokemonGoApp.getIntegerRNG(app.getPlayer().getAverageLevel()) + 1));
            this.mTrainer.getPokemons().get(index).getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
            this.mTrainer.getPokemons().get(index).getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
            this.mTrainer.getPokemons().get(index).getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
            this.mTrainer.getPokemons().get(index).getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        }

        PokémonProfile fav1 = new PokémonProfile(app.getSpawnCount(), this.mTrainer.getFavoritePokemon1(), PokemonGoApp.getIntegerRNG(app.getPlayer().getAverageLevel()) + 1);
        fav1.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        fav1.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        fav1.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        fav1.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());

        this.mTrainer.getPokemons().add(fav1);

        PokémonProfile fav2 = new PokémonProfile(app.getSpawnCount(), this.mTrainer.getFavoritePokemon2(), PokemonGoApp.getIntegerRNG(app.getPlayer().getAverageLevel()) + 1);
        fav2.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        fav2.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        fav2.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());
        fav2.getMoves().add(app.getAllMoves().get(app.getIntegerRNG(app.getAllMoves().size())).generateCopy());

        this.mTrainer.getPokemons().add(fav2);

        this.mEnemy = this.mTrainer.getBuddy();
        this.mEnemyInfo.getImage().setBackgroundResource(this.mTrainer.getImageMain());
        addMessage(new Message(this.mTrainer.getTitle() + " " + this.mTrainer.getName() + " wants to battle!"));
        addMessage(new Message(this.mTrainer.getName() + ": " + this.mTrainer.getIntro()));
        addMessage(new MessageUpdatePokemon(this.mTrainer.getName() + " has sent out " + this.mEnemy.getNickname() + "!", this.mEnemyInfo, this.mEnemy));
        addMessage(new MessageUpdatePokemon("Go " + this.mBuddy.getNickname() + "!", this.mBuddyInfo, this.mBuddy));

        this.mBuddyInfo.getImage().setBackgroundResource(mPlayer.getGender().getBackImage());
    }

    public Trainer getTrainer() {
        return mTrainer;
    }
    public void setTrainer(Trainer mTrainer) {
        this.mTrainer = mTrainer;
    }

    @Override
    public boolean isRanAway() {
        return false;
    }

    @Override
    public boolean isFinished(){
        return (getTrainer().isDefeated()||getPlayer().isDefeated()||isRanAway());
    }

    @Override
    public void enemyHasFainted(){
        //TODO ADD MONEY REWARD IF TRAINER
        addMessage(new Message(mEnemy.getNickname() + Message.MESSAGE_FAINTED));
        addMessage(new MessageUpdateExp(mBuddy.getNickname() + " gained " + mEnemy.getLevel()* mBuddy.getLevel() * 10
                + Message.MESSAGE_EXP_GAINED, mBuddyInfo, mBuddy));
        mBuddy.setCurrentExp(mBuddy.getCurrentExp() + mEnemy.getLevel() * mBuddy.getLevel() * 10);
        if(mBuddy.getCurrentExp() >= mBuddy.getExpNeeded() && mBuddy.getLevel() < PokémonProfile.MAX_POKEMON_LEVEL){
            buddyLevelUp();
        }

        if(getTrainer().isDefeated()){
            addMessage(new MessageUpdateTrainer(this.mTrainer.getName() + ": " + this.mTrainer.getLose(), this));
        }
        this.mBattleState = mBattleState.standbyState();
    }

    @Override
    public void buddyHasFainted(){
        if(mPlayer.isDefeated()){
            addMessage(new MessageUpdateTrainer(this.mTrainer.getName() + ": " + this.mTrainer.getWin(), this));
            addMessage(new Message(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS1));
            addMessage(new Message(getPlayer().getName() + Message.MESSAGE_PLAYER_LOSS2));
        }
        this.mBattleState = mBattleState.standbyState();
    }
}
