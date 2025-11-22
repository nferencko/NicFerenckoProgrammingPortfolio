import java.util.Scanner;

/*
 *  * Team One:  Nic Ferencko, Finnegan Allen, Jacob Cruz, Jeremy Soriano
 *  
 * This is the main menu for the probability simulator. Run the main method in this class to start the program.
 * Prints a menu, takes user input, and acts on that input.
 */

public class MainMenu {

	public static void main(String[] args) 
	{
		
		Scanner s = new Scanner(System.in);
		
		
		System.out.println("Welcome to the Probability Simulator! Press <Enter> to play.");
		s.nextLine();
		runMainMenu(s);		
		
		
	}
	
	// Prints the main menu, gets user input, and executes the appropiate simulation
	private static void runMainMenu(Scanner s)
	{
		
		final int MAX_MENU_ITEM = 5; // Amount of menu items. This is the quit choice
		final int LINE_LENGTH = 80; // Amount of stars to print
		
		boolean quit = false;
		
		// until user quits, print the main menu and get user input
		while (quit == false)
		{
			System.out.println();
			
			printStarLine(LINE_LENGTH);
			
			System.out.println("MAIN MENU");
			System.out.println();
			
			System.out.println("(1) Simulation using dice");
			System.out.println("(2) Simulation using cards");
			System.out.println("(3) Simulation using a bag of marbles");
			System.out.println("(4) Simulation of the Monty Hall problem");
			System.out.println("(5) Quit program");
			System.out.println();
			System.out.print("Enter a number between 1 and " + MAX_MENU_ITEM +". --> ");
			
									
			// get user menu choice
			try
			{
				int menuChoice = s.nextInt();
				
				if(menuChoice<1 || menuChoice>MAX_MENU_ITEM)
				{
					throw new IllegalArgumentException("Bad input. Enter a number 1-5 please!");
				}
				
				if(menuChoice == MAX_MENU_ITEM)
				{
					quit = true;
					
					System.out.println();
					System.out.println("Thank you for playing!");
				}
				
				else if(menuChoice == 1) // dice
				{
					// Jeremy is going to do this
					DiceMenu.runDiceMenu(s);
				}
				
				else if(menuChoice == 2) // cards
				{
					CardMenu.runCardMenu(s);
				}
				
				else if(menuChoice == 3) // marbles
				{
					// Jacob is doing this
					//System.out.println("You chose marbles");
					MarbleMenu.runMarbleMenu(s);
				}
				
				else if(menuChoice == 4) //monty hall
				{
					// Finn is doing this
					System.out.println("You chose Monty Hall. This is Finn's part.");
				}
			}
			catch(Exception e) // All bad input is handled the same way
			{
				s.nextLine(); // Clear the bad input
				
				System.out.println("You entered bad input!");
				System.out.println("Please enter a number between 1 and " + MAX_MENU_ITEM+" when prompted. "
						+ "Press any key to continue.");
				s.nextLine();
			}
			
		}

	}
	
	// Print a line of n stars -- used to separate information on the screen
	private static void printStarLine(int n)
	{
		for(int i = 0; i<n; i++)
		{
			System.out.print("*");
		}
		System.out.println();
	}

}
