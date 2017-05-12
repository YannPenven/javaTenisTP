import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lp on 04/05/2017.
 */
public class TennisGame implements BaseScore{
    public enum Score {
        _0("0"),
        _15("15"),
        _30("30"),
        _40("40"),
        _A("A"),
        _V("V");

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

    public TennisGame(ArrayList<String> players){
        if(players.size() != TennisMatch.MAX_NUMBER_OF_PLAYER){
            throw new IllegalArgumentException("the number number of player must be equal to " + TennisMatch.MAX_NUMBER_OF_PLAYER);
        }
        PlayersScore = new HashMap<>();
        for (String player: players){
            PlayersScore.put(player,Score._0);
        }
    }

    @Override
    public String getPlayerScore(String playerName){
        Score score = PlayersScore.get(playerName);
        if(score == null){
            throw new IllegalArgumentException("Player does not participate to the current TennisGame");
        }
        return score.value;
    }

    @Override
    public void playerHasScore(String playerName){
        Score score = PlayersScore.get(playerName);
        if(score == null){
            throw new IllegalArgumentException("Player does not participate to the current TennisGame");
        }else if(PlayersScore.containsKey(Score._V)){
            throw new IllegalStateException("Can't increase score when one player has already win the game");
        }
        PlayersScore.put(playerName,score.next());
    }

    @Override
    public boolean isFinished() {
        if(PlayersScore.containsKey(Score._V)){
            return true;
        }
        return false;
    }

    @Override
    public String getWinner() {
        return PlayersScore.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), Score._V))
                .map(HashMap.Entry::getKey)
                .collect(Collectors.toSet()).toString();
    }
}
