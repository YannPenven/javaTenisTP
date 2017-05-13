import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by lp on 04/05/2017.
 */
public class TennisSet implements BaseScore{
    private ArrayList<TennisGame> tennisGames;
    private String[] playersName;
    private HashMap<String,SetScore> PlayersScore;

    private final int MIN_GAME_TO_PLAY = 6;
    private final int MIN_GAME_DIF_TO_WIN = 2;

    public TennisSet(String[] playersName) {
        if(playersName.length != TennisMatch.MAX_NUMBER_OF_PLAYER){
            throw new IllegalArgumentException("the number number of player must be equal to " + TennisMatch.MAX_NUMBER_OF_PLAYER);
        }
        this.playersName = playersName;
        this.PlayersScore = new HashMap<>();
        this.tennisGames = new ArrayList<>();

        for (String player : playersName) {
            this.PlayersScore.put(player,new SetScore());
        }
        this.tennisGames.add(new TennisGame(new ArrayList<String>(this.PlayersScore.keySet())));
    }

    private void calculatePlayerScore(){
        this.PlayersScore.replaceAll((k,v) -> new SetScore() );
        for (TennisGame tennisgame: tennisGames) {
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
                    .getAsInt();
    }

    private int getMinGameWin(){
        return this.PlayersScore.values()
                .stream()
                .mapToInt(e -> e.gameWin)
                .min()
                .getAsInt();
    }


    @Override
    public String getPlayerScore(String playerName) {
        return Integer.toString(this.PlayersScore.get(playerName).gameWin);
    }

    @Override
    public void playerHasScore(String playerName) {
        SetScore setScore = this.PlayersScore.get(playerName);
        if(setScore == null){
            throw new IllegalArgumentException("Player does not participate to the current TennisGame");
        }
        if(isFinished()){
            throw new IllegalStateException("Can't increase play another game when one player has already win the set");
        }
        TennisGame tennisGame = this.tennisGames.get(tennisGames.size()-1);
        if(tennisGame.isFinished()){
            this.tennisGames.add(new TennisGame(new ArrayList<String>(this.PlayersScore.keySet())));
        }else {
            tennisGame.playerHasScore(playerName);
        }
    }

    @Override
    public boolean isFinished() {
        //check if score has change since last time, boolean that get to true if playerhasscore is call
        calculatePlayerScore();
        int maxGameWin = getMaxGameWin();
        if (maxGameWin > MIN_GAME_TO_PLAY){
            if(getMaxGameWin() - getMinGameWin() >= MIN_GAME_DIF_TO_WIN){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getWinner() {
        //check if game is finished
        return this.PlayersScore.entrySet()
                .stream()
                .sorted((entry1,entry2) -> Integer.compare(entry1.getValue().gameWin,entry2.getValue().gameWin))
                .map(HashMap.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> {throw new IllegalStateException("Can't have a winner if game is not finished");})
                .toString();
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
