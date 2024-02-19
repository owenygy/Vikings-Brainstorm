package vikings.brainstorm;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.assertEquals;

public class IsBoardStringValidTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String boardString, boolean expected) {
        boolean out = Vikings.isBoardStringValid(boardString);
        assertEquals("expected " + expected + " for boardString: " + boardString +
                ", but got " + out, expected, out);
    }

    @Test
    public void testValid() {
        testTrivial();
        for (int i = 0; i < Objective.OBJECTIVES.length; i++) {
            String boardString = Objective.OBJECTIVES[i].getInitialState();
            test(boardString, true);
        }
    }

    @Test
    public void testOverlappingTiles() {
        testTrivial();
        for (int i = 0; i < Utilities.overlappingTiles.length; i++) {
            String boardString = Utilities.overlappingTiles[i].getInitialState();
            test(boardString, false);
        }
    }

    @Test
    public void testOverlappingBoats() {
        testTrivial();
        for (int i = 0; i < Utilities.overlappingBoats.length; i++) {
            test(Utilities.overlappingBoats[i], false);
        }
    }

    private void testTrivial() {
        test(Objective.OBJECTIVES[0].getInitialState().replace('O', 'Q'), false);
        test(Objective.OBJECTIVES[0].getInitialState(), true);
    }
}
