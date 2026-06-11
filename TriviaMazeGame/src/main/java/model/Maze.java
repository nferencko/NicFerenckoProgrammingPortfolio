package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * Represents the entire Maze in this game.
 * This model class serves as the driver and where all the action happens in this program.
 * Brings all the model classes into one to be able to interact with user commands, and
 * adjust the status of the maze, player location, if an exit is possible and everything else
 * that may be needed.
 *
 * @author Nic Ferencko
 * @version 1
 */
public class Maze implements Serializable {


    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 3L;

    /**
     * Default # of rows and columns in a maze.
     */
    public static final int DEFAULT_SIZE = 6;

    // Property Change Events ********************

    /**
     * Player has moved event.
     */
    public static final String PLAYER_MOVE_EVENT = "Player Move";

    /**
     * Question asked event.
     */
    public static final String QUESTION_ASK_EVENT = "Question Ask";

    /**
     * Player wins.
     */
    public static final String PLAYER_WIN_EVENT = "Player win";

    /**
     * Player loses.
     */
    public static final String PLAYER_LOSE_EVENT = "Player lose";

    /**
     * Player answers question correctly.
     */
    public static final String QUESTION_CORRECT_EVENT = "Correct Answer";

    /**
     * Player answers question incorrectly.
     */
    public static final String QUESTION_INCORRECT_EVENT = "Incorrect Answer";

    /**
     * 2-D Array of Rooms representing our maze.
     */
    private final Room[][] myMaze;

    /**
     * Num of rows and columns in this maze.
     */
    private final int mySize;

    /**
     * The exit Room in this Maze.
     */
    private Room myExit;

    /**
     * The player in this Maze.
     */
    private final Player myPlayer;

    /**
     * Direction player is attempting to move in.
     */
    private Room.Direction myCurrentAttemptDirection;

    /**
     * Row player is attempting to move to.
     */
    private int myPendingNewRow;

    /**
     * Column player is attempting to move to.
     */
    private int myPendingNewCol;

    /**
     * Property Change Support object.
     */
    private transient PropertyChangeSupport myPcs;

    /**
     * Maze is a Singleton.  myInstance stores the only instance of this object.
     */
    private static Maze myInstance = new Maze();

    /**
     * Create a new Maze object.
     *
     * @param theSize    Number of rows and columns this Maze has.
     * @param theExitRow the row where the exit Room is located.
     * @param theExitCol the column where the exit Room is located.
     */
    private Maze(final int theSize, final int theExitRow, final int theExitCol) {
        mySize = theSize;
        myMaze = new Room[mySize][mySize];
        myPlayer = new Player(); // Starts Player off in [0][0]
        myPcs = new PropertyChangeSupport(this);

        // set the this object to this
        myInstance = this;

        // Fill out the maze with rooms
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                myMaze[row][col] = new Room();
            }
        }

        // Doors between rooms are shared.  This method accomplishes this
        setSharedDoors();

        setWalls();

        // Set the Room at this location as the exit room.
        setExit(theExitRow, theExitCol);
    }


    /**
     * Create a Maze object with DEFAULT_SIZE rows and columns.
     */
    private Maze() {

        this(DEFAULT_SIZE, DEFAULT_SIZE - 1, DEFAULT_SIZE - 1);
    }

    /**
     * Get the one and only instance of this Maze object.
     */
    public static Maze getInstance() {
        return myInstance;
    }

    /**
     * Saves the current instance of the Maze to a file.
     *
     * @param theFilename The name or path of the file to save to.
     * @return true if saved successfully, false otherwise.
     */
    public static boolean saveGame(final String theFilename) {
        try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
                new java.io.FileOutputStream(theFilename))) {

            out.writeObject(myInstance);
            return true;

        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads a Maze instance from a file and restores the Singleton instance.
     *
     * @param theFilename The name or path of the file to load from.
     * @return true if loaded successfully, false otherwise.
     */
    public static boolean loadGame(final String theFilename) {
        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(
                new java.io.FileInputStream(theFilename))) {

            // Deserialize the saved maze object
            final Maze loadedMaze = (Maze) in.readObject();

            // Critical Step: Update the Singleton reference to point to the loaded maze
            myInstance = loadedMaze;
            myInstance.myPcs = new PropertyChangeSupport(myInstance);

            return true;

        } catch (java.io.IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Helper method that sets the EAST-WEST Doors in 1st row to be shared.
     */
    private void setFirstRowSharedDoors() {

        for (int i = 1; i < mySize; i++) {
            myMaze[0][i].setDoor(Room.Direction.WEST, myMaze[0][i - 1].
                    getDoor(Room.Direction.EAST));
        }
    }

    /**
     * Helper method that sets the shared Doors
     * (EAST-WEST and NORTH-SOUTH) to the same reference.
     */
    private void setSharedDoors() {

        // 1st row is a special case and needs to be handled differently
        setFirstRowSharedDoors();

        for (int row = 1; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {

                if (col == 0) {
                    myMaze[row][col].setDoor(Room.Direction.NORTH,
                            myMaze[row - 1][col].getDoor(Room.Direction.SOUTH));
                } else {
                    myMaze[row][col].setDoor(Room.Direction.NORTH,
                            myMaze[row - 1][col].getDoor(Room.Direction.SOUTH));
                    myMaze[row][col].setDoor(Room.Direction.WEST,
                            myMaze[row][col - 1].getDoor(Room.Direction.EAST));
                }

            }
        }
    }

    /**
     * Helper method -- sets all of the doors that lead off of the maze to LOCKED.
     */
    private void setWalls() {

        // First row set the North doors to locked
        // Then set bottom row South doors to locked
        for (int i = 0; i < mySize; i++) {
            getRoom(0, i).getDoor(Room.Direction.NORTH).setStatus(Door.DoorStatus.LOCKED);
            getRoom(mySize - 1, i).getDoor(Room.Direction.SOUTH).setStatus(Door.DoorStatus.LOCKED);
        }

        // Set left col West doors to locked
        // Then set right col East doors to locked
        for (int i = 0; i < mySize; i++) {
            getRoom(i, 0).getDoor(Room.Direction.WEST).setStatus(Door.DoorStatus.LOCKED);
            getRoom(i, mySize - 1).getDoor(Room.Direction.EAST).setStatus(Door.DoorStatus.LOCKED);
        }
    }

    /**
     * Returns size of this Maze.
     *
     * @return Number of rows and columns in this Maze.
     */
    public int getSize() {
        return mySize;
    }

    /**
     * Returns the Room from this Maze located at the specified row, col.
     *
     * @param theRow the row
     * @param theCol the column
     * @return a Room in this Maze.
     */
    public Room getRoom(final int theRow, final int theCol) {
        return myMaze[theRow][theCol];
    }

    /**
     * Sets the exit Room in this Maze.
     *
     * @param theExitRow the theExitRow.
     * @param theExitCol the column.
     */
    public void setExit(final int theExitRow, final int theExitCol) {
        myMaze[theExitRow][theExitCol].setExit(true);
        myExit = myMaze[theExitRow][theExitCol];
    }

    /**
     * Returns the exit Room in this Maze.
     *
     * @return the exit Room.
     */
    public Room getExit() {
        return myExit;
    }

    /**
     * Moves this Player to a new position in this Maze.  Player will be moved
     * if the door to the adjacent room is open.
     *
     * @param theNewRow    the updated row
     * @param theNewCol    the updated column
     * @param theDirection the direction the player is moving
     * @return true if the player moves into Room at theNewRow, theNewCol.
     */
    public boolean movePlayer(final int theNewRow, final int theNewCol,
                              final Room.Direction theDirection) {

        final int currentRow = myPlayer.getRow();
        final int currentCol = myPlayer.getCol();
        final Room currentRoom = getRoom(currentRow, currentCol);

        // Verify that we are not moving off the board
        boolean isMoved = (theNewRow >= 0 && theNewRow < mySize)
                && (theNewCol >= 0 && theNewCol < mySize);

        // if isMoved is false, return false and exit early
        if (!isMoved) {
            return false;
        }

        // Check the Door status.  If LOCKED, player cannot move.  If OPEN, player can move.
        if (currentRoom.getDoor(theDirection).getStatus().equals(Door.DoorStatus.LOCKED)) {
            isMoved = false;
        } else if (currentRoom.getDoor(theDirection).getStatus().equals(Door.DoorStatus.OPEN)) {
            isMoved = true;
        } else {

            // reach to the closed door, send question to gui and pause moving
            final Question q = currentRoom.getDoor(theDirection).getQuestion();
            //keep the direction we are moving
            myCurrentAttemptDirection = theDirection;
            myPendingNewRow = theNewRow;
            myPendingNewCol = theNewCol;
            //let gui show questions
            myPcs.firePropertyChange(QUESTION_ASK_EVENT, q, null);
            //not moving before answer the right answer
            return false;

        }

        // if player isMoved, update their position
        if (isMoved) {
            myPlayer.setRow(theNewRow);
            myPlayer.setCol(theNewCol);
            myPcs.firePropertyChange(PLAYER_MOVE_EVENT, null, null);
        }

        return isMoved;
    }

    /**
     * call method for Controller (GUI) to verify the answer which player input on gui.
     */
    public void processPlayerAnswer(final String theAnswer) {
        final Room currentRoom = getRoom(myPlayer.getRow(), myPlayer.getCol());
        final Door targetDoor = currentRoom.getDoor(myCurrentAttemptDirection);

        final boolean isCorrect = targetDoor.getQuestion().checkAnswer(theAnswer);

        if (isCorrect) {//correct
            targetDoor.setStatus(Door.DoorStatus.OPEN);
            myPlayer.setRow(myPendingNewRow);
            myPlayer.setCol(myPendingNewCol);
            myPcs.firePropertyChange(QUESTION_CORRECT_EVENT, null, null);
        } else {
            //wrong! lock door
            targetDoor.setStatus(Door.DoorStatus.LOCKED);
            myPcs.firePropertyChange(QUESTION_INCORRECT_EVENT, null, null);
        }
        // Want to fire no matter what so Door status gets updated in view after
        // every attempted move.
        myPcs.firePropertyChange(PLAYER_MOVE_EVENT, null, null);
        determineEndGame();  // determine if game is over so that screen can be updated
    }


    /**
     * Returns the Player in this Maze.
     *
     * @return the Player.
     */
    public Player getPlayer() {
        return myPlayer;
    }

    /**
     * Add a PropertyChangeListener to the listener list. The listener is registered for
     * all properties. The same listener object may be added more than once, and will be
     * called as many times as it is added. If listener is null, no exception is thrown and
     * no action is taken.
     *
     * @param theListener The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    /**
     * Remove a PropertyChangeListener from the listener list. This removes a
     * PropertyChangeListener that was registered for all properties. If listener was added
     * more than once to the same event source, it will be notified one less time after being
     * removed. If listener is null, or was never added, no exception is thrown and no action
     * is taken.
     *
     * @param theListener The PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    /**
     * Based on the current Maze, determines if the game is over.  The game is
     * over if either the player is in the exit room OR if there is no path from the
     * player location to the exit room.  If the player is in the exit room, the
     * PLAYER_WIN_EVENT PropertyEvent is fired.  If there is no path from player to
     * the exit room, then the PLAYER_LOSE_EVENT PropertyEvent is fired.
     */
    public void determineEndGame() {

        final int playerRow = myPlayer.getRow();
        final int playerCol = myPlayer.getCol();

        if (getRoom(playerRow, playerCol) == getExit()) {
            myPcs.firePropertyChange(PLAYER_WIN_EVENT, null, null);
        } else if (!isMazeSolvable()) {
            myPcs.firePropertyChange(PLAYER_LOSE_EVENT, null, null);
        }
    }

    /**
     * Determines whether there is still a valid path
     * from the player's current location to the exit.
     *
     * @return true if solvable, false otherwise
     */
    public boolean isMazeSolvable() {

        boolean[][] visited = new boolean[mySize][mySize];

        int startRow = myPlayer.getRow();
        int startCol = myPlayer.getCol();

        return dfs(startRow, startCol, visited);
    }

    /**
     * Depth First Search helper method used to determine
     * whether the maze still has a valid path to the exit.
     *
     * @param theRow     current row
     * @param theCol     current column
     * @param theVisited visited rooms
     * @return true if exit reachable
     */
    private boolean dfs(final int theRow,
                        final int theCol,
                        final boolean[][] theVisited) {

        // out of bounds
        if (theRow < 0 || theRow >= mySize
                || theCol < 0 || theCol >= mySize) {

            return false;
        }

        // already visited
        if (theVisited[theRow][theCol]) {
            return false;
        }

        // reached exit
        if (getRoom(theRow, theCol).isExit()) {
            return true;
        }

        theVisited[theRow][theCol] = true;

        Room currentRoom = getRoom(theRow, theCol);

        // NORTH
        if (!currentRoom.getDoor(Room.Direction.NORTH)
                .getStatus()
                .equals(Door.DoorStatus.LOCKED)) {

            if (dfs(theRow - 1, theCol, theVisited)) {
                return true;
            }
        }

        // SOUTH
        if (!currentRoom.getDoor(Room.Direction.SOUTH)
                .getStatus()
                .equals(Door.DoorStatus.LOCKED)) {

            if (dfs(theRow + 1, theCol, theVisited)) {
                return true;
            }
        }

        // EAST
        if (!currentRoom.getDoor(Room.Direction.EAST)
                .getStatus()
                .equals(Door.DoorStatus.LOCKED)) {

            if (dfs(theRow, theCol + 1, theVisited)) {
                return true;
            }
        }

        // WEST
        if (!currentRoom.getDoor(Room.Direction.WEST)
                .getStatus()
                .equals(Door.DoorStatus.LOCKED)) {

            if (dfs(theRow, theCol - 1, theVisited)) {
                return true;
            }
        }

        return false;
    }

    /**
     * String representation of a Maze.
     *
     * @return String representation of a Maze
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (int row = 0; row < mySize; row++) {
            final StringBuilder line1 = new StringBuilder();
            final StringBuilder line2 = new StringBuilder();
            final StringBuilder line3 = new StringBuilder();

            for (int col = 0; col < mySize; col++) {
                final String[] parts = myMaze[row][col].toString().split("\n");
                line1.append(parts[0]).append(" "); // Top part
                line2.append(parts[1]).append(" "); // Middle part
                line3.append(parts[2]).append(" "); // Bottom part
            }

            sb.append(line1).append("\n");
            sb.append(line2).append("\n");
            sb.append(line3).append("\n");
        }

        return sb.toString();
    }

    /**
     * Simple program to test some basic functionality of this class
     */
    public static void main(String[] args) {
        Maze test = new Maze(3, 2, 2);
        System.out.println(test);
        test.getRoom(0, 0).getDoor(Room.Direction.EAST).
                setStatus(Door.DoorStatus.OPEN);
        test.getRoom(0, 0).getDoor(Room.Direction.SOUTH).
                setStatus(Door.DoorStatus.OPEN);
        test.getRoom(0, 2).getDoor(Room.Direction.WEST).
                setStatus(Door.DoorStatus.OPEN);
        test.getRoom(0, 2).getDoor(Room.Direction.SOUTH).
                setStatus(Door.DoorStatus.OPEN);
        test.getRoom(2, 2).getDoor(Room.Direction.WEST).
                setStatus(Door.DoorStatus.OPEN);
        test.getRoom(2, 2).getDoor(Room.Direction.NORTH).
                setStatus(Door.DoorStatus.OPEN);
        System.out.println(test);
        System.out.println(test.getRoom(2, 2).isExit() + " = " + test.getExit());
        //System.out.println(test.getRoom(0,0).getDoor(Room.Direction.NORTH));

        Maze test2 = new Maze(6, 2, 2);
        System.out.println(test2);
    }

    /**
     * Resets the Maze for a new Game
     */
    public void resetMaze() {
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                myMaze[row][col] = new Room();
            }
        }

        setSharedDoors();
        setWalls();
        setExit(mySize - 1, mySize - 1);
        myPlayer.setRow(0);
        myPlayer.setCol(0);
        myCurrentAttemptDirection = null;
        myPcs.firePropertyChange(PLAYER_MOVE_EVENT, null, null);
    }

}
