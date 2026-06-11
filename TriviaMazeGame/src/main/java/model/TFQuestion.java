package model;

import java.io.Serializable;

/**
 * True False Question.
 *
 * @author Nic Ferencko
 * @version Spring 2026
 */
public final class TFQuestion extends Question implements Serializable {


    /** Answer choice representing true. */
    public static final String TRUE = "true";

    /** Answer choice representing false. */
    public static final String FALSE = "false";

    /** SerialVersion UID. */
    private static final long serialVersionUID = 5L;

    /**
     * the answer to this Question.
     */
    private final boolean myAnswer;

    /**
     * Creates a new TFQuestion.
     *
     * @param theQuestion the Question
     * @param theAnswer   the answer
     */
    public TFQuestion(final String theQuestion, final boolean theAnswer) {
        super(theQuestion);
        myAnswer = theAnswer;
    }

    /**
     * Checks to see if the answer is correct.
     *
     * @param theAnswer the user input. Valid inputs are "true", "false"
     * @return true if theAnswer is correct, false otherwise
     * @throws IllegalArgumentException if theAnswer is invalid input
     */
    @Override
    public boolean checkAnswer(final String theAnswer) {

        if (!theAnswer.equals(TRUE) && !theAnswer.equals(FALSE)) {
            throw new IllegalArgumentException("Invalid Answer");
        }

        return Boolean.toString(myAnswer).equals(theAnswer);
    }

    /**
     * Simple program to test some basic functionality of this class.
     */
    public static void main(final String[] theArgs) {

        final Question q = new TFQuestion("T?", true);

        System.out.println(q.checkAnswer(TRUE));
        System.out.println(q.checkAnswer(FALSE));
        //System.out.println(q.checkAnswer("exception"));
    }
}
