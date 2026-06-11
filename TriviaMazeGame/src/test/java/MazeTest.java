import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    private Maze maze;

    @BeforeEach
    void setUp() throws Exception {
        // Since Maze is a singleton with private constructors, use reflection to call
        // the constructor and create a new instance before each test.  Using the 3 parameter
        // constructor to create our Maze object.
        Constructor<Maze> constructor = Maze.class.getDeclaredConstructor(int.class, int.class, int.class);
        constructor.setAccessible(true);

        // Create 3x3 maze, setting the exit at the bottom-right (2, 2)
        maze = constructor.newInstance(3, 2, 2);

        // Overwrite the static myInstance reference inside the Maze class
        Field instanceField = Maze.class.getDeclaredField("myInstance");
        instanceField.setAccessible(true);
        instanceField.set(null, maze);
    }

    @Test
    void testGetInstanceAndInitialState() {
        assertNotNull(Maze.getInstance(), "getInstance should never return null.");
        assertEquals(maze, Maze.getInstance(), "getInstance must return the current active Singleton instance.");
        assertEquals(3, maze.getSize(), "Maze size should match initialization parameter.");

        // Verify player initial coordinates
        assertEquals(0, maze.getPlayer().getRow(), "Player should start at row 0.");
        assertEquals(0, maze.getPlayer().getCol(), "Player should start at column 0.");
    }

    @Test
    void testSetWallsLocksOuterBoundaries() {
        // Verify that setWalls locked the outermost edges of the board
        assertEquals(Door.DoorStatus.LOCKED, maze.getRoom(0, 0).getDoor(Room.Direction.NORTH).getStatus(),
                "North boundary of the maze should be LOCKED.");
        assertEquals(Door.DoorStatus.LOCKED, maze.getRoom(0, 0).getDoor(Room.Direction.WEST).getStatus(),
                "West boundary of the maze should be LOCKED.");
        assertEquals(Door.DoorStatus.LOCKED, maze.getRoom(2, 2).getDoor(Room.Direction.SOUTH).getStatus(),
                "South boundary of the maze should be LOCKED.");
        assertEquals(Door.DoorStatus.LOCKED, maze.getRoom(2, 2).getDoor(Room.Direction.EAST).getStatus(),
                "East boundary of the maze should be LOCKED.");
    }

    @Test
    void testSharedDoorReferences() {
        // Shared doors between Room(0,0) East and Room(0,1) West should point to the exact same Door object
        Door r00East = maze.getRoom(0, 0).getDoor(Room.Direction.EAST);
        Door r01West = maze.getRoom(0, 1).getDoor(Room.Direction.WEST);

        assertSame(r00East, r01West, "Adjacent rooms must share identical door references.");

        // Changing status on one must modify the other
        r00East.setStatus(Door.DoorStatus.OPEN);
        assertEquals(Door.DoorStatus.OPEN, r01West.getStatus(), "Modifying a shared door state must update both room boundaries.");
    }

    @Test
    void testMovePlayerWhenDoorIsOpen() {
        // Manually unlock the path East
        maze.getRoom(0, 0).getDoor(Room.Direction.EAST).setStatus(Door.DoorStatus.OPEN);

        boolean moved = maze.movePlayer(0, 1, Room.Direction.EAST);

        assertTrue(moved, "movePlayer should return true if the targeted door is OPEN.");
        assertEquals(0, maze.getPlayer().getRow());
        assertEquals(1, maze.getPlayer().getCol(), "Player column position should have advanced to 1.");
    }

    @Test
    void testMovePlayerWhenDoorIsLocked() {
        // Manually lock the path South
        maze.getRoom(0, 0).getDoor(Room.Direction.SOUTH).setStatus(Door.DoorStatus.LOCKED);

        boolean moved = maze.movePlayer(1, 0, Room.Direction.SOUTH);

        assertFalse(moved, "movePlayer should return false if the targeted door is LOCKED.");
        assertEquals(0, maze.getPlayer().getRow(), "Player row position should remain unchanged.");
    }


    @Test
    void testMovePlayerWhenDoorIsClosed() {
        // Setup a dummy closed door with a custom question
        Question dummyQuestion = new SAQuestion("What is 1+1?", "2");
        maze.getRoom(0, 0).getDoor(Room.Direction.EAST).setStatus(Door.DoorStatus.CLOSED);
        maze.getRoom(0, 0).setDoor(Room.Direction.EAST, new Door(dummyQuestion));

        // Setup a mock listener to listen for firePropertyChange notifications
        final List<PropertyChangeEvent> receivedEvents = new ArrayList<>();
        maze.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals(Maze.QUESTION_ASK_EVENT)) {
                receivedEvents.add(evt);
            }
        });

        boolean moved = maze.movePlayer(0, 1, Room.Direction.EAST);

        assertFalse(moved, "Player shouldn't move yet when encountering a CLOSED door.");
    }

    @Test
    void testProcessPlayerAnswerCorrect() {
        Question dummyQuestion = new SAQuestion("True?", "true");
        maze.getRoom(0, 0).setDoor(Room.Direction.EAST, new Door(dummyQuestion));

        // Stage the player interaction loop by hitting a closed door first
        maze.movePlayer(0, 1, Room.Direction.EAST);

        final List<String> eventNames = new ArrayList<>();
        maze.addPropertyChangeListener(evt -> eventNames.add(evt.getPropertyName()));

        // Submit matching answer payload
        maze.processPlayerAnswer("true");

        assertEquals(Door.DoorStatus.OPEN, maze.getRoom(0, 0).getDoor(Room.Direction.EAST).getStatus(),
                "Door status should update to OPEN on a correct answer.");
        assertEquals(1, maze.getPlayer().getCol(), "Player should move to the pending target column automatically.");
        assertTrue(eventNames.contains(Maze.QUESTION_CORRECT_EVENT), "QUESTION_CORRECT_EVENT should fire.");
        assertTrue(eventNames.contains(Maze.PLAYER_MOVE_EVENT), "PLAYER_MOVE_EVENT should fire.");
    }

    @Test
    void testProcessPlayerAnswerIncorrect() {
        Question dummyQuestion = new SAQuestion("True?", "true");
        maze.getRoom(0, 0).setDoor(Room.Direction.EAST, new Door(dummyQuestion));

        // Stage the initial block interaction
        maze.movePlayer(0, 1, Room.Direction.EAST);

        final List<String> eventNames = new ArrayList<>();
        maze.addPropertyChangeListener(evt -> eventNames.add(evt.getPropertyName()));

        // Submit wrong answer
        maze.processPlayerAnswer("wrong answer");

        assertEquals(Door.DoorStatus.LOCKED, maze.getRoom(0, 0).getDoor(Room.Direction.EAST).getStatus(),
                "Door status should update to LOCKED on an incorrect answer.");
        assertEquals(0, maze.getPlayer().getCol(), "Player position must stay back in place.");
        assertTrue(eventNames.contains(Maze.QUESTION_INCORRECT_EVENT), "QUESTION_INCORRECT_EVENT should fire.");
    }

    @Test
    void testIsMazeSolvable() {
        // Open a direct clear path to the exit room at (2,2)
        // Path: (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2)
        maze.getRoom(0, 0).getDoor(Room.Direction.EAST).setStatus(Door.DoorStatus.OPEN);
        maze.getRoom(0, 1).getDoor(Room.Direction.EAST).setStatus(Door.DoorStatus.OPEN);
        maze.getRoom(0, 2).getDoor(Room.Direction.SOUTH).setStatus(Door.DoorStatus.OPEN);
        maze.getRoom(1, 2).getDoor(Room.Direction.SOUTH).setStatus(Door.DoorStatus.OPEN);

        assertTrue(maze.isMazeSolvable(), "Maze should be solvable when an open path exists to the exit.");

        // Trap the player completely by locking all immediate surroundings out of (0,0)
        maze.getRoom(0, 0).getDoor(Room.Direction.EAST).setStatus(Door.DoorStatus.LOCKED);
        maze.getRoom(0, 0).getDoor(Room.Direction.SOUTH).setStatus(Door.DoorStatus.LOCKED);

        assertFalse(maze.isMazeSolvable(), "Maze should not be solvable if the player is entirely boxed in by LOCKED doors.");

    }

    @Test
    void testDetermineEndGamePlayerWins() {
        // Put Player into the exit room
        maze.getPlayer().setRow(2);
        maze.getPlayer().setCol(2);

        final List<String> endEvents = new ArrayList<>();
        maze.addPropertyChangeListener(evt -> endEvents.add(evt.getPropertyName()));

        maze.determineEndGame();
        assertTrue(endEvents.contains(Maze.PLAYER_WIN_EVENT), "determineEndGame() should fire PLAYER_WIN_EVENT when standing on exit.");
    }

    @Test
    void testResetMaze() {
        // Teleport player and lock an adjacent space to dirty up the grid state
        maze.getPlayer().setRow(1);
        maze.getPlayer().setCol(1);
        maze.getRoom(0, 0).getDoor(Room.Direction.EAST).setStatus(Door.DoorStatus.OPEN);

        maze.resetMaze();

        // Ensure everything rolled back to fresh starting parameters cleanly
        assertEquals(0, maze.getPlayer().getRow(), "Reset should move player row back to 0.");
        assertEquals(0, maze.getPlayer().getCol(), "Reset should move player col back to 0.");
    }
}
