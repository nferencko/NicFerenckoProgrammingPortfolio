package view;


import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import model.Maze;

/** show the game's current status UI panel (View).
 * @author MingChunKao
 * @version 5/09/2026
 */
public class StatusPanel extends JPanel implements PropertyChangeListener {
    /** Label for the Player location. */
    private JLabel myLocationLabel;
    /** Label for status message. */
    private JLabel myStatusMessageLabel;


    /** Create the StatusPanel. */
    public StatusPanel() {
        setLayout(new GridLayout(1, 2, 10, 0));

        // border with title
        setBorder(BorderFactory.createTitledBorder("Game Status"));

        // initialize tag content
        myLocationLabel = new JLabel("Player Location: "
                + Maze.getInstance().getPlayer(), SwingConstants.CENTER);
        myStatusMessageLabel = new JLabel("Status:Ready to play!", SwingConstants.CENTER);

        // font size
        myLocationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        myStatusMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        myLocationLabel.setForeground(Color.RED);
        myStatusMessageLabel.setForeground(Color.RED);
        //add tag to panel
        add(myLocationLabel);
        add(myStatusMessageLabel);

        addListeners();

    }

    /** Helper method -- add this Panel as a listener to the Maze model. */
    private void addListeners() {

        Maze.getInstance().addPropertyChangeListener(this);
    }

    /**
     * Update the Player location label to the specified values.
     * @param theRow the player's row
     * @param theCol the player's column
     */
    public void updateLocation(final int theRow, final int theCol) {
        myLocationLabel.setText("Location: Room (" + theRow + ", " + theCol + ")");
    }

    /**
     * Update the status message label with the specified message.
     * @param theMessage the status message to be displayed.
     */
    public void setStatusMessage(final String theMessage) {

        myStatusMessageLabel.setText("Status: " + theMessage);
    }

    /**
     * This panel responds to the following events:  PLAYER_MOVE_EVENT,
     *  PLAYER_LOSE_EVENT, PLAYER_WIN_EVENT, QUESTION_CORRECT_EVENT, and
     *  QUESTION_INCORRECT_EVENT.
     *
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if (theEvent.getPropertyName().equals(Maze.PLAYER_MOVE_EVENT)) {
            myLocationLabel.setText("Location: (" + Maze.getInstance().getPlayer().getRow()
                    + ", " + Maze.getInstance().getPlayer().getCol() + ")");
        }

        if (theEvent.getPropertyName().equals(Maze.PLAYER_LOSE_EVENT)) {
            myStatusMessageLabel.setText("End Square Not Reachable -- GAME OVER");
        }

        if (theEvent.getPropertyName().equals(Maze.QUESTION_CORRECT_EVENT)) {
            myStatusMessageLabel.setText("Correct Answer.  Door opened, player moved.");
        }

        if (theEvent.getPropertyName().equals(Maze.QUESTION_INCORRECT_EVENT)) {
            myStatusMessageLabel.setText("Wrong Answer.  Door is locked!!!");
        }

    }
}
