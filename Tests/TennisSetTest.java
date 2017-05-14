import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Yann on 14/05/2017.
 */
public class TennisSetTest extends TennisTest{
    private SetScore tennisSet;
    private int numberOfGameToPlayToWinASet = (TennisSet.MIN_GAME_TO_PLAY * TennisGame.TennisPoint.values().length) - 1;

    public TennisSetTest(){
        super();
        tennisSet = new TennisSet(playerList);
    }

    @Test
    public void isFinished() throws Exception {
        for (int i = 0; i < numberOfGameToPlayToWinASet; i++) {
            assertEquals(false,tennisSet.isFinished());
            tennisSet.updateWithPointWonBy(player1);
        }
        assertEquals(true,tennisSet.isFinished());
    }

    @Test
    public void getWinner() throws Exception {
        for (int i = 0; i < numberOfGameToPlayToWinASet; i++) {
            tennisSet.updateWithPointWonBy(player1);
        }
        assertEquals(player1,tennisSet.getWinner());
    }

    @Test
    public void pointsForPlayer() throws Exception {
        tennisSet.updateWithPointWonBy(player1);
        assertEquals("15",tennisSet.pointsForPlayer(player1));
    }

    @Test
    public void updateWithPointWonBy() throws Exception {
        tennisSet.updateWithPointWonBy(player1);
        tennisSet.updateWithPointWonBy(player2);
        assertEquals("15",tennisSet.pointsForPlayer(player2));
        assertEquals("15",tennisSet.pointsForPlayer(player1));
    }

    @Test
    public void gamesInSetForPlayer() throws Exception {
        for (int i = 0; i < numberOfGameToPlayToWinASet/2; i++) {
            tennisSet.updateWithPointWonBy(player1);
        }
        assertEquals(3, tennisSet.gamesInSetForPlayer(player1));
        assertEquals(0, tennisSet.gamesInSetForPlayer(player2));
    }

}