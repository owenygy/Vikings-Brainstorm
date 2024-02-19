package vikings.brainstorm;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.assertEquals;

public class DoTilesInterlockTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String boardString, int position1, int position2, boolean expected) {
        boolean out = Vikings.doTilesInterlock(boardString, position1, position2);
        assertEquals("Expected " + expected + " for boardString " + boardString +
                ", position 1: " + position1 + " and position2: " + position2 + ", but got "
                + out + ".", expected, out);
    }

    @Test
    public void testInterlockLeft() {
        Objective objective = Objective.OBJECTIVES[0];
        for (int i = 0; i < 3; i++) {
            int position1 = Utilities.interlockingTiles[i][0];
            int position2 = Utilities.interlockingTiles[i][1];
            test(objective.getInitialState(), position1, position2, true);
        }
        for (int i = 0; i < 6; i++) {
            objective = Objective.OBJECTIVES[i];
            int position1 = Utilities.nonInterlockingTiles[i][0];
            int position2 = Utilities.nonInterlockingTiles[i][1];
            test(objective.getInitialState(), position1, position2, false);
        }
    }

    @Test
    public void testInterlockRight() {
        Objective objective = Objective.OBJECTIVES[1];
        for (int i = 0; i < 3; i++) {
            int position1 = Utilities.interlockingTiles[i + 3][0];
            int position2 = Utilities.interlockingTiles[i + 3][1];
            test(objective.getInitialState(), position1, position2, true);
        }
        for (int i = 0; i < 4; i++) {
            objective = Objective.OBJECTIVES[i + 6];
            int position1 = Utilities.nonInterlockingTiles[i + 6][0];
            int position2 = Utilities.nonInterlockingTiles[i + 6][1];
            test(objective.getInitialState(), position1, position2, false);
        }
    }

    @Test
    public void testInterlockAbove() {
        Objective objective = Objective.OBJECTIVES[2];
        for (int i = 0; i < 5; i++) {
            int position1 = Utilities.interlockingTiles[i + 6][0];
            int position2 = Utilities.interlockingTiles[i + 6][1];
            test(objective.getInitialState(), position1, position2, true);
        }
        for (int i = 0; i < 5; i++) {
            objective = Objective.OBJECTIVES[i + 10];
            int position1 = Utilities.nonInterlockingTiles[i + 10][0];
            int position2 = Utilities.nonInterlockingTiles[i + 10][1];
            test(objective.getInitialState(), position1, position2, false);
        }
    }

    @Test
    public void testInterlockBelow() {
        Objective objective = Objective.OBJECTIVES[5];
        for (int i = 0; i < 3; i++) {
            int position1 = Utilities.interlockingTiles[i + 11][0];
            int position2 = Utilities.interlockingTiles[i + 11][1];
            test(objective.getInitialState(), position1, position2, true);
        }
        for (int i = 0; i < 5; i++) {
            objective = Objective.OBJECTIVES[i + 15];
            int position1 = Utilities.nonInterlockingTiles[i + 15][0];
            int position2 = Utilities.nonInterlockingTiles[i + 15][1];
            test(objective.getInitialState(), position1, position2, false);
        }
    }
}
