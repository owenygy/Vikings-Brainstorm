package vikings.brainstorm;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

public class DoTilesOverlapTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void test(String boardString, int position1, int position2, boolean expected) {
        boolean out = Vikings.doTilesOverlap(boardString, position1, position2);
        assertTrue("Expected " + expected + " for boardString " + boardString +
                ", position 1: " + position1 + " and position2: " + position2 + ", but got "
                + out + ".", out == expected);
    }

    @Test
    public void testNotOverlapping() {
        testTrivial();
        for (int i = 0; i < Objective.OBJECTIVES.length; i++) {
            Objective objective = Objective.OBJECTIVES[i];
            Vikings game = new Vikings(objective);
            String boardString = objective.getInitialState();
            for (int t1 = 0; t1 < 9; t1++) {
                for (int t2 = 0; t2 < 9; t2++) {
                    if (t1 != t2) {
                        test(boardString, game.tiles[t1].getPosition(), game.tiles[t2].getPosition(), false);
                    }
                }
            }
        }
    }

    @Test
    public void testOverlapping() {
        testTrivial();
        for (int i = 0; i < Utilities.overlappingTiles.length; i++) {
            Objective objective = Utilities.overlappingTiles[i];
            Vikings game = new Vikings(objective);
            String boardString = objective.getInitialState();
            int t1 = Utilities.overlappingPos[i][0];
            int t2 = Utilities.overlappingPos[i][1];
            test(boardString, game.tiles[t1].getPosition(), game.tiles[t2].getPosition(), true);
        }
    }

    private void testTrivial() {
        test(Objective.OBJECTIVES[0].getInitialState(), 0, 1, false);
        test("O1O1N1N0N0O1N0N3N1Rt", 0, 1, true);
    }

}
