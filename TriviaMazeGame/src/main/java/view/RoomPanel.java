package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import model.Door;
import model.Room;

/**
 * RoomPanel displays the door statuses (North, South, East, West)
 * of the Room the Player is currently in.
 * @author Harman Sandhu, Nic Ferencko, MingChun Kao
 * @version 05/2026
 */
public class RoomPanel extends JPanel implements PropertyChangeListener {

    // Door status colors
    /** Open Door background color. */
    private static final Color OPEN_BG   = new Color(144, 238, 144); // light green
    /** Open door foreground color. */
    private static final Color OPEN_FG   = new Color(0,   100,   0);
    /** Closed door background color. */
    private static final Color CLOSED_BG = new Color(255, 236, 153); // light yellow
    /** Closed door foreground color. */
    private static final Color CLOSED_FG = new Color(130, 100,   0);
    /** Locked door background color. */
    private static final Color LOCKED_BG = new Color(255, 160, 160); // light red
    /** Locked door foreground color. */
    private static final Color LOCKED_FG = new Color(139,   0,   0);

    // Door labels — one per direction
    /** Label for the north door. */
    private final JLabel myNorthLabel;
    /** Label for the south door. */
    private final JLabel mySouthLabel;
    /** Label for the east door. */
    private final JLabel myEastLabel;
    /** Label for the west door. */
    private final JLabel myWestLabel;

    /**
     * Builds the RoomPanel with a compass-style layout:
     * North on top, South on bottom, West and East on the sides,
     * and a small "YOU" marker in the centre.
     * Constructor
     */
    public RoomPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Current Room - Doors",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13)));

        myNorthLabel = makeDoorLabel();
        mySouthLabel = makeDoorLabel();
        myEastLabel  = makeDoorLabel();
        myWestLabel  = makeDoorLabel();

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(4, 6, 4, 6);
        gbc.fill    = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Row 0, col 1 — North
        gbc.gridx = 1; gbc.gridy = 0;
        add(wrapWithHeading("North ↑", myNorthLabel), gbc);

        // Row 1, col 0 — West
        gbc.gridx = 0; gbc.gridy = 1;
        add(wrapWithHeading("← West", myWestLabel), gbc);

        // Row 1, col 1 — centre YOU marker
        gbc.gridx = 1; gbc.gridy = 1;
        add(makeCentreMarker(), gbc);

        // Row 1, col 2 — East
        gbc.gridx = 2; gbc.gridy = 1;
        add(wrapWithHeading("East →", myEastLabel), gbc);

        // Row 2, col 1 — South
        gbc.gridx = 1; gbc.gridy = 2;
        //add(wrapWithHeading("↓ South", mySouthLabel), gbc);
        add(wrapWithFooter("↓ South", mySouthLabel), gbc);
    }

    /**
     * Refreshes all four door labels to reflect the given Room's current state.
     * Call this every time the player enters a new room.
     * Public API
     * @param theRoom the Room the player is now in (non-null)
     */
    public void update(final Room theRoom) {
        if (theRoom == null) return;
        applyStatus(myNorthLabel, theRoom.getDoor(Room.Direction.NORTH).getStatus());
        applyStatus(mySouthLabel, theRoom.getDoor(Room.Direction.SOUTH).getStatus());
        applyStatus(myEastLabel,  theRoom.getDoor(Room.Direction.EAST).getStatus());
        applyStatus(myWestLabel,  theRoom.getDoor(Room.Direction.WEST).getStatus());

        revalidate();
        repaint();
    }

    // Private helpers
    
    /** Creates a blank status label for a door. */
    private JLabel makeDoorLabel() {
        JLabel lbl = new JLabel("—", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setOpaque(true);
        lbl.setBackground(Color.LIGHT_GRAY);
        lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lbl.setPreferredSize(new Dimension(80, 28));
        return lbl;
    }

    /**
     * Wraps a direction heading and status label together in a small sub-panel.
     * Ex "North ↑" sits above the green/yellow/red status label.
     */
    private JPanel wrapWithHeading(final String theHeading, final JLabel theLabel) {
        final JPanel p = new JPanel(new GridLayout(2, 1, 0, 2));
        p.setOpaque(false);

        final JLabel heading = new JLabel(theHeading, SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.PLAIN, 11));

        p.add(heading);
        p.add(theLabel);
        return p;
    }

    /**
     * Wraps a direction heading and status label together in a small sub-panel.
     * Ex "↓ South" sits below the green/yellow/red status label.
     */
    private JPanel wrapWithFooter(final String theFooter, final JLabel theLabel) {
        final JPanel p = new JPanel(new GridLayout(2, 1, 0, 2));
        p.setOpaque(false);

        final JLabel footer = new JLabel(theFooter, SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 11));


        p.add(theLabel);
        p.add(footer);
        return p;
    }

    /** Small "YOU" marker for the center cell of the compass. */
    private JLabel makeCentreMarker() {
        final JLabel lbl = new JLabel("YOU", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setOpaque(true);
        lbl.setBackground(new Color(200, 210, 255));
        lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lbl.setPreferredSize(new Dimension(60, 28));
        return lbl;
    }

    /**
     * Colors and labels a door status label according to the given DoorStatus.
     *
     *  OPEN   → green  "Open"
     *  CLOSED → yellow "Closed"
     *  LOCKED → red    "Locked"
     */
    private void applyStatus(final JLabel theLabel, final Door.DoorStatus theStatus) {
        switch (theStatus) {
            case OPEN:
                theLabel.setText("Open");
                theLabel.setBackground(OPEN_BG);
                theLabel.setForeground(OPEN_FG);
                theLabel.setToolTipText("Door is open — you may pass through.");
                break;
            case LOCKED:
                theLabel.setText("Locked");
                theLabel.setBackground(LOCKED_BG);
                theLabel.setForeground(LOCKED_FG);
                theLabel.setToolTipText("Door is locked — you cannot pass through.");
                break;
            case CLOSED:
            default:
                theLabel.setText("Closed");
                theLabel.setBackground(CLOSED_BG);
                theLabel.setForeground(CLOSED_FG);
                theLabel.setToolTipText("Door is closed — answer the trivia question to open it.");
                break;
        }
    }

    /**
     *
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        // do nothing

    }
}
