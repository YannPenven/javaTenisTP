import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by lp on 04/05/2017.
 */
class PlayerTest {
    @Test
    void getName() {
        String name = "Patrick";
        Player player = new Player(name);
        assertEquals(name,player.getName(),"Name not equal to expected value");
    }

}