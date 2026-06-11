package view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import controller.GameController;
import model.Maze;
 
/**
 *
 * This class contains the Main method of our program.  Run this class to play the
 * TriviaMazeGame.
 *
 * MingChunKao, TCSS 360, TriviaMazeGui class
 * the main panel combining all panels
 */
public class TriviaMazeGui extends JFrame implements PropertyChangeListener {
 
    // Class Constants for all Menu Item Names
    /** File menu item name. */
    private static final String FILE_MENU_ID = "File";
    /** Help menu item name. */
    private static final String HELP_MENU_ID = "Help";
    /** Save menu item name. */
    private static final String SAVE_MENU_ID = "Save Game";
    /** Load menu item name. */
    private static final String LOAD_MENU_ID = "Load Game";
    /** Exit menu item. */
    private static final String EXIT_MENU_ID = "Exit";
    /** About Menu Item. */
    private static final String ABOUT_MENU_ID = "About";
    /** Instructions Menu Item. */
    private static final String INSTRUCTIONS_MENU_ID = "Game Play Instructions";
    /** Reset menu item. */
    private static final String RESET_MENU_ID = "Reset Game";
 
    /** Default horizontal gap. */
    private static final int H_GAP = 5;
    /** Default vertical gap. */
    private static final int V_GAP = 5;
    /** Default min frame width. */
    private static final int MIN_WIDTH = 900;
    /** Default minimum frame height. */
    private static final int MIN_HEIGHT = 600;
 
    /** Panel to show status. */
    private final StatusPanel myStatusPanel;
    /** Panel to show Maze. */
    private final MazePanel myMazePanel;
    /** Panel to show movement buttons. */
    private final ControlPanel myControlPanel;
    /** Panel to show questions. */
    private final QuestionPanel myQuestionPanel;
    /** Panel to show current room door statuses */
    private final RoomPanel myRoomPanel;
 
    /** Maze model being represented by this GUI */
    private final Maze myMaze;
 
    /** Create the GUI. */
    public TriviaMazeGui(){
        final String windowTitle = "Trivia Maze Game Team Project";
 
        super(windowTitle);
        myMaze = Maze.getInstance();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        myStatusPanel   = new StatusPanel();
        myMazePanel     = new MazePanel();
        myControlPanel  = new ControlPanel();
        myQuestionPanel = new QuestionPanel();
        myRoomPanel     = new RoomPanel();
 
        setupMenuBAR();
 
        setLayout(new BorderLayout(H_GAP, V_GAP));
 
        // status bar on top
        add(myStatusPanel, BorderLayout.NORTH);
 
        // left area: maze grid + movement controls
        JPanel leftContainer = new JPanel(new BorderLayout(H_GAP, V_GAP));
        leftContainer.add(myMazePanel,    BorderLayout.CENTER);
        leftContainer.add(myControlPanel, BorderLayout.SOUTH);
        add(leftContainer, BorderLayout.WEST);
 
        // center area: room door info on top, question panel below
        JPanel centerContainer = new JPanel(new BorderLayout(H_GAP, V_GAP));
        centerContainer.add(myRoomPanel,     BorderLayout.NORTH);
        centerContainer.add(myQuestionPanel, BorderLayout.CENTER);
        myQuestionPanel.setPreferredSize(new Dimension(MIN_WIDTH / 3, MIN_HEIGHT / 4));
        add(centerContainer, BorderLayout.CENTER);
 
        // listen for player-move and question events so RoomPanel stays in sync
        myMaze.addPropertyChangeListener(this);
 
        pack();
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLocationRelativeTo(null);
 
        // show the starting room's doors right away
        // I actually don't think this method is necessary -- comment out for now until more sure
        //refreshRoomPanel();
    }
 
    /**
     * Reads the player's current position from the Maze and updates the RoomPanel.
     */
    private void refreshRoomPanel() {
        myRoomPanel.update( myMaze.getRoom(myMaze.getPlayer().getRow(), myMaze.getPlayer().getCol()) );
      }

    /**
     * If there is a PLAYER_MOVE_EVENT or a QUESTION_ASK_EVENT, refresh the RoomPanel.
     *
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        // Refresh door display whenever the player moves OR after a question is answered
        if (Maze.PLAYER_MOVE_EVENT.equals(theEvent.getPropertyName()) ||
            Maze.QUESTION_ASK_EVENT.equals(theEvent.getPropertyName())) {

             refreshRoomPanel();
        }
    }
 
    /**
     * Set up the system menu bar (file, help).
     */
    private void setupMenuBAR() {
        JMenuBar menuBar = new JMenuBar();
 
        JMenu fileMenu = new JMenu(FILE_MENU_ID);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setName(FILE_MENU_ID);
 
        JMenuItem saveItem = new JMenuItem(SAVE_MENU_ID);
        saveItem.setMnemonic(KeyEvent.VK_S);
        saveItem.setName(SAVE_MENU_ID);
 
        JMenuItem loadItem = new JMenuItem(LOAD_MENU_ID);
        loadItem.setMnemonic(KeyEvent.VK_L);
        loadItem.setName(LOAD_MENU_ID);

        JMenuItem resetItem = new JMenuItem(RESET_MENU_ID);
        resetItem.setMnemonic(KeyEvent.VK_R);
        resetItem.setName(RESET_MENU_ID);
        resetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK)); // Ctrl + R
 
        JMenuItem exitItem = new JMenuItem(EXIT_MENU_ID);
        exitItem.setMnemonic(KeyEvent.VK_E);
        exitItem.setName(EXIT_MENU_ID);
 
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        fileMenu.add(resetItem);
 
        JMenu helpMenu = new JMenu(HELP_MENU_ID);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpMenu.setName(HELP_MENU_ID);
 
        JMenuItem aboutItem = new JMenuItem(ABOUT_MENU_ID);
        aboutItem.setMnemonic(KeyEvent.VK_A);
        aboutItem.setName(ABOUT_MENU_ID);
 
        JMenuItem instructionsItem = new JMenuItem(INSTRUCTIONS_MENU_ID);
        instructionsItem.setMnemonic(KeyEvent.VK_I);
        instructionsItem.setName(INSTRUCTIONS_MENU_ID);
 
        helpMenu.add(aboutItem);
        helpMenu.add(instructionsItem);
 
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
 
        setJMenuBar(menuBar);
    }
 
    /** @return the Save menu item */
    public JMenuItem getSaveMenuItem(){
        return getMenuItem(getJMenuBar(), SAVE_MENU_ID);
    }
 
    /** @return the Load menu item */
    public JMenuItem getLoadMenuItem(){
        return getMenuItem(getJMenuBar(), LOAD_MENU_ID);
    }
 
    /** @return the Exit menu item */
    public JMenuItem getExitMenuItem(){
        return getMenuItem(getJMenuBar(), EXIT_MENU_ID);
    }
 
    /** @return the About menu item */
    public JMenuItem getAboutMenuItem(){
        return getMenuItem(getJMenuBar(), ABOUT_MENU_ID);
    }
 
    /** @return the Game Instructions menu item */
    public JMenuItem getInstructionsMenuItem(){
        return getMenuItem(getJMenuBar(), INSTRUCTIONS_MENU_ID);
    }

    /** @return the Reset menu item */
    public JMenuItem getResetMenuItem(){
        return getMenuItem(getJMenuBar(), RESET_MENU_ID);
    }
 
    private JMenuItem getMenuItem(JMenuBar theBar, String theMenuID){
        if (theMenuID == null) return null;
        for(int i = 0; i < theBar.getMenuCount(); i++){
            JMenu menu = theBar.getMenu(i);
            if (menu == null) continue;
            for(int j = 0; j < menu.getItemCount(); j++){
                JMenuItem item = menu.getItem(j);
                if(item != null && theMenuID.equals(item.getName())) return item;
            }
        }
        return null;
    }
 
    /** @return the Maze object associated with this GUI */
    public Maze getMaze() { return myMaze; }
 
    /** @return the MazePanel */
    public MazePanel getMazePanel() { return myMazePanel; }
 
    /** @return the ControlPanel */
    public ControlPanel getControlPanel() { return myControlPanel; }
 
    /** @return the QuestionPanel */
    public QuestionPanel getQuestionPanel() { return myQuestionPanel; }
 
    /** @return the StatusPanel */
    public StatusPanel getStatusPanel() { return myStatusPanel; }
 
    /** @return the RoomPanel */
    public RoomPanel getRoomPanel() { return myRoomPanel; }
 
    /** Create the GUI and run the program */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TriviaMazeGui gui = new TriviaMazeGui();
            new GameController(gui);
            gui.setVisible(true);
        });
    }
}
 
