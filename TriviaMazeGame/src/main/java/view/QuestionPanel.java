package view;

import model.MCQuestion;
import model.Maze;
import model.Question;
import model.TFQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;
import java.util.List;

/** Panel for displaying the trivia questions.
 * @author MingChunKao, TCSS 360, QuestionPanel class
 * @version 5/10/2026
 */
public class QuestionPanel extends JPanel implements PropertyChangeListener {

    /** Horizontal gap Panel components. */
    private static final int H_GAP = 10;

    /** Vertical gap between Panel components. */
    private static final int V_GAP = 10;

    /** Area for the question text. */
    private final JTextArea myQuestionText;
    /** Area for Question options. */
    private final JPanel myOptionsArea;
    /** Button to submit question. */
    private final JButton mySubmitButton;
    /** Button to cancel question. */
    private final JButton myCancelButton;
    /** Button grouping for multiple choice answer choices. */
    private ButtonGroup myButtonGroup;
    /** Text field for user to enter answer. */
    private JTextField myShortAnswerField;

    /** Create the QuestionPanel. */
    public QuestionPanel() {

        setLayout(new BorderLayout(H_GAP, V_GAP));
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS) );

        setBorder(BorderFactory.createTitledBorder("Question"));

        //top show question
        myQuestionText = new JTextArea("\nWhen you walk into a closed door, "
                + "a question will be revealed.  "
                + "Answer correctly to open the door, otherwise it is locked forever!!!");
        myQuestionText.setWrapStyleWord(true);
        myQuestionText.setLineWrap(true);
        myQuestionText.setEditable(false);
        myQuestionText.setOpaque(false);
        myQuestionText.setFont(new Font("Arial", Font.BOLD, 14));
        add(myQuestionText, BorderLayout.NORTH);

        //middle answer area
        myOptionsArea = new JPanel();
        myOptionsArea.setLayout(new BoxLayout(myOptionsArea, BoxLayout.Y_AXIS));
        add(myOptionsArea, BorderLayout.CENTER);

        //bottom answer button
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, H_GAP, 0));
        // setup submit button
        mySubmitButton = new JButton("Submit (Enter)");
        mySubmitButton.setEnabled(false);
        // setup cancel button
        myCancelButton = new JButton("Cancel (Esc)");
        myCancelButton.setEnabled(false);

        bottomPanel.add(mySubmitButton);
        bottomPanel.add(myCancelButton);
        add(bottomPanel, BorderLayout.SOUTH);

        addListeners();
    }

    /** Add this as a listener to the Maze model. */
    private void addListeners() {
        // need to add property change listeners
        Maze.getInstance().addPropertyChangeListener(this);
    }

    /**
     * create relative answer view base on question type input.
     *
     * @param theQuestion the question
     */
    public void displayQuestion(final Question theQuestion) {
        //clear previous options
        myOptionsArea.removeAll();
        myButtonGroup = new ButtonGroup();
        mySubmitButton.setEnabled(true);
        myCancelButton.setEnabled(true);

        //renew JTextArea text
        myQuestionText.setText(theQuestion.getQuestionText());
        //create view based on question type
        if (theQuestion instanceof MCQuestion) {
            setupMultipleChoice((MCQuestion) theQuestion);
        } else if (theQuestion instanceof TFQuestion) {
            setupTrueFalse();
        } else {
            setupShortAnswer(theQuestion);
        }

        //renew view

        this.revalidate();
        this.repaint();

        // take enter as submit button
        SwingUtilities.getRootPane(mySubmitButton).setDefaultButton(mySubmitButton);
    }

    /**
     * Set up a multiple-choice question.
     * @param theMCQ the Question
     */
    private void setupMultipleChoice(final MCQuestion theMCQ) {
        final List<String> options = theMCQ.getOptions();
        JRadioButton firstBtn = null;
        for (final String option : options) {
            final JRadioButton radioBtn = new JRadioButton(option);
            radioBtn.setActionCommand(option);
            myButtonGroup.add(radioBtn);
            myOptionsArea.add(radioBtn);

            if (firstBtn == null) {
                firstBtn = radioBtn;
            }
        }

        myOptionsArea.add(Box.createVerticalStrut(10));
        final JLabel hintLabel = new JLabel("Tip: Use arrow keys (↑ ↓ ← →) to select options");
        hintLabel.setForeground(Color.GRAY);
        myOptionsArea.add(hintLabel);

        if (firstBtn != null) {
            firstBtn.setSelected(true);
            final JRadioButton finalFirstBtn = firstBtn;
            SwingUtilities.invokeLater(() -> finalFirstBtn.requestFocusInWindow());
        }
    }

    /**
     * Set up a true/fale question.
     */
    private void setupTrueFalse() {
        final JRadioButton trueBtn = new JRadioButton("True");
        trueBtn.setActionCommand("true");

        final JRadioButton falseBtn = new JRadioButton("False");
        falseBtn.setActionCommand("false");

        //can choose only one
        myButtonGroup.add(trueBtn);
        myButtonGroup.add(falseBtn);

        myOptionsArea.add(trueBtn);
        myOptionsArea.add(falseBtn);

        myOptionsArea.add(Box.createVerticalStrut(10));
        final JLabel hintLabel = new JLabel("Tip: Use arrow keys (↑ ↓ ← →) to select options");
        hintLabel.setForeground(Color.GRAY);
        myOptionsArea.add(hintLabel);

        trueBtn.setSelected(true);
        SwingUtilities.invokeLater(() -> trueBtn.requestFocusInWindow());
    }

    /**
     * Set up a short answer question.
     * @param theQuestion the question
     */
    private void setupShortAnswer(final Question theQuestion) {
        myShortAnswerField = new JTextField(15);
        //myOptionsArea.add(new JLabel("Question:  " + theQuestion.getQuestionText()));
        myOptionsArea.add(myShortAnswerField);

        // player can type text once move the position
        SwingUtilities.invokeLater(() -> myShortAnswerField.requestFocusInWindow());
    }

    //for controller
    /** Get the submit button. */
    public JButton getSubmitButton() {
        return mySubmitButton;
    }

    /** for controller: catch the answer from the player input. */
    public String getUserAnswer() {
        //SC
        if (myShortAnswerField != null && myShortAnswerField.getParent() != null) {
            return myShortAnswerField.getText();
        }
        //TF and MC
        if (myButtonGroup != null && myButtonGroup.getSelection() != null) {
            return myButtonGroup.getSelection().getActionCommand();
        }
        return "";
    }

    @Override
    /**
     * Respond QUESTION_ASK_EVENT.
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    public void propertyChange(final PropertyChangeEvent theEvent) {

        // Display the question in the Question panel */
        if (theEvent.getPropertyName().equals(Maze.QUESTION_ASK_EVENT)) {

            displayQuestion((Question) theEvent.getOldValue());
        }
    }

    /** Get the cancel button. */
    public JButton getCancelButton() {
        return myCancelButton;
    }
    public void clearQuestion() {
        myQuestionText.setText("\nWhen you walk into a closed door, a question will be revealed."
                + "  Answer correctly to open the door, otherwise it is locked forever!!!");
        myOptionsArea.removeAll();
        mySubmitButton.setEnabled(false);
        myCancelButton.setEnabled(false);
        this.revalidate();
        this.repaint();
    }
}
