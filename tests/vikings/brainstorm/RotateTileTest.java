package vikings.brainstorm;

import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RotateTileTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String boardString, int position, String expected) {
        String out = Vikings.rotateTile(boardString, position);
        assertEquals("Expected " + expected + " for boardString; " + boardString + ", position: " + position +
                ", but got: " + out, expected, out);
    }

    @Test
    public void testOneBoat() {
        test(Objective.OBJECTIVES[0].getInitialState(), 8, "N0O1N1N0O0O1N0N3N2Rq");
        test("O0O1O0N3N1N2N3N3N2Gp", 7, "O0O1O0N3N1N2N3N0N2Gt");
    }

    @Test
    public void testCompleteObjOne() {
        for (int i = 0; i < Utilities.completeObjOne.length - 1; i++) {
            String initial = Utilities.completeObjOne[i][0];
            String end = Utilities.completeObjOne[i + 1][0];
            int position = Character.getNumericValue(Utilities.completeObjOne[i][1].charAt(0));
            test(initial, position, end);
        }
    }

    @Test
    public void testOneMoveExpert() {
        for (int i = 0; i < Utilities.oneMoveExpert.length; i++) {
            String initial = Objective.OBJECTIVES[i + 30].getInitialState();
            String end = Utilities.oneMoveExpert[i][0];
            int position = Character.getNumericValue(Utilities.oneMoveExpert[i][1].charAt(0));
            test(initial, position, end);
        }
    }
}
