import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by lp on 04/05/2017.
 */
public class TennisMatch implements BaseScore {
    private Player[] players;
    private MatchType matchType;
    private Boolean tieBreak;
    private List<SetScore> tennisSets;

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
        participateInTheMatch(player);
        if(isFinished()){
            throw new IllegalStateException("Can't keep playing when their already a winner");
        }
        if(this.tennisSets.get(tennisSets.size() -1 ).isFinished()){
            tennisSets.add(new TennisSet(Arrays.stream(this.players).map(Player::getName).collect(Collectors.<String>toList())));
        }else{
            tennisSets.get(tennisSets.size()-1).updateWithPointWonBy(player.getName());
        }
    }

    private void participateInTheMatch(Player player) {
        if(Arrays.stream(this.players).noneMatch(x -> x.equals(player))){
            throw new IllegalArgumentException("Player does not participate in the current TennisMatch");
        }
    }

    public String pointsForPlayer(Player player){
        participateInTheMatch(player);
        /*int point = 0;
        for (TennisSet tennisSet: this.tennisSets) {
            if(tennisSet.isFinished() && tennisSet.getWinner().equals(player.getName())){
                point++;
            }
        }
        return player.getName() + ": has win " + point + " set and " + this.tennisSets.get(this.tennisSets.size() - 1).pointsForPlayer(player.getName()) + "game in the current Set";*/
        return this.tennisSets.get(this.tennisSets.size() -1).pointsForPlayer(player.getName());
    }

    public int currentSetNumber(){
        return this.tennisSets.size();
    }

    public int gamesInCurrentSetForPlayer(Player player){
        return this.tennisSets.get(this.tennisSets.size() -1).gamesInSetForPlayer(player.getName());
    }

    public int gamesIntSetForPlayer(int setNumber, Player player){
        if(setNumber >= this.tennisSets.size() && setNumber < 0){
            throw new IllegalArgumentException("The set number can't be inf to 0 or bigger than the current number of set");
        }
        return this.tennisSets.get(setNumber).gamesInSetForPlayer(player.getName());
    }

    public boolean isFinished(){
        if(matchType.maxNumberOfSets() == this.tennisSets.size() && this.tennisSets.get(this.tennisSets.size() -1).isFinished()){
            return true;
        }
        return false;
    }

    @Override
    public String getWinner() {
        if(!isFinished()){
            throw new IllegalStateException("Can't have a winner if Match is not finished");
        }
        return this.tennisSets
                .stream()
                .collect(Collectors.groupingBy(SetScore::getWinner,Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingLong(Map.Entry::getValue))
                .map(HashMap.Entry::getKey)
                .findFirst()
                .orElse("");
    }
}
