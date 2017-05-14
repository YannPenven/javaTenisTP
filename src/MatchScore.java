/**
 * Created by Yann on 13/05/2017.
 */
public interface MatchScore extends BaseScore {
    String pointsForPlayer(Player playerName);
    void updateWithPointWonBy(Player playerName);
}
