package vikings.brainstorm;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class LocationIsEqualTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(Location loc1, Location loc2, boolean expected) {
        boolean out = loc1.isEqual(loc2);
        assertEquals("Expected " + expected + " for location 1: " + loc1 +
                " and location 2: " + loc2 + ", but got "
                + out + ".", expected, out);
    }

    @Test
    public void testEqual() {
        test(new Location(1,1), new Location(0,0), false);
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                Location loc1 = new Location(x, y);
                Location loc2 = new Location(x, y);
                test(loc1, loc2, true);
            }
        }
    }

    @Test
    public void testNotEqual() {
        test(new Location (0,0), new Location(0,0), true);
        ArrayList<Location> locations = new ArrayList();
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                Location loc = new Location(x, y);
                locations.add(loc);
            }
        }
        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                if (i != j) {
                    test(locations.get(i), locations.get(j), false);
                }
            }
        }
    }
}
