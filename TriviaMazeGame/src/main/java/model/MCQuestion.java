package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Multiple Choice Question.
 * @author Nic Ferencko
 * @version Spring 2026
 */
public final class MCQuestion extends Question implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 2L;

    /**
     * A List of answer options.
     */
    private final List<String> myOptions;

    /**
     * The correct answer.
     */
    private final String myAnswer;

    /**
     * Creates a new MCQuestion.
     * @param theQuestion the Question
     * @param theAnswer the answer
     * @param theOptions the List of possible options
     */
    public MCQuestion(final String theQuestion, final String theAnswer,
                      final List<String> theOptions) {
        super(theQuestion);
        Objects.requireNonNull(theOptions);

        // take away any leading or trailing whitespace
        myAnswer = theAnswer.trim();
        myOptions = theOptions;

        // If theOptions does not contain theAnswer, add the answer
        if (!theOptions.contains(myAnswer)) {
            myOptions.add(myAnswer);
        }
    }

    /**
     * Gets the List of options.
     * @return an unmodifiable copy of myOptions
     */
    public List<String> getOptions() {
        return List.copyOf(myOptions);
    }

    /**
     * Adds an option to myOptions.
     * @param theOption the option
     */
    public void addOption(final String theOption) {
        // trim any leading or trailing whitespace
        myOptions.add(theOption.trim());
    }

    /**
     * Checks the user input's answer.
     * @param theAnswer the user input
     * @return true if the answer is correct, false otherwise
     */
    @Override
    public boolean checkAnswer(final String theAnswer) {
        return myAnswer.equals(theAnswer.trim());
    }

    /** Simple program to test some basic functionality of this class. */
    public static void main(final String[] theArgs) {

        final List<String> options = new ArrayList<>();
        options.add("Hello");
        options.add("World");
        options.add("I'm");
        options.add("Correct");

        final Question q = new MCQuestion("Which One?", "Correct", options);

        System.out.println(q.checkAnswer("Correct"));
        System.out.println(q.checkAnswer("World"));
        System.out.println(q.checkAnswer("garbage"));

        // Check for correct answer not starting in options list
        final List<String> empty = new ArrayList<>();

        final Question m = new MCQuestion("Which One?", "Correct", empty);

        System.out.println(m.checkAnswer("Correct"));

        final List<String> l = ((MCQuestion) q).getOptions();
        System.out.println(l);

        // Throws exception because l is an immutable List.
        // l.add("bad");
    }
}
