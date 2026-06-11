import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MCQuestionTest {

    private List<String> optionsList;
    private final String questionText = "What is the capital of France?";
    private final String correctAnswer = "Paris";

    @BeforeEach
    void setUp() {
        optionsList = new ArrayList<>();
        optionsList.add("London");
        optionsList.add("Paris");
        optionsList.add("Berlin");
    }

    @Test
    void testConstructorAddsAnswerIfMissing() {
        List<String> incompleteOptions = new ArrayList<>();
        incompleteOptions.add("London");
        incompleteOptions.add("Berlin");

        MCQuestion question = new MCQuestion(questionText, correctAnswer, incompleteOptions);

        assertTrue(question.getOptions().contains(correctAnswer),
                "Constructor should append the correct answer to the options if it is missing.");
    }

    @Test
    void testConstructorThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new MCQuestion(questionText, correctAnswer, null);
        }, "Constructor should throw NullPointerException if options list is null.");
    }

    @Test
    void testGetQuestionText() {
        MCQuestion question = new MCQuestion(questionText, correctAnswer, optionsList);
        assertEquals(questionText, question.getQuestionText(), "Question text should match initialization.");
    }


    @Test
    void testAddOption() {
        MCQuestion question = new MCQuestion(questionText, correctAnswer, optionsList);
        question.addOption("Rome");

        assertTrue(question.getOptions().contains("Rome"), "addOption should successfully append to options.");
    }

    @Test
    void testCheckAnswerCorrect() {
        MCQuestion question = new MCQuestion(questionText, correctAnswer, optionsList);
        assertTrue(question.checkAnswer("Paris"), "Should return true for exact match.");
    }

    @Test
    void testCheckAnswerCorrectWithWhitespace() {
        MCQuestion question = new MCQuestion(questionText, correctAnswer, optionsList);
        assertTrue(question.checkAnswer("  Paris  "), "Should return true when leading/trailing spaces are trimmed.");
    }


    @Test
    void testCheckAnswerIncorrect() {
        MCQuestion question = new MCQuestion(questionText, correctAnswer, optionsList);
        assertFalse(question.checkAnswer("London"), "Should return false for a wrong option.");
    }
}