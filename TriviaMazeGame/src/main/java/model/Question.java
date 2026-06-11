package model;

import java.io.Serializable;

/**
 * Abstract class for all Question types.
 * @author Nic Ferencko
 * @version Spring 2026
 */
public abstract class Question implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 6L;

    /**
     * The question prompt.
     */
    private String myQuestion;

    public Question(final String theQuestion) {
        myQuestion = theQuestion;
    }

    /**
     * get question string，provide to View (QuestionPanel).
     * Added by: MingChun Kao
     * @return question string
     */
    public String getQuestionText() {
        return myQuestion;
    }

    /**
     * Checks the answer.
     * @param theAnswer the user input
     * @return true if the user is correct, false otherwise
     */
    public abstract boolean checkAnswer(String theAnswer);
}
