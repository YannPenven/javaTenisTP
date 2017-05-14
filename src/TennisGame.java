import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by lp on 04/05/2017.
 */
public class TennisGame implements GameScore {
    public enum TennisPoint {
        _0("0"),
        _15("15"),
        _30("30"),
        _40("40"),
        _A("A"),
        _V("V");

        private String value;
        private static  TennisPoint[] tennisPoints = values();

        TennisPoint(String i) {
            this.value = i;
        }

        public String getValue(){
            return this.value;
        }

        public TennisPoint next(){
            return tennisPoints[(this.ordinal()+1) % tennisPoints.length];
        }

        public TennisPoint previous() {return  tennisPoints[(this.ordinal()-1) % tennisPoints.length];}
    }

    private HashMap<String, TennisPoint> PlayersScore;

    public TennisGame(List<String> playersName){
        if(playersName.size() != TennisMatch.MAX_NUMBER_OF_PLAYER){
            throw new IllegalArgumentException("the number number of player must be equal to " + TennisMatch.MAX_NUMBER_OF_PLAYER);
        }
        PlayersScore = new HashMap<>();
        for (String player: playersName){
            PlayersScore.put(player, TennisPoint._0);
        }
    }

    @Override
    public String pointsForPlayer(String playerName){
        TennisPoint tennisPoint = PlayersScore.get(playerName);
        if(tennisPoint == null){
            throw new IllegalArgumentException("Player does not participate to the current TennisGame");
        }
        return tennisPoint.value;
    }

    @Override
    public void updateWithPointWonBy(String playerName){
        TennisPoint tennisPoint = PlayersScore.get(playerName);
        if(tennisPoint == null){
            throw new IllegalArgumentException("Player does not participate to the current TennisGame");
        }else if(PlayersScore.containsKey(TennisPoint._V)){
            throw new IllegalStateException("Can't increase tennisPoint when one player has already win the game");
        }else if(tennisPoint != TennisPoint._A && PlayersScore.containsValue(TennisPoint._A)){
            PlayersScore.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), TennisPoint._A))
                    .findFirst()
                    .ifPresent((x) -> { PlayersScore.put(x.getKey(),x.getValue().previous());});
        }
        PlayersScore.put(playerName, tennisPoint.next());
    }

    @Override
    public boolean isFinished() {
        if(PlayersScore.containsValue(TennisPoint._V)){
            return true;
        }
        return false;
    }

    @Override
    public String getWinner() {
        if(!isFinished()){
            throw new IllegalStateException("Can't have a winner if Game is not finished");
        }
        return PlayersScore.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), TennisPoint._V))
                .map(HashMap.Entry::getKey)
                .findFirst()
                .orElse("");
    }
}
