import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by lp on 04/05/2017.
 */
public class TennisSet implements SetScore{
    private ArrayList<SubScore> tennisGames;
    //private ArrayList<String> playersName;//TODO: replace with ArrayList
    private HashMap<String,SetScore> PlayersScore;

    private final int MIN_GAME_TO_PLAY = 6;
    private final int MIN_GAME_DIF_TO_WIN = 2;

    public TennisSet(List<String> playersName) {
        if(playersName.size() != TennisMatch.MAX_NUMBER_OF_PLAYER){
            throw new IllegalArgumentException("the number of player must be equal to " + TennisMatch.MAX_NUMBER_OF_PLAYER);
        }
        this.PlayersScore = new HashMap<>();
        this.tennisGames = new ArrayList<>();

        for (String player : playersName) {
            this.PlayersScore.put(player,new SetScore());
        }
        this.tennisGames.add(new TennisGame(new ArrayList<String>(this.PlayersScore.keySet())));

        calculatePlayerScore();
    }

    private void calculatePlayerScore(){
        this.PlayersScore.replaceAll((k,v) -> new SetScore() );
        for (SubScore tennisgame: tennisGames) {
            if(tennisgame.isFinished()){
                this.PlayersScore.get(tennisgame.getWinner()).gameWin++;
            }
        }
    }

    private int getMaxGameWin() {
        return this.PlayersScore.values()
                    .stream()
                    .mapToInt(e -> e.gameWin)
                    .max()
                    .orElse(0);
    }

    private int getMinGameWin(){
        return this.PlayersScore.values()
                .stream()
                .mapToInt(e -> e.gameWin)
                .min()
                .orElse(0);
    }

    @Override
    public boolean isFinished() {
        int maxGameWin = getMaxGameWin();
        if (maxGameWin > MIN_GAME_TO_PLAY){
            if(getMaxGameWin() - getMinGameWin() >= MIN_GAME_DIF_TO_WIN){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getWinner() throws IllegalStateException{
        //check if game is finished
        if(!isFinished()){
            throw new IllegalStateException("Can't have a winner if Set is not finished");
        }
        return this.PlayersScore.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().gameWin))
                .map(HashMap.Entry::getKey)
                .findFirst()
                .orElse("");
    }

    @Override
    public String pointsForPlayer(String playerName) {
        return this.tennisGames.get(this.tennisGames.size() -1).pointsForPlayer(playerName);
    }

    @Override
    public void updateWithPointWonBy(String playerName) {
        SetScore setScore = this.PlayersScore.get(playerName);
        if(setScore == null){
            throw new IllegalArgumentException("Player does not participate to the current TennisGame");
        }
        if(isFinished()){
            throw new IllegalStateException("Can't increase play another game when one player has already win the set");
        }
        SubScore tennisGame = this.tennisGames.get(tennisGames.size()-1);
        if(tennisGame.isFinished()){
            this.tennisGames.add(new TennisGame(new ArrayList<String>(this.PlayersScore.keySet())));
        }else {
            tennisGame.updateWithPointWonBy(playerName);
        }
        calculatePlayerScore();
    }

    @Override
    public int gamesInSetForPlayer(String playerName) {
        return this.PlayersScore.get(playerName).gameWin;
    }

    private class SetScore{
        private int gameWin;

        public SetScore(){
            gameWin = 0;
        }

        public int getGameWin() {
            return gameWin;
        }
    }
}
