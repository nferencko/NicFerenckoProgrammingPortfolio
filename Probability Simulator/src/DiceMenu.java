/**
 *  * Team One:  Nic Ferencko, Finnegan Allen, Jacob Cruz, Jeremy Soriano
 * 
 * DiceMenu Program by Jeremy Soriano
 *
 * Overview:
 * This Java application is part of a larger suite of probability games.
 *
 * Key Features:
 * Features:
 * 1. Multiple Game Modes: Includes various dice-based games such as a single die roll, dual dice roll with 
 *    attempt limitations, and targeted sum achievement, each offering a unique probability scenario.
 * 2. Educational Emphasis: Each game mode is crafted to enhance understanding of randomness and 
 *    probability, making the program a valuable educational resource.
 * 3. User Interface: Equipped with an intuitive menu system for game selection and clear feedback 
 * 4. Exception Handling: Implements robust exception handling for reliable input processing
 * 5. Analytical Component: Utilizes data structures like ArrayList to track and display the history of dice 
 *    rolls, adding an analytical perspective to the gameplay.
 */



import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class DiceMenu {

    public static void runDiceMenu(Scanner s) {
        final int MAX_MENU_ITEM = 4;
        final int LINE_LENGTH = 80;

        boolean quit = false;

        while (!quit) {
            System.out.println();
            printStarLine(LINE_LENGTH);
            System.out.println("Enter a number between 1 and " + MAX_MENU_ITEM + ".");
            System.out.println();

            System.out.println("(1) Roll a single die (Win on a 6)");
            System.out.println("(2) Roll two dice (Win if sum >= 10)");
            System.out.println("(3) Sum of two dice equals 7 (Win on a sum of 7)");
            System.out.println("(4) Return to main menu.");
            System.out.print("Menu Choice --> ");

            try {
                int menuChoice = s.nextInt();

                if (menuChoice < 1 || menuChoice > MAX_MENU_ITEM) {
                    throw new IllegalArgumentException("Bad input. Enter a number 1-" + MAX_MENU_ITEM + " please!");
                }

                if (menuChoice == MAX_MENU_ITEM) {
                    quit = true;
                    System.out.println();
                } else {
                    switch (menuChoice) {
                        case 1:
                            rollSingleDie();
                            break;
                        case 2:
                            rollTwoDiceWithHistory();
                            break;
                        case 3:
                            sumOfTwoDiceEqualsSeven();
                            break;
                    }
                }
            } catch (Exception e) {
                s.nextLine(); // Clear the bad input
                System.out.println("You entered bad input!");
                System.out.println("Please enter a number between 1 and " + MAX_MENU_ITEM + " when prompted. Press any key to continue.");
                s.nextLine();
            }
        }
    }

    private static void rollSingleDie() {
        Random rand = new Random();
        int roll = rand.nextInt(6) + 1;
        System.out.println("You rolled a " + roll + ".");
        if (roll == 6) {
            System.out.println("Congratulations! You rolled a 6 and won!");
        } else {
            System.out.println("Sorry, you didn't roll a 6. Try again!");
        }
    }
    // 3 Atttempts then Game Over and show Roll History
    private static void rollTwoDiceWithHistory() {
        Random rand = new Random();
        ArrayList<Integer> rollHistory = new ArrayList<>();
        int numberOfAttempts = 3; // Set the number of attempts
    
        for (int attempt = 0; attempt < numberOfAttempts; attempt++) {
            int roll1 = rand.nextInt(6) + 1;
            int roll2 = rand.nextInt(6) + 1;
            int sum = roll1 + roll2;
            rollHistory.add(sum);
    
            System.out.println("Attempt " + (attempt + 1) + ": You rolled " + roll1 + " and " + roll2 + " (Sum: " + sum + ").");
    
            if (sum >= 10) {
                System.out.println("Congratulations! Your roll is 10 or higher. You win!");
                break;
            } else if (attempt == numberOfAttempts - 1) {
                System.out.println("Sorry, you've used all your attempts. Game over!");
            } else {
                System.out.println("Your total roll is less than 10. Try again...");
            }
        }
    
        System.out.println("\nYour roll history: " + rollHistory);
    }
    

    private static void sumOfTwoDiceEqualsSeven() {
        Random rand = new Random();
        int roll1 = rand.nextInt(6) + 1;
        int roll2 = rand.nextInt(6) + 1;
        System.out.println("You rolled " + roll1 + " and " + roll2 + ".");
        if (roll1 + roll2 == 7) {
            System.out.println("The sum is 7! You win!");
        } else {
            System.out.println("The sum is not 7. Try again!");
        }
    }

    private static void printStarLine(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}