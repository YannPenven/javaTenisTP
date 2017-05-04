import java.util.ArrayList;

/**
 * Created by lp on 04/05/2017.
 */
public class TennisMatch {
    private Player[] players;
    private MatchType matchType;
    private Boolean tieBreack;

    public static final int MAX_NUMBER_OF_PLAYER = 2;

    public enum MatchType{
        BEST_OF_THREE,
        BEST_OF_FIVE
    }

    public TennisMatch(Player player1,Player player2, MatchType matchType, Boolean tieBreack) {
        this(init(player1,player2), matchType, tieBreack);
    }

    private static Player[] init(Player player1, Player player2){
        Player[] player = new Player[TennisMatch.MAX_NUMBER_OF_PLAYER];
        player[0] = player1;
        player[1] = player2;
        return player;
    }

    public TennisMatch(Player[] players, MatchType matchType, Boolean tieBreack){
        if(players.length != TennisMatch.MAX_NUMBER_OF_PLAYER){
            throw new IllegalArgumentException("the number number of player must be equal to" + TennisMatch.MAX_NUMBER_OF_PLAYER);
        }
        this.players = players;
        this.matchType = matchType;
        this.tieBreack = tieBreack;

    }

    public void updateWithPointWonBy(Player player){

    }

    public String pointsForPlayer(Player player){
        //Score actuel Game score + TennisSet score
    }

    public int currentSetNumber(){

    }

    public int gamesInCurrentSetForPlayer(Player player){

    }

    public int gamesIntSetForPlayer(int setNumber, Player player){

    }

    public boolean isFinished(){

    }
}
