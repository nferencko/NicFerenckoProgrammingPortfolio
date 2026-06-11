import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Room class.
 *
 * @author Gemini -- prompted and edited by Nic Ferencko
 */
class RoomTest {

    private Room testRoom;
    private Question dummyMC;
    private Question dummyTF;
    private Question dummySA;

    @BeforeEach
    void setUp() {
        // 1. Create a fresh room instance
        testRoom = new Room();

        // 2. Create simple, predictable concrete question objects manually
        dummyMC = new MCQuestion("What is Java?",
                "Language", List.of("Language", "Coffee"));
        dummyTF = new TFQuestion("Is the sky blue?", true);
        dummySA = new SAQuestion("Type 'hello'", "hello");

        // 3. Use our simple mock objects into the room's doors to bypass the live DB
        testRoom.setDoor(Room.Direction.NORTH, new Door(dummyMC));
        testRoom.setDoor(Room.Direction.SOUTH, new Door(dummyTF));
        testRoom.setDoor(Room.Direction.EAST, new Door(dummySA));
        testRoom.setDoor(Room.Direction.WEST, new Door(new TFQuestion("Is Jalen Brunson the best?", true)));
    }

    /**
     * Tests that the initial exit status of a newly created Room defaults to false.
     */
    @Test
    void testInitialExitState() {
        assertFalse(testRoom.isExit(), "A newly instantiated room should not default to being an exit.");
    }

    /**
     * Tests both setExit() and isExit() methods.
     */
    @Test
    void testSetAndGetExit() {
        testRoom.setExit(true);
        assertTrue(testRoom.isExit(),
                "isExit() should return true after setExit(true) is called.");

        testRoom.setExit(false);
        assertFalse(testRoom.isExit(),
                "isExit() should return false after setExit(false) is called.");
    }

    /**
     * Tests both setDoor() and getDoor() methods by placing a brand new door
     * in a direction and ensuring it can be successfully retrieved.
     */
    @Test
    void testSetAndGetDoor() {
        Question customQuestion = new SAQuestion("What is 2+2?", "4");
        Door customDoor = new Door(customQuestion);

        // Overwrite the North door with our custom door
        testRoom.setDoor(Room.Direction.NORTH, customDoor);

        // Retrieve it and verify it matches perfectly
        Door retrievedDoor = testRoom.getDoor(Room.Direction.NORTH);
        assertNotNull(retrievedDoor, "Retrieved door should not be null.");
        assertEquals(customDoor, retrievedDoor, "getDoor() must return the exact Door object assigned by setDoor().");
        assertEquals("What is 2+2?", retrievedDoor.getQuestion().getQuestionText());
    }

    /**
     * Tests that you can verify the details of the questions assigned to the doors
     * and interact with their game-logic checking methods cleanly.
     */
    @Test
    void testDoorQuestionsAndAnswering() {
        Door northDoor = testRoom.getDoor(Room.Direction.NORTH);
        Door southDoor = testRoom.getDoor(Room.Direction.SOUTH);

        // Verify question texts match our mock objects
        assertEquals("What is Java?", northDoor.getQuestion().getQuestionText());
        assertEquals("Is the sky blue?", southDoor.getQuestion().getQuestionText());

        // Test answering them correctly
        assertTrue(northDoor.getQuestion().checkAnswer("Language"));
        assertTrue(southDoor.getQuestion().checkAnswer("true"));
    }
}
