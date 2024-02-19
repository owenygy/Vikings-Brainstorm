package vikings.brainstorm;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * This class represents the Vikings - Brainstorm game.
 * <p>
 * The board state is represented by a single string.
 * The first 18 characters represent the placement of the nine tiles in
 * order from left to right and top to bottom.
 * <p>
 * 0   1   2
 * 3   4   5
 * 6   7   8
 * <p>
 * The two tile types are represented by the uppercase characters 'N'
 * and 'O', with the four rotations represented by the digits 0-3.
 * The four boats are represented by an uppercase character which is the
 * first letter of the name of the colour i.e. 'B','G','R','Y'.
 * Edges are represented by a lowercase character 'a'-'x'.
 * <p>
 * <pre>
 *   a   b   c
 * d 0 e 1 f 2 g
 *   h   i   j
 * k 3 l 4 m 5 n
 *   o   p   q
 * r 6 s 7 t 8 u
 *   v   w   x
 * </pre>
 * <p>
 * Thus the string 'Bl' represents a blue boat at edge 'l' (in between
 * tiles '3' and '4'.
 * <p>
 * The first objective in the game book is
 * "N0O1N1N0O0O1N0N3N1Rt", "Rv"
 * Where "N0O1N1N0O0O1N0N3N1Rt" represents the initial boardState and "Rv" represents the objective (ie:
 * the red boat get to edge v)
 */
public class Vikings {
    /**
     * An array of the nine tiles on the Vikings game board.
     * Since this data structure reflects the board state,
     * it is initially empty until we initialize a game.
     */
    final Tile[] tiles;

    /**
     * An array of boats for the current game,
     * containing at least 1 and no more than 4 boats.
     */
    final Boat[] boats;

    /**
     * The objective of this game
     */
    private Objective objective;

    public Vikings(Objective objective) {
        this.objective = objective;
        String boardString = objective.getInitialState();
        tiles = Tile.fromBoardString(boardString);
        boats = Boat.fromBoardString(boardString);
    }

    /**
     * Construct a game for a given level of difficulty.
     * This chooses a new objective and creates a new instance of
     * the game at the given level of difficulty.
     *
     * @param difficulty The difficulty of the game.
     */
    public Vikings(int difficulty) {
        this(Objective.newObjective(difficulty));
    }

    public Objective getObjective() {
        return objective;
    }

    /**
     * A boardString is well-formed if it contains:
     * - nine tiles, each with:
     * - a correct type for each tile;
     * - 6 'N' tiles and 3 'O' tiles;
     * - the correct rotation of tiles (0-3);
     * - at least 1 boat at a valid edge; and
     * - no more than 1 boat of each colour, in order B-G-R-Y.
     * Note: a well-formed boardString is not necessarily a valid boardString!
     * (For example, tiles and boats may overlap.)
     *
     * @param boardString A string representing the board
     * @return true if boardString is well-formed, false if boardString is not well-formed.
     */
    public static boolean isBoardStringWellFormed(String boardString) {
        if (boardString.length()<20)
            return false;
        int countO = 0;
        int countN = 0;
        int countRotation = 0;
        int countPosition = 0;
        int countColor = 0;
        String tiles = boardString.substring(0,18);
        String boats = boardString.substring(18);
        String type = getOdd(tiles);
        String rotation = getEven(tiles);
        String color = getOdd(boats);
        String position = getEven(boats);
        for (int i=0;i<type.length();i++){
            if (type.charAt(i)=='O')
                countO++;
            if (type.charAt(i)=='N')
                countN++; }
        for (int i=0;i<rotation.length();i++){
            if (rotation.charAt(i)>'3')
                countRotation++; }
        for (int i=0;i<position.length();i++){
            if (rotation.charAt(i)>'x')
                countPosition++;}
        for (int i=0;i<color.length();i++){
            if (color.charAt(i)!='B'&&color.charAt(i)!='G'&&color.charAt(i)!='R'&&color.charAt(i)!='Y')
                countColor++;}
        if (countN==6&&countO==3&&countRotation==0&&countPosition==0&&countColor==0&&
                !repeat(boats)&&sortColor(color).equals(color))
            return true;
        else return false;
    }
    private static boolean repeat(String s){
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    return true;
                } } }
        return false;
    }
    private static String sortColor(String s){
        char[] temp = s.toCharArray();
        Arrays.sort(temp);
        return new String(temp);
    }
    private static String getEven(String s) {
        StringBuilder newStr = new StringBuilder();
        int length = s.length();
        for (int i=0; i<length; i++) {
            if (i%2!=0)
            newStr.append(s.charAt(i));
        }
        return newStr.toString();
    }
    private static String getOdd(String s) {
        StringBuilder newStr = new StringBuilder();
        int length = s.length();
        for (int i=0; i < length; i++) {
            if (i%2==0)
            newStr.append(s.charAt(i));
        }
        return newStr.toString();
    }
    /**
     * @param boardString a well-formed board string
     * @param position1   Position of tile 1
     * @param position2   Position of tile 2
     * @return true if Tiles overlap, False if tiles do not overlap
     */
    public static boolean doTilesOverlap(String boardString, int position1, int position2) {
        if (position1>position2){
            int temp = position1;
            position1 = position2;
            position2 = temp;}
        int tile1Direction = getTileDirection(boardString,position1);
        int tile2Direction = getTileDirection(boardString,position2);
        String tile1Type = getTileType(boardString,position1);
        String tile2Type = getTileType(boardString,position2);
        if ((position2-position1==1)&&(position1!=2)&&(position1!=5)){
            if (tile1Type.equals("O")&&tile2Type.equals("O")){
                if (tile1Direction==0||tile1Direction==2)
                    return false;
                if ((tile1Direction==1)||(tile1Direction==3))
                    return (tile2Direction==1||tile2Direction==3); }
            if (tile1Type.equals("N")&&tile2Type.equals("N")){
                if (tile1Direction==0||tile1Direction==3)
                    return false;
                if (tile1Direction==1||tile1Direction==2)
                    return (tile2Direction==0||tile2Direction==3); }
            if (tile1Type.equals("O")&&tile2Type.equals("N")){
                if (tile1Direction==0||tile1Direction==2)
                    return false;
                if (tile1Direction==1||tile1Direction==3)
                    return (tile2Direction==0||tile2Direction==3); }
            if (tile1Type.equals("N")&&tile2Type.equals("O")){
                if (tile1Direction==0||tile1Direction==3)
                    return false;
                if (tile1Direction==1||tile1Direction==2)
                    return (tile2Direction==1||tile2Direction==3); }
        }
        if (position2-position1==3){
            if (tile1Type.equals("O")&&tile2Type.equals("O")){
                if (tile1Direction==1||tile1Direction==3)
                    return false;
                if (tile1Direction==0||tile1Direction==2)
                    return (tile2Direction==0||tile2Direction==2); }
            if (tile1Type.equals("N")&&tile2Type.equals("N")){
                if (tile1Direction==0||tile1Direction==1)
                    return false;
                if (tile1Direction==2||tile1Direction==3)
                    return (tile2Direction==0||tile2Direction==1); }
            if (tile1Type.equals("O")&&tile2Type.equals("N")){
                if (tile1Direction==1||tile1Direction==3)
                    return false;
                if (tile1Direction==0||tile1Direction==2)
                    return (tile2Direction==0||tile2Direction==1); }
            if (tile1Type.equals("N")&&tile2Type.equals("O")){
                if (tile1Direction==0||tile1Direction==1)
                    return false;
                if (tile1Direction==2||tile1Direction==3)
                    return (tile2Direction==0||tile2Direction==2);
            }
        }
       return false;}

    /**
     * A board string is valid if it is well-formed,
     * there are no two boats on the same edge, and no two pieces overlapping.
     *
     * @param boardString a board string
     * @return True if valid, false if invalid.
     */
    public static boolean isBoardStringValid(String boardString) {
        String boats = boardString.substring(18);
        String boatPos = getEven(boats);
        char[] boatP = boatPos.toCharArray();
        if (isBoardStringWellFormed(boardString)){
            for (int a=0;a<boatPos.length();a++){
                for (int b=a+1;b<boatPos.length();b++){
                    if (boatP[a]==boatP[b])
                        return false; }
            }
            for (int i=0;i<9;i++){
                for (int o=0;o<9;o++){
                    if (doTilesOverlap(boardString,i,o))
                        return false; }
            }
            return true;
        }
        return false;
    }

    private static String getTileType(String s, int a){
        return s.substring(2*a,2*a+1);
    }

    private static int getTileDirection(String s, int a){
        return Integer.parseInt(s.substring(2*a+1,2*a+2));
    }

    /**
     * Given two adjacent tiles, decide whether they interlock.
     * Two tiles interlock if there is no gap on the edge between them.
     * For example: in OBJECTIVES[0], Tile "O0" in position "4" interlocks with Tile "O1" in position "2".
     * However, Tile "O0" in position "4" does not interlock with Tile "N0" in position "3".
     *
     * @param position1 int representing the position of tile 1
     * @param position2 int representing the position of tile 2
     * @return True if tiles interlock, False if they do not interlock.
     */

    public static boolean doTilesInterlock(String boardString, int position1, int position2) {
        if (position1>position2){
            int temp = position1;
            position1 = position2;
            position2 = temp;}
        int tile1D = Integer.parseInt(boardString.substring(2*position1+1,2*position1+2));
        int tile2D = Integer.parseInt(boardString.substring(2*position2+1,2*position2+2));
        String tile1T = boardString.substring(2*position1,2*position1+1);
        String tile2T = boardString.substring(2*position2,2*position2+1);
        if (position2-position1==1){
            if (tile1T.equals("O")&&tile2T.equals("O")){
                return (((tile1D==0||tile1D==2)&&(tile2D==1||tile2D==3))||((tile1D==1||tile1D==3)&&(tile2D==0||tile2D==2)));}
            if (tile1T.equals("N")&&tile2T.equals("N")){
                return (((tile1D==0||tile1D==3)&&(tile2D==0||tile2D==3))||((tile1D==1||tile1D==2)&&(tile2D==1||tile2D==2)));}
            if (tile1T.equals("O")&&tile2T.equals("N")){
                return (((tile1D==0||tile1D==2)&&(tile2D==0||tile2D==3))||((tile1D==1||tile1D==3)&&(tile2D==1||tile2D==2)));}
            if (tile1T.equals("N")&&tile2T.equals("O")){
                return (((tile1D==0||tile1D==3)&&(tile2D==1||tile2D==3))||((tile1D==1||tile1D==2)&&(tile2D==0||tile2D==2)));}
        }
        if (position2-position1==3){
            if (tile1T.equals("O")&&tile2T.equals("O")){
                return (((tile1D==0||tile1D==2)&&(tile2D==1||tile2D==3))||((tile1D==1||tile1D==3)&&(tile2D==0||tile2D==2)));}
            if (tile1T.equals("N")&&tile2T.equals("N")){
                return (((tile1D==0||tile1D==1)&&(tile2D==0||tile2D==1))||((tile1D==2||tile1D==3)&&(tile2D==2||tile2D==3)));}
            if (tile1T.equals("O")&&tile2T.equals("N")){
                return (((tile1D==0||tile1D==2)&&(tile2D==2||tile2D==3))||((tile1D==1||tile1D==3)&&(tile2D==0||tile2D==1)));}
            if (tile1T.equals("N")&&tile2T.equals("O")){
                return (((tile1D==0||tile1D==1)&&(tile2D==0||tile2D==2))||((tile1D==2||tile1D==3)&&(tile2D==1||tile2D==3)));}
        }
        return false;
    }

    /**
     * Returns true if the given tile is currently able to be rotated
     * i.e. it has a boat on at least one edge, and its rotation is not
     * blocked by any other tile.
     *
     * @param boardString a valid board string
     * @param position    '0'-'8' represents the position of the tile to be rotated
     * @return True if tile can be rotated, otherwise return false (tile can't be rotated)
     */
    public static boolean canRotateTile(String boardString, int position) {
        String tiles = boardString.substring(0,18);
        String boats = boardString.substring(18);
        String tileType = getEven(tiles);
        String tilePos = getOdd(tiles);
        String boatPos = getEven(boats);
        int amount = boatPos.length();
        char[] tilePositions = tilePos.toCharArray();
        char[] tileTypes = tileType.toCharArray();
        char[] boatPositions = boatPos.toCharArray();
        return true;
    }

    public static void main(String[] args) {
        String a = "ByGk";
        char[] b = a.toCharArray();
        System.out.println(b[0]);
    }

    /**
     * Rotate the specified tile one quarter-turn (90 degrees) clockwise.
     *
     * @param boardString String representing the board
     * @param pos         position of the tile to be rotated
     * @return An updated boardString that reflects the rotation
     */
    public static String rotateTile(String boardString, int pos) {
        return "";
    }

    /**
     * Given an objective, return a sequence of rotations that solves it.
     * The sequence of rotations is a String in which each character is an
     * integer representing the position of the tile to be rotated.
     * All rotations are clockwise quarter-turns (90 degrees).
     * For example, the String "8887" represents three clockwise quarter-
     * turns of tile number eight, followed by a single turn of tile
     * number 7.
     *
     * @param objective an objective for the Vikings game
     * @return a String representing the sequence of rotations to solve the objective,
     * or an empty String if no solution exists
     */
    public static String findSolution(comp1110.ass1.Objective objective) {
        return "";
    }
}
