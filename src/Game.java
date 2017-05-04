import java.util.HashMap;

/**
 * Created by lp on 04/05/2017.
 */
public class Game {
    public enum Score {
        _0("0"),
        _15("15"),
        _30("30"),
        _40("40"),
        _A("A");

        private String value;
        private static  Score[] scores = values();

        Score(String i) {
            this.value = i;
        }

        public String getValue(){
            return this.value;
        }

        public Score next(){
            return scores[(this.ordinal()+1) % scores.length];
        }
    }

    private HashMap<String,Score> PlayersScore;

    public Game(Player[] players){
        if(players.length != TennisMatch.MAX_NUMBER_OF_PLAYER){
            throw new IllegalArgumentException("the number number of player must be equal to" + TennisMatch.MAX_NUMBER_OF_PLAYER);
        }
        PlayersScore = new HashMap<>();
        for (Player player: players){
            PlayersScore.put(player.getName(),Score._0);
        }
    }

    public String getPlayerScore(String playerName){
        return  PlayersScore.get(playerName).value;
    }

    public void playerHasScore(String playerName){
        Score score = PlayersScore.get(playerName);
        if(score == null){
            throw new IllegalArgumentException("Player does not participae to the current Game");
        }
        PlayersScore.put(playerName,score.next());
    }
}
