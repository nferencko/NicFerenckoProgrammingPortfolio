package model;

import java.io.Serializable;

/**
 * Represents a door in the Trivia Maze.
 * Each door is associated with a Question that must be answered
 * correctly in order to pass through it.
 * A door can be:
 * - CLOSED (initial state)
 * - OPENED (answered correctly)
 * - LOCKED (answered incorrectly)
 *
 * @author Harman Sandhu
 * @author Nic Ferencko
 * @version Spring 2026
 */
public class Door implements Serializable {

    /**
     * SerialVersion UID.
     */
    private static final long serialVersionUID = 2L;

    /**
     * All the different states a Door can be in.
     */
    public enum DoorStatus { CLOSED, LOCKED, OPEN }

    /**
     * The status of this Door.
     */
    private DoorStatus myStatus;

    /**
     * The question associated with this door.
     */
    private final Question myQuestion;

    /**
     * Constructs a Door with a given Question.
     *
     * @param theQuestion the Question tied to this door
     */
    public Door(final Question theQuestion) {
        myQuestion = theQuestion;
        setStatus(DoorStatus.CLOSED);
    }


    /**
     * Returns the status of this Door.
     *
     * @return the status
     */
    public DoorStatus getStatus() {
        return myStatus;
    }

    /**
     * Sets the status of this Door.
     *
     * @param theStatus Status of this Door
     */
    public void setStatus(final DoorStatus theStatus) {
        myStatus = theStatus;
    }

    /**
     * Returns the Question associated with this door.
     *
     * @return the Question object
     */
    public Question getQuestion() {
        return myQuestion;
    }


    /**
     * Determines if the player can pass through the door.
     *
     * @return true if answered correctly, false otherwise
     */
    public boolean canPass() {
        return myStatus.equals(DoorStatus.OPEN);
    }

    /**
     * Returns a string representation of the door's state.
     */
    @Override
    public String toString() {
        return myStatus.toString();
    }
}
