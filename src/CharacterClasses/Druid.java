
public class Druid extends CharacterClass{

    private static final int FalconReach = 2;
    private static final String className = "Druid";
    private boolean bear=false;
    //0 means its a person attack, 1 is a tile attack, and 2 is no attack or attack everyone(aka nothing passed in)
    //falcon is index 3
    private static int[] _selectingTile={0,2,1,1};
    int attackCount = 2;


    Druid(){
        //reach is bear reach
        super(className,0,_selectingTile,2);
    }

    ///even indexes are names, odd indexes are decriptions
    String[] getAttackName() {
        String[] attacks = {"Animal Strike", "1-4 damage to two enemies.", "Falcon Dive", "Choose a tile and do 1 damage to one random person on that tile."};
        return attacks;
    }

    String[] getAbilityName() {
        String[] abilities = {"Spike Growth", "Any movement on a selected tile will inflict 2 damage."};
        return abilities;
    }

    String[] getSuperMoveName() {
        String[] superMove = {"Dragon Morph", "Move to a selected tile and deal 5-20 damage to every place on that tile."};
        return superMove;
    }
////

    //bear attack
    void Attack(Player ptr) {
        DealDamage(Dice.RollDie(1,4,0),ptr);
        attackCount--;
    }
    //falcon attack
    void Attack(int tile) {
        Player.getCurrentPlayer().MoveAnywhere(tile,false);
        Player[] ptr = Tile.getTile(tile).getPlayerPtrs();
        for (int i = 0; i < ptr.length; i++){
            if(Player.getCurrentPlayer()!=ptr[i])
                DealDamage(2,ptr[i]);
        }
    }



    void Ability() {
        bear=!bear;
        if(bear)
            attackCount = maxAttackCount;
        else{
            attackCount = 0;
        }
        if(!Tile.getTile(Player.getCurrentPlayer().currTile()).isFull()) {
            SpikeGrowth ptr = new SpikeGrowth(Player.getCurrentPlayer().currTile(), 1, Player.getCurrentPlayer());
        }
    }

    void SuperMove() {
        int playerTileMinus=0;
        for(int play=0;play<Tile.getTile(0).getPlayerPtrs().length;play++) {
            if(Tile.getTile(0).getPlayerPtrs()[play].equals(Player.getCurrentPlayer()))
                playerTileMinus=-1;
        }
        int maxPlayerTile=Tile.getTile(0).getPlayerPtrs().length+playerTileMinus;
        playerTileMinus=0;
        for(int i=1;i<7;i++){
            for(int play=0;play<Tile.getTile(i).getPlayerPtrs().length;play++) {
                if(Tile.getTile(i).getPlayerPtrs()[play].equals(Player.getCurrentPlayer()))
                    playerTileMinus=-1;
            }
            if((Tile.getTile(i).getPlayerPtrs().length+playerTileMinus)>Tile.getTile(maxPlayerTile).getPlayerPtrs().length) {
                maxPlayerTile = i;
            }
            playerTileMinus=0;
        }
        Player.getCurrentPlayer().MoveAnywhere(maxPlayerTile,false);
        Player[] ptr = Tile.getTile(maxPlayerTile).getPlayerPtrs();
        int damage=Dice.RollDie(3,6,2);
        for (int i = 0; i < ptr.length; i++){
            if(Player.getCurrentPlayer()!=ptr[i])
                DealDamage(damage,ptr[i]);
        }
        SpikeGrowth spikes = new SpikeGrowth(Player.getCurrentPlayer().currTile(),1,Player.getCurrentPlayer());
    }
    void EndTurn(){

    }
    //overiding
    public int getReach(){
        if(bear)
            return reach;
        else 
            return FalconReach;
    }
    boolean isBear(){
        return bear;
    }
    public int getAttackCount(){
        return attackCount;
    }
    public void setAttackCount(int _setAttackCount){
        attackCount = _setAttackCount;
    }
    public void resetAttackCount(){
        if(bear)
            attackCount = maxAttackCount;
        else{
            attackCount = 0;
        }
    }
////

    //////////////ACTUAL IMPLEMENTATIONS HERE
    ////all these are shifted up one because they are the damage calculators, pass an int into

////each of these to get the added modifier complete
    // d2 (coin flip animation) and get random number 1 or 2
    // d4 (dice animation) and get random number 1 or 2 or 3 or 4
    // d6 (dice animation) and get random number 1 or 2 or 3 or 4 or 5 or 6
////

}


