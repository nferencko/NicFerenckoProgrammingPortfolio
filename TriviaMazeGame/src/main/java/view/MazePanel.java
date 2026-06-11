package view;

import model.Maze;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

/**
 * Panel to display the Maze and the Player within that maze.
 *
 * @author Harman Sandhu
 * @version 5/7/2026
 */
public class MazePanel extends JPanel implements PropertyChangeListener {

    /**
     * Window Title.
     */
    private static final String WINDOW_TITLE = "Trivia Maze Game";

    /**
     * Label for room occupied by player.
     */
    private static final String ROOM_LABEL_PLAYER = "Player";

    /**
     * Label for empty room.
     */
    private static final String ROOM_LABEL_EMPTY = "Room";

    /**
     * Label for exit room.
     */
    private static final String ROOM_LABEL_EXIT = "Exit";

    /**
     * Player Color.
     */
    private static final Color PLAYER_COLOR = Color.YELLOW;

    /**
     * Exit Room Color.
     */
    private static final Color EXIT_COLOR = Color.GREEN;

    /**
     * The pending room Color.
     */
    private static final Color PENDING_COLOR = Color.LIGHT_GRAY;


    /** Horizontal Gap for Room buttons. */
    private static final int H_GAP = 5;

    /** Vertical Gap for Room buttons. */
    private static final int V_GAP = 5;

    // creates a 2D array of buttons
    // Each button represents one room in the maze
    /**
     * Room array.
     */
    private final JButton[][] myRoomButtons;

    /**
     * The Room array is mySize x mySize.
     */
    private int mySize;

    //pending room initial position
    /**
     * Row in which Player intends to move.
     */
    private int myPendingRow = -1;
    /**
     * Column to which Player intends to move.
     */
    private int myPendingCol = -1;


    /**
     * Create the MazePanel.
     */
    public MazePanel() {

        mySize = Maze.getInstance().getSize();
        // Creates 2D Array and stores all room buttons
        myRoomButtons = new JButton[mySize][mySize];
        // Creates 4x4 grid, as the 5 represents spacing between buttons
        setLayout(new GridLayout(mySize, mySize, H_GAP, V_GAP));

        // Adds border around panel
        setBorder(BorderFactory.createTitledBorder(WINDOW_TITLE));

        createMazeButtons();

        Maze.getInstance().addPropertyChangeListener(this);
    }

    /**
     * Loops through rows and columns and adds the room button to the panel.
     */
    private void createMazeButtons() {
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                // if exit, label that room exit
                if (Maze.getInstance().getRoom(row, col).isExit()) {
                    final JButton exitButton = new JButton(ROOM_LABEL_EXIT);
                    myRoomButtons[row][col] = exitButton;
                    add(exitButton);
                } else {
                    final JButton roomButton = new JButton(ROOM_LABEL_EMPTY);
                    myRoomButtons[row][col] = roomButton;
                    add(roomButton);
                }
            }
        }
    }

    /**
     * Set the pending room.
     *
     * @param theRow the pending room row
     * @param theCol the pending room column
     */
    public void setPendingRoom(final int theRow, final int theCol) {
        myPendingRow = theRow;
        myPendingCol = theCol;
    }

    /**
     * Set the pending room off the grid (thus no pending room will
     * be displayed.
     */
    public void clearPendingRoom() {
        myPendingRow = -1;
        myPendingCol = -1;
    }

    /**
     * Update the player position.
     *
     * @param theRow the new player row
     * @param theCol the new player column
     */
    public void updatePlayerPosition(final int theRow, final int theCol) {

        resetRooms();

        if (myPendingRow != -1 && myPendingCol != -1) {
            myRoomButtons[myPendingRow][myPendingCol].setBackground(PENDING_COLOR);
        }

        myRoomButtons[theRow][theCol].setText(ROOM_LABEL_PLAYER);
        myRoomButtons[theRow][theCol].setBackground(PLAYER_COLOR);
    }

    /**
     * Iterates through the room array and updates contents with the contents
     * stored in the Maze model.
     */
    private void resetRooms() {
        for (int row = 0; row < mySize; row++) {
            for (int col = 0; col < mySize; col++) {
                if (Maze.getInstance().getRoom(row, col).isExit()) {
                    myRoomButtons[row][col].setText(ROOM_LABEL_EXIT);
                    myRoomButtons[row][col].setBackground(EXIT_COLOR);
                } else {
                    myRoomButtons[row][col].setText(ROOM_LABEL_EMPTY);
                    myRoomButtons[row][col].setBackground(null);
                }
            }
        }
    }

    /**
     * Creates JOptionPanes for display if the game is over.
     *
     * @param theMessage message to display in the JOptionPane
     */
    private void setUpEndGamePanels(final String theMessage) {

        final int answer = JOptionPane.showConfirmDialog(null,
                theMessage + "\nPlay Again?");

        // if they choose yes, exit game.  Otherwise do nothing.
        if (answer == JOptionPane.YES_OPTION) {
            Maze.getInstance().resetMaze();
        } else {
            System.exit(0);
        }

    }

    /**
     * Respond to PropertyChangeEvents.
     *
     * @param theEvent Repsonds to end game events like PLAYER_WIN_EVENT and PLAYER_LOSE_EVENT
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if (theEvent.getPropertyName().equals(Maze.PLAYER_WIN_EVENT)) {
            // move player into exit spot before showing JOptionPane
            updatePlayerPosition(myPendingRow, myPendingCol);
            setUpEndGamePanels("You Win!!!");
        }

        if (theEvent.getPropertyName().equals(Maze.PLAYER_LOSE_EVENT)) {

            setUpEndGamePanels("You Lose!!!");
        }
    }
}