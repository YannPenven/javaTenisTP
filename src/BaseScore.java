/**
 * Created by lp on 04/05/2017.
 */
public interface BaseScore {
    String getPlayerScore(String playerName);
    void playerHasScore(String playerName);
    boolean isFinished();
}
