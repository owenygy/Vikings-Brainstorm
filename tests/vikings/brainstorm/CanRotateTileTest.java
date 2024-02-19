package vikings.brainstorm;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CanRotateTileTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String boardString, int position, boolean expected) {
        boolean out = Vikings.canRotateTile(boardString, position);
        assertEquals("Expected " + expected + " for position: " + position +
                ", and boardString " + boardString + ", but got " + out + ".", expected, out);
    }

    @Test
    public void testRotating() {
        testTrivialFalse();
        for (int i = 0; i < Utilities.rotatingTiles.length; i++) {
            for (int j = 0; j < Utilities.rotatingTiles[i].length; j++) {
                test(Objective.OBJECTIVES[i + 45].getInitialState(), Utilities.rotatingTiles[i][j], true);
            }
        }
    }

    @Test
    public void testNonRotating() {
        testTrivialTrue();
        for (int i = 0; i < Utilities.nonRotatingTiles.length; i++) {
            for (int j = 0; j < Utilities.nonRotatingTiles[i].length; j++) {
                test(Objective.OBJECTIVES[i + 45].getInitialState(), Utilities.nonRotatingTiles[i][j], false);
            }

        }
    }

    private void testTrivialTrue() {
        test(Objective.OBJECTIVES[0].getInitialState(), 8, true);
    }

    private void testTrivialFalse() {
        test(Objective.OBJECTIVES[1].getInitialState(), 0, false);
    }
}
