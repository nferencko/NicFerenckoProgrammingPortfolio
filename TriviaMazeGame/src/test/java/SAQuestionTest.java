import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SAQuestionTest {

    private SAQuestion question;
    private final String questionText = "What programming language is this?";
    private final String correctAnswer = "Java";

    @BeforeEach
    void setUp() {
        question = new SAQuestion(questionText, correctAnswer);
    }

    @Test
    void testGetQuestionText() {
        assertEquals(questionText, question.getQuestionText(),
                "Question text should match initialization.");
    }

    @Test
    void testCheckAnswerExactMatch() {
        assertTrue(question.checkAnswer("Java"),
                "Should return true for an exact match.");
    }

    @Test
    void testCheckAnswerIgnoreCase() {
        assertTrue(question.checkAnswer("jAvA"),
                "Should return true regardless of character case.");
    }

    @Test
    void testCheckAnswerWithWhitespace() {
        assertTrue(question.checkAnswer("   Java   "),
                "Should return true when ignoring whitespace.");
    }

    @Test
    void testCheckAnswerIncorrect() {
        assertFalse(question.checkAnswer("Python"),
                "Should return false for a completely incorrect answer.");
    }

    @Test
    void testCheckAnswerPartialMatchFails() {
        assertFalse(question.checkAnswer(" J a v a "),
                "Should return false for embedded whitespace anomalies.");
    }
}
