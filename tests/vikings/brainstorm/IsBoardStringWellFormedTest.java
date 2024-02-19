package vikings.brainstorm;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

public class IsBoardStringWellFormedTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String boardString, Boolean expected) {
        Boolean out = Vikings.isBoardStringWellFormed(boardString);
        assertTrue("expected " + expected + "for boardString: " + boardString +
                ", but got " + out, out == expected);
    }

    @Test
    public void testWellFormed() {
        testTrivial();
        for (int i = 0; i < Objective.OBJECTIVES.length; i++) {
            String boardString = Objective.OBJECTIVES[i].getInitialState();
            test(boardString, true);
        }
    }

    @Test
    public void testBadBoats() {
        testTrivial();
        for (int i = 0; i < Utilities.badBoats.length; i++) {
            test(Utilities.badBoats[i], false);
        }
    }

    @Test
    public void testNotWellFormed() {
        testTrivial();
        for (int i = 0; i < Utilities.notWellFormed.length; i++) {
            test(Utilities.notWellFormed[i], false);
        }
    }

    private void testTrivial() {
        test(Objective.OBJECTIVES[0].getInitialState(), true);
        test("XXXXXXXXXXXXXXXXXXXX", false);
    }
}
