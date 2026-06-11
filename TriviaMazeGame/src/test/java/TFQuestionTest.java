import model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TFQuestionTest {

    private final String questionText = "Is the sky blue?";

    @Test
    void testGetQuestionText() {
        TFQuestion question = new TFQuestion(questionText, true);
        assertEquals(questionText, question.getQuestionText(),
                "Question text should match initialization.");
    }

    @Test
    void testCheckAnswerTrueExpectedCorrect() {
        TFQuestion question = new TFQuestion(questionText, true);
        assertTrue(question.checkAnswer(TFQuestion.TRUE),
                "Should return true when input is 'true' and answer is true.");
    }

    @Test
    void testCheckAnswerTrueExpectedIncorrect() {
        TFQuestion question = new TFQuestion(questionText, true);
        assertFalse(question.checkAnswer(TFQuestion.FALSE),
                "Should return false when input is 'false' and answer is true.");
    }

    @Test
    void testCheckAnswerFalseExpectedCorrect() {
        TFQuestion question = new TFQuestion(questionText, false);
        assertTrue(question.checkAnswer(TFQuestion.FALSE),
                "Should return true when input is 'false' and answer is false.");
    }

    @Test
    void testCheckAnswerFalseExpectedIncorrect() {
        TFQuestion question = new TFQuestion(questionText, false);
        assertFalse(question.checkAnswer(TFQuestion.TRUE),
                "Should return false when input is 'true' and answer is false.");
    }

}
