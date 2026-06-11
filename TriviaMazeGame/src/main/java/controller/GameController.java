package controller;

import java.awt.event.ActionEvent;
import javax.swing.*;
import model.Maze;
import model.Room;
import view.ControlPanel;
import view.TriviaMazeGui;


/**
 * The GameController class serves as the main controller in the
 * MVC pattern for the Trivia Maze game.
 * It coordinates communication between the view (TriviaMazeGui) and the model (Maze), managing
 * user inputs, button clicks, menu selections, and game loop updates.
 *
 * @author Harman Sandhu
 * @author Mingchun Kao
 * @author Nicholas Ferencko
 * @version 1.0
 */
public class GameController {

    /**
     * The graphical user interface framework for the trivia maze game.
     */
    private final TriviaMazeGui myGui;

    /**
     * The model representing the maze state.
     */
    private Maze myMaze;

    /**
     * Constructs a new GameController and initializes the connections between
     * the GUI and the Maze model.
     * Sets up all operational listeners, key bindings, and initial views.
     * @param theGui
     */
    public GameController(final TriviaMazeGui theGui) {
        myGui = theGui;
        myMaze = theGui.getMaze(); // Grabs the Maze instance

        setupButtonListeners();
        setupMenuListeners();
        refreshView();
        setupKeyBindings();
    }

    /**
     * Attaches action listeners to all interactive menu items within the application menu bar,
     * such as Reset, Save, Load, Exit, About, and Instructions.
     */
    private void setupMenuListeners() {

        // reset button
        myGui.getResetMenuItem().addActionListener(e -> {
            myMaze.resetMaze();
            myGui.getQuestionPanel().getSubmitButton().setEnabled(false);
            myGui.getStatusPanel().setStatusMessage("game reset !");
            refreshView();
            myGui.getControlPanel().setFocusable(true);
            myGui.getControlPanel().requestFocusInWindow();
        });
        
        // Action Listener for saving a game to a file
        myGui.getSaveMenuItem().addActionListener(e -> {
            saveGameAction();
        });

        // Action Listener for loading a game from a file
        myGui.getLoadMenuItem().addActionListener(e -> {
            loadGameAction();
        });

        // ACTION LISTENER FOR EXITING A GAME
        myGui.getExitMenuItem().addActionListener(e -> {
            menuExitAction();
        });

        // ACTION LISTENER FOR ABOUT MENU ITEM
        myGui.getAboutMenuItem().addActionListener(e -> {
            menuAboutAction();
        });

        // ACTION LISTENER FOR GAME INSTRUCTIONS MENU ITEM
        myGui.getInstructionsMenuItem().addActionListener(e -> {
            menuInstructionsAction();
        });
    }

    /**
     * Helper method called by the "Save Game" JMenuItem.  Open a JFileChooser
     * and save the current state of the game (using Serialization) to a file
     * location chosen by the user.
     */
    private void saveGameAction() {

        // Create a pop-up file chooser window
        final javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Save Game Instance");

        final int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            final java.io.File fileToSave = fileChooser.getSelectedFile();

            // Call the static method we put in Maze.java
            final boolean success = Maze.saveGame(fileToSave.getAbsolutePath());

            if (success) {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Game saved successfully!");
            } else {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Failed to save game.", "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Helper method called by the "Load Game" JMenuItem.  Opens a
     * JFileChooser dialog allowing the user to select a previously saved game file.
     * Deserializes the file data, establishes property change listeners to GUI sub-panels,
     * and refreshes the display to reflect the loaded game state.
     */
    private void loadGameAction() {

        // Create a pop-up file chooser window
        final javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Load Game Instance");

        final int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            final java.io.File fileToLoad = fileChooser.getSelectedFile();

            // Call the static method we put in Maze.java to load the data
            final boolean success = Maze.loadGame(fileToLoad.getAbsolutePath());

            if (success) {
                // Update the local myMaze reference to the newly loaded instance
                // and re-attach all the listeners.
                myMaze = Maze.getInstance();
                myMaze.addPropertyChangeListener(myGui.getQuestionPanel());
                myMaze.addPropertyChangeListener(myGui.getMazePanel());
                myMaze.addPropertyChangeListener(myGui.getRoomPanel());
                myMaze.addPropertyChangeListener(myGui.getStatusPanel());

                // Refresh the UI view elements so the map redraws the loaded state
                refreshView();

                javax.swing.JOptionPane.showMessageDialog(null,
                        "Game loaded successfully!");
            } else {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Failed to load game file.", "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Helper method called by the "Exit" JMenuItem.  If a user chooses exit,
     * prompt them to be sure.  If yes, quit the game.
     */
    private void menuExitAction() {

        final int answer = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to exit?");

        // if they choose yes, exit game.  Otherwise do nothing.
        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Helper method called by menuItem "About".  Displays a
     * JOptionPane with information about this game.
     */
    private void menuAboutAction() {

        JOptionPane.showMessageDialog(null,
                "Trivia Maze Game Developed By:\n"
                + "Harmon Sandhu\n"
                + "Mingchun Kao\n"
                + "Nicholas Ferencko");

    }

    /**
     * Helper method called by menuItem "Game Instructions".  Displays a
     * JOptionPane dialog providing directions on how to play the game.
     */
    private void menuInstructionsAction() {

        final StringBuilder instructions = new StringBuilder();

        instructions.append("GAME INSTRUCTIONS:\n\n");
        instructions.append("Goal:\n  Get to the exit before all paths are closed off.\n\n");
        instructions.append("Controls:\n  To move your player, use the arrow keys "
                        + "or click the movement buttons.\n\n");
        instructions.append("Movement:\n  If you walk into a closed door, a trivia"
                + " question is revealed.  If you");
        instructions.append("\n  answer correctly the door is opened and you move through."
                + " If you answer");
        instructions.append("\n  incorrectly, the door is locked forever.\n\n");
        instructions.append("Game End:\n  If you make it to the exit, you win."
                + "If all paths to the exit are");
        instructions.append("\n  closed before reaching the exit, you lose.");

        JOptionPane.showMessageDialog(null, instructions.toString());
    }

    /**
     * Helper method to set up the navigation button listeners.
     * Binds action listeners to the navigation directional buttons (North, South, East, West)
     * and question tracking buttons (Submit, Cancel) mapped inside the visual panels.
     */
    private void setupButtonListeners() {
        final ControlPanel controls = myGui.getControlPanel();

        controls.addNorthButtonListener(e -> movePlayer(-1, 0, Room.Direction.NORTH));
        controls.addSouthButtonListener(e -> movePlayer(1, 0, Room.Direction.SOUTH));
        controls.addEastButtonListener(e -> movePlayer(0, 1, Room.Direction.EAST));
        controls.addWestButtonListener(e -> movePlayer(0, -1, Room.Direction.WEST));

        // add a listener for cancel button
        myGui.getQuestionPanel().getCancelButton().addActionListener(e -> cancelDoorAttempt());
        
        //submit button
        myGui.getQuestionPanel().getSubmitButton().addActionListener(e -> {
            // catch the answer from the panel
            final String playerAnswer = myGui.getQuestionPanel().getUserAnswer();
            // ensure player has input answer or text
            if (playerAnswer != null && !playerAnswer.isEmpty()) {
                // pass the answer to maze
                myMaze.processPlayerAnswer(playerAnswer);
                // lock submit button
                myGui.getQuestionPanel().getSubmitButton().setEnabled(false);

                myGui.getMazePanel().clearPendingRoom();
                // refresh view
                refreshView();

                // forced focus back to direction buttons once the answers are submitted
                myGui.getControlPanel().setFocusable(true);
                myGui.getControlPanel().requestFocusInWindow();
            }
        });
    }

    /**
     * Helper method.  Evaluates and processes player movement attempts in a designated grid
     * direction. Handles prompts and validation if a question must be resolved
     * before proceeding.
     *
     * @param theRowChange the delta amount added to the current player row position index
     * @param theColChange the delta amount added to the current player column position index
     * @param theDirection the explicit enum Direction orientation of the step taken
     */
    private void movePlayer(final int theRowChange,
                            final int theColChange,
                            final Room.Direction theDirection) {

        if (myGui.getQuestionPanel().getSubmitButton().isEnabled()) {
            myGui.getStatusPanel().setStatusMessage("Please answer your choice.");
            return;
        }

        final int newRow = myMaze.getPlayer().getRow() + theRowChange;
        final int newCol = myMaze.getPlayer().getCol() + theColChange;

        // record the result of this move
        final boolean canMove = myMaze.movePlayer(newRow, newCol, theDirection);

        refreshView();

        // show the status based on the result of the answer
        if (canMove) {
            myGui.getStatusPanel().setStatusMessage("Player moved to the "
                    + theDirection + "!");
        } else {
            if (myGui.getQuestionPanel().getSubmitButton().isEnabled()) {
                myGui.getStatusPanel().setStatusMessage(
                        "Door closed, answer the question to open.");

                myGui.getMazePanel().setPendingRoom(newRow, newCol);
            } else {
                myGui.getStatusPanel().setStatusMessage(
                        "Door locked - try a different direction.");
            }
        }

        myMaze.determineEndGame();
        refreshView();
    }


    /**
     * Helper method that should be called whenever a player is moved.
     * Synchronizes display updates across all sub-panels
     * (MazePanel, RoomPanel, and StatusPanel) using the current
     * model tracking data.
     */
    private void refreshView() {
        final int playerRow = myMaze.getPlayer().getRow();
        final int playerCol = myMaze.getPlayer().getCol();

        // Update all the panels
        myGui.getMazePanel().updatePlayerPosition(playerRow, playerCol);
        myGui.getRoomPanel().update(myMaze.getRoom(playerRow, playerCol));
        myGui.getStatusPanel().updateLocation(playerRow, playerCol);
    }

    /**
     * Resets a door unlock action sequence, discarding the active trivia context,
     * clearing relevant pending maps, and restoring navigation panel focus.
     */
    private void cancelDoorAttempt() {
        // cancel button available only when answering the question
        if (myGui.getQuestionPanel().getSubmitButton().isEnabled()) {

            myGui.getQuestionPanel().clearQuestion();

            myGui.getStatusPanel().setStatusMessage(
                    "cancel door opening attempt！choose new direction again");

            myGui.getMazePanel().clearPendingRoom();
            refreshView();

            // 3. focus back to ControlPanel
            myGui.getControlPanel().setFocusable(true);
            myGui.getControlPanel().requestFocusInWindow();
        }
    }

    /**
     * Configures the Swing input and action maps for directional
     * arrow keys and escape keystrokes, enabling fluid keyboard navigation
     * within the main window context.
     */
    private void setupKeyBindings() {
        final JPanel controlPanel = myGui.getControlPanel();
        final InputMap inputMap = controlPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        final ActionMap actionMap = controlPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            public void actionPerformed(final ActionEvent theEvent) {
                movePlayer(-1, 0, Room.Direction.NORTH);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            public void actionPerformed(final ActionEvent theEvent) {
                movePlayer(1, 0, Room.Direction.SOUTH);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            public void actionPerformed(final ActionEvent theEvent) {
                movePlayer(0, 1, Room.Direction.EAST);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            public void actionPerformed(final ActionEvent theEvent) {
                movePlayer(0, -1, Room.Direction.WEST);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "cancelDoor");
        actionMap.put("cancelDoor", new AbstractAction() {
            public void actionPerformed(final ActionEvent theEvent) {
                cancelDoorAttempt();
            }
        });
    }
}
