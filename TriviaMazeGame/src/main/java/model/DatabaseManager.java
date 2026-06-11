package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Connects program to the Question Database and serves questions from the database
 * to the Maze via the getRandomQuestion which is essentially a QuestionFactory.
 *
 * @author Mingchun Kao
 * @version Spring 2026
 */
public class DatabaseManager {

    /** The question database file. */
    private static final String DB_URL = "jdbc:sqlite:GameQuestion.db";

    /** Set to true if you want the questions and answers printed to the console when
     * getRandomQuestion is called.  Otherwise, set to false.
     */
    private static final boolean PRINT_QUESTIONS_TO_CONSOLE = true;

    /**
     * Randomly picks a Question from the database and returns it.
     */
    public Question getRandomQuestion() {
        // use ORDER BY RANDOM() LIMIT 1 randomly choose one question
        final String sql = "SELECT * FROM questions ORDER BY RANDOM() LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                final String type = rs.getString("type");
                final String qText = rs.getString("question_text");
                final String answer = rs.getString("correct_answer");

                if (PRINT_QUESTIONS_TO_CONSOLE) {
                    System.out.println("question: " + qText);
                    System.out.println("correct answer: " + answer);
                }

                // create Question objects base on type
                if (type.equals("MC")) {
                    final String optionsStr = rs.getString("options");
                    final List<String> optionsList =
                            new ArrayList<>(Arrays.asList(optionsStr.split(",")));
                    Collections.shuffle(optionsList);   // shuffle the answer choices
                    // question, answer, options
                    return new MCQuestion(qText, answer, optionsList);
                } else if (type.equals("TF")) {
                    final boolean tfAnswer = Boolean.parseBoolean(answer);
                    return new TFQuestion(qText, tfAnswer);
                } else if (type.equals("SA")) {
                    return new SAQuestion(qText, answer);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Simple program to test our database connection and
     * functionality of getRandomQuestion.
     */
    public static void main(final String[] theArgs) {
        System.out.println("trying to connect and choose randomly question");
        final DatabaseManager db = new DatabaseManager();
        final Question q = db.getRandomQuestion();

        if (q != null) {
            System.out.println("get a question from db");
            System.out.println("-----------------------------------");
            System.out.println("question: " + q.getQuestionText());


            if (q instanceof MCQuestion) {
                System.out.println("mc");
                System.out.println("op: " + ((MCQuestion) q).getOptions());
            } else if (q instanceof TFQuestion) {
                System.out.println("tf");
            } else if (q instanceof SAQuestion) {
                System.out.println("sa");
            }
            System.out.println("-----------------------------------");
        } else {
            System.err.println("failed! check db");
        }
    }
}