package model;

import java.io.Serializable;

/**
 * Short Answer Question.
 *
 * @author Nic Ferencko
 * @version Spring 2026
 */
public final class SAQuestion extends Question implements Serializable {

    private static final long serialVersionUID = 5L;

    /**
     * Answer to this Question.
     */
    private final String myAnswer;

    /**
     * Creates a SAQuestions.
     *
     * @param theQuestion the Question.
     * @param theAnswer   the answer.
     */
    public SAQuestion(final String theQuestion, final String theAnswer) {
        super(theQuestion);
        myAnswer = theAnswer;
    }

    /**
     * Checks the answer passed in to myAnswer. Not case-sensitive.
     * Leading and trailing white spaces are ignored.
     *
     * @param theAnswer the user input
     * @return true when the input is the same as myAnswer, false otherwise
     */
    @Override
    public boolean checkAnswer(final String theAnswer) {

        return myAnswer.equalsIgnoreCase(theAnswer.trim());
    }

    /**
     * Simple program to test some basic functionality of this class.
     */
    public static void main(final String[] theArgs) {

        final Question q = new SAQuestion("Answer is hello?", "Hello");
        final Question n = new SAQuestion("Answer is 12?", "12");

        System.out.println(q.checkAnswer("hello"));
        System.out.println(q.checkAnswer("good"));

        System.out.println(n.checkAnswer("12"));
        System.out.println(n.checkAnswer(" 1 2 "));
        System.out.println(n.checkAnswer(" 12 "));
    }
}
