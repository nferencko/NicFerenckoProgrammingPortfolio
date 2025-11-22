import java.util.Scanner;

/*
 * Team One:  Nic Ferencko, Finnegan Allen, Jacob Cruz, Jeremy Soriano
 * 
 * This is the card menu for the probability simulator. It prints the menu for the different
 * simulations within the CardSimulations, gets the user input and then acts upon
 * that user input by calling the appropriate method from the CardSimulations class.
 * 
 * @author Nic Ferencko
 */

public class CardMenu 
{

	// print the Card Menu, get user input, and act upon theat user input
	public static void runCardMenu(Scanner s)
	{
		
		final int MAX_MENU_ITEM = 5; // Amount of menu items. This item returns user back to main menu
		final int LINE_LENGTH = 80;  // number of stars in the line separator
		
		boolean quit = false;
		
		// Until user chooses to quit, print the card menu
		while (quit == false)
		{
			System.out.println();
			
			printStarLine(LINE_LENGTH);
			
			System.out.println("CARD MENU");
			System.out.println();
			
			System.out.println("(1) Pick any pair");
			System.out.println("(2) Pick a specified pair");
			System.out.println("(3) Getting blackjack");
			System.out.println("(4) Getting a poker flush");
			System.out.println("(5) Return to main menu.");
			System.out.println();
			System.out.print("Enter a number between 1 and " + MAX_MENU_ITEM + ". --> ");
					
			// Get user input and run the appropiate simulation
			try
			{
				int menuChoice = s.nextInt();
				
				if(menuChoice<1 || menuChoice>MAX_MENU_ITEM)
				{
					throw new IllegalArgumentException("Bad input. Enter a number 1-6 please!");
				}
				
				if(menuChoice == MAX_MENU_ITEM)
				{
					quit = true;
					
					System.out.println();
				}
				
				else if(menuChoice == 1)
				{
					CardSimulations.anyPair(s);
				}
				
				else if(menuChoice == 2)
				{
					CardSimulations.selectPair(s);
				}
				
				else if(menuChoice == 3)
				{
					CardSimulations.blackJackSimulation(s);
				}
				
				else if(menuChoice == 4)
				{
					CardSimulations.gettingFlushSimulation(s);
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
	
	
	// prints a line of n stars.  This is used to draw to make the console easier to read.
	private static void printStarLine(int n)
	{
		for(int i = 0; i<n; i++)
		{
			System.out.print("*");
		}
		System.out.println();
	}
	
}
