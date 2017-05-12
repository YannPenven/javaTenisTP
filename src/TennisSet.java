import java.util.ArrayList;
import java.util.HashMap;

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
        this.PlayersScore.replaceAll((k,v) -> new SetScore());
        for (TennisGame tennisgame: tennisGames) {
            if(tennisgame.isFinished()){
                this.PlayersScore.get(tennisgame.getWinner()).gameWin++;
            }
        }
    }

    private boolean minNumberOfGameReach(){
        for(HashMap.Entry<String,SetScore> k: this.PlayersScore.entrySet()){
            if(k.getValue().gameWin >= MIN_GAME_TO_PLAY){
                return true;
            }
        }
        return false;
    }


    @Override
    public String getPlayerScore(String playerName) {
        return tennisGames.get(tennisGames.size()-1).getPlayerScore(playerName);
    }

    @Override
    public void playerHasScore(String playerName) {
        calculatePlayerScore();
        if (minNumberOfGameReach()){

        }
        //si un il y au moin 6 game
            //verifier si le nombre de game gagner par les deux joueurs est supérieur à 2 par rapport à l'autre joueur
        if(this.tennisGames.get(tennisGames.size()-1).isFinished()){
            this.tennisGames.add(new TennisGame(new ArrayList<String>(this.PlayersScore.keySet())));
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public String getWinner() {
        return null;
    }

    private class SetScore{
        private int gameWin;

        public SetScore(){
            gameWin = 0;
        }
    }
}
