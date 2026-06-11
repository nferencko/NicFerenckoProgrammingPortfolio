package view;

import java.awt.*;
import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
import javax.swing.*;


/** Panel that contains the Player control (movement) buttons.
 * @author Mingchun Kao
 * @version Spring 2026
 */
public class ControlPanel extends JPanel {

    /** Move North button. */
    private final JButton myNorthButton;
    /** Move South button. */
    private final JButton mySouthButton;
    /** Move East button. */
    private final JButton myEastButton;
    /** Move West button. */
    private final JButton myWestButton;

    /**
     * Create the ControlPanel which is a panel with a North, South, East, and West button.
     */
    public ControlPanel() {

        // Padding to add to text to stretch east/west buttons in BorderLayout
        final String buttonPadding = "       ";

        //setLayout(new GridLayout(2, 3, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Player Controls"));

        setLayout(new BorderLayout());
        //setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        // Create Direction buttons.
        // Mnemonic shortcuts commented out because the keyboard controls for game are now
        // all set elsewhere.
        myNorthButton = new JButton("North (press ↑)");
        //myNorthButton.setMnemonic(KeyEvent.VK_UP);
        mySouthButton = new JButton("South (press ↓)");
        //mySouthButton.setMnemonic(KeyEvent.VK_DOWN);
        myEastButton = new JButton(buttonPadding + "East (press →)" + buttonPadding);
        //myEastButton.setMnemonic(KeyEvent.VK_RIGHT);
        myWestButton = new JButton(buttonPadding + "West (press ←)" + buttonPadding);
        //myWestButton.setMnemonic(KeyEvent.VK_LEFT);


        // Add the buttons to the panel
        add(myNorthButton, BorderLayout.NORTH);
        add(myWestButton, BorderLayout.WEST);
        add(mySouthButton, BorderLayout.SOUTH);
        add(myEastButton, BorderLayout.EAST);

        //addListeners();
    }

    /**
     *
     * @return the North button
     */
    public JButton getNorthButton() {
        return myNorthButton;
    }

    /**
     *
     * @return the South button
     */
    public JButton getSouthButton() {
        return mySouthButton;
    }

    /**
     *
     * @return the East button
     */
    public JButton getEastButton() {
        return myEastButton;
    }

    /**
     *
     * @return the West button
     */
    public JButton getWestButton() {
        return myWestButton;
    }


    /** Add a listener to the North button.
     *
     * @param theListener the listener to be added.
     */
    public void addNorthButtonListener(final ActionListener theListener) {
        myNorthButton.addActionListener(theListener);
    }

    /**
     * Add a listener to the South button.
     *
     * @param theListener the listener to be added
     */
    public void addSouthButtonListener(final ActionListener theListener) {
        mySouthButton.addActionListener(theListener);
    }

    /**
     * Add a listener to the East button.
     * @param theListener the listener to be added
     */
    public void addEastButtonListener(final ActionListener theListener) {

        myEastButton.addActionListener(theListener);
    }

    /**
     * Add a listener to the West button.
     * @param theListener the listener to be added
     */
    public void addWestButtonListener(final ActionListener theListener) {

        myWestButton.addActionListener(theListener);
    }
}