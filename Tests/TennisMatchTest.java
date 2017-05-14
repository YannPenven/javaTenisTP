import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Yann on 14/05/2017.
 */
public class TennisMatchTest{

    private MatchScore tennisMatch;
    private List<Player> playerList;

    public TennisMatchTest(){
        playerList = new ArrayList<>();
        playerList.add(new Player("player1"));
        playerList.add(new Player("player2"));
        tennisMatch = new TennisMatch(playerList.get(0),playerList.get(1), TennisMatch.MatchType.BEST_OF_THREE, false);
    }

    @Test
    public void updateWithPointWonBy() throws Exception {
        tennisMatch.updateWithPointWonBy(playerList.get(0));
        assertEquals("15",tennisMatch.pointsForPlayer(playerList.get(0)));
        assertEquals("0",tennisMatch.pointsForPlayer(playerList.get(1)));
    }

    @Test
    public void pointsForPlayer() throws Exception {
        tennisMatch.updateWithPointWonBy(playerList.get(0));
        assertEquals("15",tennisMatch.pointsForPlayer(playerList.get(0)));
    }

    @Test
    public void currentSetNumber() throws Exception {
        for(int i = 1; i < TennisMatch.MatchType.BEST_OF_THREE.numberOfSetsToWin(); i++){
            assertEquals(i,tennisMatch.currentSetNumber());
            for(int j = 0; j < TennisGame.TennisPoint.values().length * TennisSet.MIN_GAME_TO_PLAY; j++){
                tennisMatch.updateWithPointWonBy(playerList.get(0));
            }
        }
    }

    @Test
    public void gamesInCurrentSetForPlayer() throws Exception {
        int numberOfGameWin = 0;
        int numberOfPointToWinAGame = TennisGame.TennisPoint.values().length;
        for(int j = 0; j < TennisGame.TennisPoint.values().length * TennisSet.MIN_GAME_TO_PLAY; j++){
            if(j%numberOfPointToWinAGame == numberOfPointToWinAGame - 1){
                numberOfGameWin++;
            }
            assertEquals(numberOfGameWin, tennisMatch.gamesInCurrentSetForPlayer(playerList.get(0)));
            tennisMatch.updateWithPointWonBy(playerList.get(0));
        }
    }

    @Test
    public void gamesIntSetForPlayer() throws Exception {
        int numberOfGameWin = 0;
        int numberOfPointToWinAGame = TennisGame.TennisPoint.values().length;
        for(int j = 0; j < TennisGame.TennisPoint.values().length * TennisSet.MIN_GAME_TO_PLAY; j++){
            if(j%numberOfPointToWinAGame == numberOfPointToWinAGame - 1){
                numberOfGameWin++;
            }
            assertEquals(numberOfGameWin, tennisMatch.gamesIntSetForPlayer(0,playerList.get(0)));
            tennisMatch.updateWithPointWonBy(playerList.get(0));
        }
    }

    @Test
    public void isFinished() throws Exception {
        for(int i = 1; i < TennisMatch.MatchType.BEST_OF_THREE.numberOfSetsToWin(); i++){
            assertEquals(false,tennisMatch.isFinished());
            for(int j = 0; j < TennisGame.TennisPoint.values().length * TennisSet.MIN_GAME_TO_PLAY; j++){
                tennisMatch.updateWithPointWonBy(playerList.get(0));
            }
        }
        assertEquals(true,tennisMatch.isFinished());
    }

    @Test
    public void getWinner() throws Exception {
    }

}