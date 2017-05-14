
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by lp on 04/05/2017.
 */
public class PlayerTest {
    @Test
    void getName() {
        String name = "Patrick";
        Player player = new Player(name);
        assertEquals(name,player.getName(),"Name not equal to expected value");
    }

}