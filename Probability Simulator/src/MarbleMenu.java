import java.util.Random;
import java.util.Scanner;

//This part of the code was created by Jacob Cruz

//Team One:  Nic Ferencko, Finnegan Allen, Jacob Cruz, Jeremy Soriano

//This is a game of chance using random nums to simulate marbles
//There can be 2-6 teams(or colors)
//You can use any integer for the amount of marbles you like
//You guess on which team you think will win, and it will let you know if you did
//or didn't and the chance of that team winning


public class MarbleMenu {

    static int marbleTotal;
    static int teams;
    static int marbleColor;

    public static void runMarbleMenu(Scanner s) {
        final int MAX_MENU_ITEM = 4;
        final int LINE_LENGTH = 80;

        boolean quit = false;

        while (!quit) {
            System.out.println();
            printStarLine(LINE_LENGTH);
            System.out.println("Enter a number between 1 and " + MAX_MENU_ITEM + ".");
            System.out.println();

            //used these print statements from the other games in the code so it has similar looks
            System.out.println("(1) Evenly divided marbles");
            System.out.println("(2) Randomly divided marbles");
            System.out.println("(3) Run until you win");
            System.out.println("(4) Return to main menu.");
            System.out.print("Menu Choice --> ");


            try {

                //Uses this num to decide which game youre playing
                int marbleSelect = s.nextInt();

                //if you select in this case 5, it ends the game and takes you to the menu
                if (marbleSelect == MAX_MENU_ITEM) {
                    quit = true;
                    System.out.println();
                    break;
                }

                System.out.println("Enter a number greater than 1 to select how many marbles you would like.");
                //total amount of marbles
                marbleTotal = s.nextInt();

                System.out.println("Enter a number 2-6 to select how many teams you would like.");
                //how many teams the marbles are divided into
                teams = s.nextInt();


                System.out.println("Now select which color you think will win.");

                //switch case that increases the amount of options based on how many teams you have
                switch (teams) {
                    case 2:
                        //2 teams
                        System.out.println("\n1. Red \n2. Orange");
                        break;
                    case 3:
                        //3 teams
                        System.out.println("\n1. Red \n2. Orange \n3. Yellow");
                        break;
                    case 4:
                        //4 teams
                        System.out.println("\n1. Red \n2. Orange \n3. Yellow \n4. Green");
                        break;
                    case 5:
                        //5 teams
                        System.out.println("\n1. Red \n2. Orange \n3. Yellow \n4. Green \n5. Blue ");
                        break;
                    case 6:
                        //6 teams
                        System.out.println("\n1. Red \n2. Orange \n3. Yellow \n4. Green \n5. Blue \n6. Purple");
                        break;
                }

                //The color/team you think will win
                marbleColor = s.nextInt();


                //if you did not type a valid num this error appears
                if (marbleTotal <= 1 || marbleSelect < 0 || marbleSelect > MAX_MENU_ITEM || teams < 2 || teams > 6) {
                    throw new IllegalArgumentException("Bad input. Enter a valid number please!");
                }

                //uses the marbleSelect at the top that was decided earlier
                //and then uses the game with the correlating num
                switch (marbleSelect) {
                    case 1:
                        evenlyDivided();
                        break;
                    case 2:
                        randomlyDivided();
                        break;
                    case 3:
                        runUntilDone();
                        break;
                }

                //error appears if a wrong num
                //used from another members code
            } catch (Exception e) {
                s.nextLine(); // Clear the bad input
                System.out.println("You entered bad input!");
                System.out.println("Please enter a number between 1 and " + MAX_MENU_ITEM + " when prompted. Press any key to continue.");
                s.nextLine();
            }
        }
    }

    private static void evenlyDivided() {
        Random rand = new Random();


        //determines each teams win %
        double winPercent = ((double) 1 / teams) * 100;
        //how many marbles each team has
        int marbleDivide = marbleTotal / teams;
        //the random num to decide which team won
        int winnerNum = (rand.nextInt(marbleTotal) + 1);
        //this num will change to correlate the winning team
        int actualWinner = 0;

        System.out.println("Each team has a " + winPercent + "% chance to win");


        //goes through the amount of teams * i and if its between that amount changes winning team to that num
        for (int i = 1; i <= teams; i++) {
            if (winnerNum > marbleDivide * i && winnerNum < marbleDivide * i + marbleDivide) {
                actualWinner = i;
            }
        }

        //uses actual winner that was changed above to show who won
        switch (actualWinner) {
            case 1:
                System.out.println("Red won!");
                break;
            case 2:
                System.out.println("Orange won!");
                break;
            case 3:
                System.out.println("Yellow won!");
                break;
            case 4:
                System.out.println("Green won!");
                break;
            case 5:
                System.out.println("Blue won!");
                break;
            case 6:
                System.out.println("Purple won!");
                break;
        }

        //if you didnt guess right
        if (actualWinner != marbleColor) {
            System.out.println("The color you selected did not win.");
        } else { // if you did guess right
            System.out.println("The color you selected won!");
        }
    }


    private static void runUntilDone() {
        Random rand = new Random();

        //win % for evenly divided
        double winPercent = ((double) 1 / teams) * 100;
        //num to determine how many marbles each team has
        int marbleDivide = marbleTotal / teams;
        //num to determine winner
        int winnerNum = (rand.nextInt(marbleTotal) + 1);
        System.out.println("Each team has a " + winPercent + "% chance to win");


        //a loop that increases until the search area until you win
        for (int i = 1; i <= teams; i++) {
            if (winnerNum >= marbleDivide * i && winnerNum <= marbleDivide * i + marbleDivide) {
                System.out.println("You won!");
                break;
            } else {
                //lets you know that its running again and sets i back to 1 and selects a new winner num to try and assist in getting a win
                System.out.println("You did not guess correctly... running again");
                winnerNum = (rand.nextInt(marbleTotal) + 1);
                i = 1;
            }
        }

    }


    private static void randomlyDivided() {
        Random rand = new Random();


        int winnerNum = (rand.nextInt(marbleTotal) + 1);
        int actualWinner = 0;
        //win% starting at 0 changes later
        double winPercent = 0;
        //a temp total
        int marbleTotTemp = marbleTotal;
        //array of how close each team is to 0 and closer they are to it they are the winner
        int[] teamWinPerc = new int[teams];
        int n = 0;

        System.out.println("Each team has a varying win percentage");

        //gives out random amounts until either no teams are left to give or there are no marbles left to give
        while (marbleTotTemp != 0 || n != teams) {
            int randNum = rand.nextInt(marbleTotal) + 1;

            if (randNum < marbleTotTemp) {
                marbleTotTemp -= randNum;
                teamWinPerc[n] = randNum;
                n++;
            } else if (n == teams - 1) {
                //if its the last team give it the rest of the marbles
                teamWinPerc[n] = marbleTotTemp;
                marbleTotTemp = 0;
                n++;
            }

        }

        for (int i = 0; i < teams; i++) {
            //subtracts winner num to see which is closest to 0
            teamWinPerc[i] = teamWinPerc[i] - winnerNum;
        }

        for (int i = 0; i < teams; i++) {

            //if the num is negative change to positive
            if (teamWinPerc[i] < 0) {
                int tempNum = -(teamWinPerc[i]);
                teamWinPerc[i] = tempNum;
            }

            //temporary winner
            int tempWinner = teamWinPerc[i];

            //takes the smaller num and sets as the winner
            tempWinner = Math.min(tempWinner, teamWinPerc[i]);

            //changes the actual winner
            actualWinner = tempWinner;

            //the chance the winner had to win
            winPercent = ((double) actualWinner / marbleTotal) * 100;
        }

        int j = 0;
        //finds the actualwinner by comparing to the array stated earlier
        while (actualWinner != teamWinPerc[j]) {
            j++;
        }
        //sets winner num to j
        actualWinner = j;


        //uses the j(or team num) to show who won and their % to win
        switch (actualWinner) {
            case 1:
                System.out.println("Red won with a " + winPercent + "% chance to win.");
                break;
            case 2:
                System.out.println("Orange won with a " + winPercent + "% chance to win.");
                break;
            case 3:
                System.out.println("Yellow won with a " + winPercent + "% chance to win.");
                break;
            case 4:
                System.out.println("Green won with a " + winPercent + "% chance to win.");
                break;
            case 5:
                System.out.println("Blue won with a " + winPercent + "% chance to win.");
                break;
            case 6:
                System.out.println("Purple won with a " + winPercent + "% chance to win.");
                break;
        }
    }

    //star line code used from another member to keep game in similar style
    private static void printStarLine(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
