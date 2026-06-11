
package model;

import java.io.Serializable;

/**
 * Represents a Player in a Maze Game.
 *
 * @author Nic Ferencko
 * @version 1
 */
public class Player implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The current row of this Player.
     */
    private int myRow;
    /**
     * The current column of this Player.
     */
    private int myCol;

    /**
     * Create Player at location (0,0).
     */
    public Player() {
        final int initRow = 0;  // initial player row location
        final int initCol = 0;  // initial player column location
        setRow(initRow);
        setCol(initCol);
    }

    /**
     * Create a Player object at the specified location.
     *
     * @param theRow the row location
     * @param theCol the column location
     */
    public Player(final int theRow, final int theCol) {
        setRow(theRow);
        setCol(theCol);
    }

    /**
     * Set the row location.
     *
     * @param theRow the row location
     */
    public void setRow(final int theRow) {
        myRow = theRow;
    }

    /**
     * Sets the column location.
     *
     * @param theCol the column location
     */
    public void setCol(final int theCol) {
        myCol = theCol;
    }

    /**
     * Gets the row.
     *
     * @return the row
     */
    public int getRow() {
        return myRow;
    }

    /**
     * Gets the column.
     *
     * @return the column
     */
    public int getCol() {
        return myCol;
    }

    /**
     * String representation of Player.
     *
     * @return String representation of Player
     */
    @Override
    public String toString() {
        return "(" + myRow + "," + myCol + ")";
    }

    /**
     * Simple program to test some basic functionality of this class.
     */
    public static void main(final String[] theArgs) {
        final Player defaultP = new Player();
        final Player c = new Player(2, 3);

        System.out.println(defaultP);
        System.out.println(c);
    }
}
