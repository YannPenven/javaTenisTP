import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Yann on 13/05/2017.
 */
public class TennisGameTest extends TennisTest{
    private GameScore tennisGame;

    public TennisGameTest(){
        super();
        tennisGame = new TennisGame(playerList);
    }

    @Test
    public void pointsForPlayer() throws Exception {
        tennisGame.updateWithPointWonBy(player1);
        assertEquals("15",tennisGame.pointsForPlayer(player1));
        assertEquals("0",tennisGame.pointsForPlayer(player2));

    }

    @Test
    public void updateWithPointWonBy() throws Exception {
        for(int i = 0; i < TennisGame.TennisPoint.values().length; i++){
            if(TennisGame.TennisPoint.values()[i] == TennisGame.TennisPoint._A){
                tennisGame.updateWithPointWonBy(player2);
                assertEquals(TennisGame.TennisPoint._40.getValue(),tennisGame.pointsForPlayer(player1));
            }
            tennisGame.updateWithPointWonBy(player1);
        }
    }

    @Test
    public void isFinished() throws Exception {
        for (TennisGame.TennisPoint point: TennisGame.TennisPoint.values()) {
            tennisGame.updateWithPointWonBy(player1);
            if(point == TennisGame.TennisPoint._A){
                assertEquals(true, tennisGame.isFinished());
            }else {
                assertEquals(false,tennisGame.isFinished());
            }

        }
    }

    @Test
    public void getWinner() throws Exception {
        for(int i = 0; i < TennisGame.TennisPoint.values().length -1; i++){
            tennisGame.updateWithPointWonBy(player1);
        }
        assertEquals(player1,tennisGame.getWinner());
    }

}