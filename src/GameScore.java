/**
 * Created by Yann on 14/05/2017.
 */
public interface GameScore extends BaseScore {
    String pointsForPlayer(String playerName);
    void updateWithPointWonBy(String playerName);
}
