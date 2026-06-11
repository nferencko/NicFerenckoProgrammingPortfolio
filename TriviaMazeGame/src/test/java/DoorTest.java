import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoorTest {

    private Door testDoor;
    private Question dummyQuestion;

    @BeforeEach
    void setUp() {
        //Create a hardcoded question manually
        dummyQuestion = new MCQuestion("What color is a banana?",
                "Yellow", List.of("Red", "Yellow", "Blue"));

        // Create a Door object using our mock question
        testDoor = new Door(dummyQuestion);
    }

    /**
     * Tests that a newly created door defaults to DoorStatus.CLOSED.
     */
    @Test
    void testInitialStatusIsClosed() {
        assertEquals(Door.DoorStatus.CLOSED, testDoor.getStatus(),
                "A newly initialized door must default to CLOSED status.");
    }

    /**
     * Tests that getQuestion() returns the exact question reference passed during initialization.
     */
    @Test
    void testGetQuestion() {
        assertNotNull(testDoor.getQuestion(), "The attached question should not be null.");
        assertEquals(dummyQuestion, testDoor.getQuestion(),
                "getQuestion() must return the identical Question instance provided to the constructor.");
        assertEquals("What color is a banana?", testDoor.getQuestion().getQuestionText());
    }

    /**
     * Tests that updating the door's status updates correctly to all valid enum choices.
     */
    @Test
    void testSetAndGetStatus() {
        // Transition to LOCKED
        testDoor.setStatus(Door.DoorStatus.LOCKED);
        assertEquals(Door.DoorStatus.LOCKED, testDoor.getStatus(),
                "Door failed to update status to LOCKED.");

        // Transition to OPEN
        testDoor.setStatus(Door.DoorStatus.OPEN);
        assertEquals(Door.DoorStatus.OPEN, testDoor.getStatus(),
                "Door failed to update status to OPEN.");

        // Transition back to CLOSED
        testDoor.setStatus(Door.DoorStatus.CLOSED);
        assertEquals(Door.DoorStatus.CLOSED, testDoor.getStatus(),
                "Door failed to update status back to CLOSED.");
    }

    /**
     * Tests that canPass() accurately enforces game rules based on status.
     * The player should ONLY be allowed to pass through an OPEN door.
     */
    @Test
    void testCanPass() {
        // 1. Initially CLOSED (Should be false)
        testDoor.setStatus(Door.DoorStatus.CLOSED);
        assertFalse(testDoor.canPass(),
                "A player should not be able to pass through a CLOSED door.");

        // 2. State is LOCKED (Should be false)
        testDoor.setStatus(Door.DoorStatus.LOCKED);
        assertFalse(testDoor.canPass(),
                "A player should not be able to pass through a LOCKED door.");

        // 3. State is OPEN (Should be true)
        testDoor.setStatus(Door.DoorStatus.OPEN);
        assertTrue(testDoor.canPass(), "A player should be able to pass through an OPEN door.");
    }

    /**
     * Tests that you can check answers on the question mapped to this specific door.
     */
    @Test
    void testAnsweringDoorQuestion() {
        // Ensure our dummy question evaluation logic works cleanly in the context of the door
        assertTrue(testDoor.getQuestion().checkAnswer("Yellow"),
                "Door's question should evaluate correct answers.");
        assertFalse(testDoor.getQuestion().checkAnswer("Red"),
                "Door's question should reject incorrect answers.");
    }
}