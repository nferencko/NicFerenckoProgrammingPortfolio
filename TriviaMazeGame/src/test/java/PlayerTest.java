import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player defaultPlayer;
    private Player parameterizedPlayer;

    @BeforeEach
    void setUp() {
        // Create a default player (expected at 0, 0)
        defaultPlayer = new Player();

        // Create a player at location (row 2, col 3)
        parameterizedPlayer = new Player(2, 3);
    }

    /**
     * Tests that the default constructor accurately sets coordinates to (0,0).
     */
    @Test
    void testDefaultConstructorCoordinates() {
        assertEquals(0, defaultPlayer.getRow(),
                "Default constructor must initialize row position to 0.");
        assertEquals(0, defaultPlayer.getCol(),
                "Default constructor must initialize column position to 0.");
    }

    /**
     * Tests that the parameterized constructor accurately saves the provided row and column values.
     */
    @Test
    void testParameterizedConstructorCoordinates() {
        assertEquals(2, parameterizedPlayer.getRow(),
                "Parameterized constructor failed to map specified row.");
        assertEquals(3, parameterizedPlayer.getCol(),
                "Parameterized constructor failed to map specified column.");
    }

    /**
     * Tests that coordinate changes are correctly set and retrieved via setRow() and getRow().
     */
    @Test
    void testSetAndGetRow() {
        defaultPlayer.setRow(5);
        assertEquals(5, defaultPlayer.getRow(),
                "getRow() did not return the expected updated value after setRow().");

        defaultPlayer.setRow(-1);
        assertEquals(-1, defaultPlayer.getRow(),
                "setRow() should accept arbitrary integer bounds.");
    }

    /**
     * Tests that coordinate changes are correctly set and retrieved via setCol() and getCol().
     */
    @Test
    void testSetAndGetCol() {
        defaultPlayer.setCol(8);
        assertEquals(8, defaultPlayer.getCol(),
                "getCol() did not return the expected updated value after setCol().");

        defaultPlayer.setCol(-4);
        assertEquals(-4, defaultPlayer.getCol(),
                "setCol() should accept arbitrary integer bounds.");
    }
}
