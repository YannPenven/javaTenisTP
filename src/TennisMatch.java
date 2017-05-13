import java.util.ArrayList;

/**
 * Created by lp on 04/05/2017.
 */
public class TennisMatch {
    private Player[] players;
    private MatchType matchType;
    private Boolean tieBreak;
    private ArrayList<TennisSet> tennisSets;

    public static final int MAX_NUMBER_OF_PLAYER = 2;

    public enum MatchType{
        BEST_OF_THREE(2), BEST_OF_FIVE(3);

        private final int numberOfSetsToWin;

        private MatchType(int numberOfSetsToWin) {
            this.numberOfSetsToWin = numberOfSetsToWin;
        }
        public int numberOfSetsToWin() {
            return numberOfSetsToWin;
        }
        public int maxNumberOfSets(){
            return numberOfSetsToWin*2 - 1;
        }
    }

    public TennisMatch(Player player1,Player player2, MatchType matchType, Boolean tieBreak) {
        this(init(player1,player2), matchType, tieBreak);
    }

    private static Player[] init(Player player1, Player player2){
        Player[] player = new Player[TennisMatch.MAX_NUMBER_OF_PLAYER];
        player[0] = player1;
        player[1] = player2;
        return player;
    }

    public TennisMatch(Player[] players, MatchType matchType, Boolean tieBreak){
        if(players.length != TennisMatch.MAX_NUMBER_OF_PLAYER){
            throw new IllegalArgumentException("the number number of player must be equal to" + TennisMatch.MAX_NUMBER_OF_PLAYER);
        }
        this.players = players;
        this.matchType = matchType;
        this.tieBreak = tieBreak;

    }

    public void updateWithPointWonBy(Player player){
        tennisSets.get(tennisSets.size()-1).playerHasScore(player.getName());
    }

    public String pointsForPlayer(Player player){
        int point = 0;
        for (TennisSet tennisSet: this.tennisSets) {
            if(tennisSet.isFinished() && tennisSet.getWinner().equals(player.getName())){
                point++;
            }
        }
        return player.getName() + ": has win " + point + " set and " + this.tennisSets.get(this.tennisSets.size() - 1).getPlayerScore(player.getName()) + "game in the current Set";
    }

    public int currentSetNumber(){
        return this.tennisSets.size();
    }

    public int gamesInCurrentSetForPlayer(Player player){

    }

    public int gamesIntSetForPlayer(int setNumber, Player player){

    }

    public boolean isFinished(){

    }
}
