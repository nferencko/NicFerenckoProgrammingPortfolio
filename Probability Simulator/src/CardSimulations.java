import java.util.Scanner;

/*
 * Team One:  Nic Ferencko, Finnegan Allen, Jacob Cruz, Jeremy Soriano
 * 
 * This is the card simulations for the probability simulator. It contains several static methods. Each method runs
 * a different simulation.
 * 
 * @author Nic Ferencko
 */

public class CardSimulations 
{
	final private static int MAX_NUM_TRIALS = 1_000_000; // Max # of trials user can run
	
	// Helper method that gets user input for the number of trials for the simulation
	private static int getNumberOfTrials(Scanner s) 
	{
		boolean badData = true;
		
		while (badData == true) 
		{
			try 
			{
				int userInput = s.nextInt();
				if (userInput < 0 || userInput > MAX_NUM_TRIALS) 
				{
					throw new IllegalArgumentException("Input out of bounds!");
				}
				
				badData = false;
				
				return userInput;
			}

			catch (Exception e) 
			{
				System.out.println("You must enter an integer between 1 - " + MAX_NUM_TRIALS + ". Please try again --> ");
			}
		}
		
		return 10; // Never will get here
	}
	
	
	// Helper method -- get a Card choice from the user
	private static int getCardChoice(Scanner s)
	{
		boolean badData = true;
		
		while (badData == true) 
		{
			try 
			{
				int userInput = s.nextInt();
				if (userInput < Card.ACE || userInput > Card.KING) 
				{
					throw new IllegalArgumentException("Input out of bounds!");
				}
				
				badData = false;
				
				return userInput;
			}

			catch (Exception e) 
			{
				System.out.println("You must enter an integer between 1 - 13. Please try again --> ");
			}
		}
		
		return 10; // Never will get here
	}

	// In this simulation the method will take how many trials the user wants to run.  In each
	// trial, the user will be dealt 2 card.  The method will display the number and percentage
	// of times the user was dealt a pair along with the theoretical results. 
	public static void anyPair(Scanner s) 
	{
		int numberOfTrials = 0;
		DeckOfCards d = new DeckOfCards();
		d.shuffle();
		int counter = 0;
		
		// Get numberOfTrials from the user
		System.out.println();
		System.out.println("In this simulation you will draw two cards to see how often you pick a pair.");
		System.out.println("How many trials do you want to run? Enter a number between 1 - " + MAX_NUM_TRIALS + " or 0 to quit --> ");
		
		numberOfTrials = getNumberOfTrials(s);
		
		// If user chooses to quit, exit method immediately
		if(numberOfTrials == 0)
		{
			return;
		}

		// User did not quit		
		for (int i = 0; i < numberOfTrials; i++) 
		{

			d.resetDeck();
			d.shuffle();
			Card a = d.takeCard();
			Card b = d.takeCard();

			if (a.getRank() == b.getRank()) 
			{
				counter++;
			}
		}
		
		// Compute and display results
		double experimentalP = (1.0*counter/numberOfTrials)*100;
		
		System.out.println();
		System.out.println("*You got a pair " + counter + " times out of " + numberOfTrials + " which is " 
		+ String.format("%,.1f", experimentalP) + "%!");
		System.out.println("*The theoretical probability for this simulation is 1/17 which is about 5.9%.");
		System.out.println();
		System.out.print("Press <ENTER> to continue. ");
		
		s.nextLine();  // remove the carriage that is still in buffer
		s.nextLine();
		
	}
	
	
	// In this simulation the method will take how many trials the user wants to run.  The user 
	// will also select a particular card from a deck of cards.  Then, the simulations will
	// be run and the number and percentage of how many times the user was dealt a pair of cards
	// of the type that they specified will be displayed on the screen along with the theoretical results.
	public static void selectPair(Scanner s)
	{
		int numberOfTrials = 0;// number of trial's to run
		int selectedPair = 0; // user's card choice
		DeckOfCards d = new DeckOfCards();
		d.shuffle();
		int counter = 0;
		
		// Get numberOfTrials from the user
		System.out.println();
		System.out.println("In this simulation you will pick a card and then see how often you pick a pair of that type.");
		System.out.println("How many trials do you want to run? Enter a number between 1 - " + MAX_NUM_TRIALS + " or 0 to quit --> ");
		
		numberOfTrials = getNumberOfTrials(s);
		
		// Get slectPair from the user
		System.out.println("Which card would you like to pick?");
		System.out.println("Enter a number between 1 and 13.  For Ace = 1, Jack = 11, Queen = 12, King = 13.");
		
		selectedPair = getCardChoice(s);
		
		// If user chooses to quit, exit method immediately
		if(numberOfTrials == 0)
		{
			return;
		}

		// User did not quit	
		for (int i = 0; i < numberOfTrials; i++) 
		{

			d.resetDeck();
			d.shuffle();
			Card a = d.takeCard();
			Card b = d.takeCard();

			if (a.getRank() == selectedPair && b.getRank() == selectedPair) 
			{
				counter++;
			}
		}
		
		// Compute and display results
		double experimentalP = (1.0*counter/numberOfTrials)*100;
		
		System.out.println();
		System.out.println("*You got your specified pair " + counter + " times out of " + numberOfTrials + " which is " 
		+ String.format("%,.2f", experimentalP) + "%!");
		System.out.println("*The theoretical probability for this simulation is 1/221 which is about .45%.");
		System.out.println();
		System.out.print("Press <ENTER> to continue. ");
		
		s.nextLine();  // remove the carriage that is still in buffer
		s.nextLine();
	}
	
	
	// In this simulation the method will take how many trials the user wants to run.  In each trial, the user will
	// be dealt 2 cards.  The method will then display the number and percentage
	// of times the user was dealt blackjack.
	public static void blackJackSimulation(Scanner s)
	{
		int numberOfTrials = 0;
		DeckOfCards d = new DeckOfCards();
		d.shuffle();
		int counter = 0;
		
		// Get numberOfTrials from the user
		System.out.println();
		System.out.println("In this simulation you will be dealt two cards to see how often you are dealt Black Jack.");
		System.out.println("How many trials do you want to run? Enter a number between 1 - " + MAX_NUM_TRIALS + " or 0 to quit --> ");
		
		numberOfTrials = getNumberOfTrials(s);
		
		// If user chooses to quit, exit method immediately
		if(numberOfTrials == 0)
		{
			return;
		}

		// User did not quit
		
		for (int i = 0; i < numberOfTrials; i++) 
		{

			d.resetDeck();
			d.shuffle();
			BlackJackHand hand = new BlackJackHand();
			
			hand.add( d.takeCard() );
			hand.add( d.takeCard() );

			if (hand.isBlackJack()) 
			{
				counter++;
			}
		}
		
		// Compute and display results
		double experimentalP = (1.0*counter/numberOfTrials)*100;
		
		System.out.println();
		System.out.println("*You were dealt BlackJack " + counter + " times out of " + numberOfTrials + " which is " 
		+ String.format("%,.1f", experimentalP) + "%!");
		System.out.println("*According to SnapChat AI, the theoretical probablity is  about 4.8%.  What do you think -- "
				+ "is SnapChat AI correct?");
		System.out.println();
		System.out.print("Press <ENTER> to continue. ");
		
		s.nextLine();  // remove the carriage that is still in buffer
		s.nextLine();
	}
	
	
	// In this simulation the method will take how many trials the user wants to run.  In each trial
	// the user will be dealt 5 cards.  The method will display the number and percentage
	// of times the user was dealt a flush.
	public static void gettingFlushSimulation(Scanner s)
	{
		int numberOfTrials = 0;
		DeckOfCards d = new DeckOfCards();
		d.shuffle();
		int counter = 0;
		
		// Get numberOfTrials from the user
		System.out.println();
		System.out.println("In this simulation you will be dealt five cards to see how often you are dealt a flush.");
		System.out.println("How many trials do you want to run? Enter a number between 1 - " + MAX_NUM_TRIALS + " or 0 to quit --> ");
		
		numberOfTrials = getNumberOfTrials(s);
		
		// If user chooses to quit, exit method immediately
		if(numberOfTrials == 0)
		{
			return;
		}

		// User did not quit
		
		for (int i = 0; i < numberOfTrials; i++) 
		{

			d.resetDeck();
			d.shuffle();
			PokerHand hand = new PokerHand();
			
			for(int j = 0; j < 5; j++ )
			{
				hand.addCard( d.takeCard() );
			}

			if (hand.isFlush()) 
			{
				counter++;
			}
		}
		
		// Compute and display results
		double experimentalP = (1.0*counter/numberOfTrials)*100;
		
		System.out.println();
		System.out.println("*You were dealt a Flush " + counter + " times out of " + numberOfTrials + " which is " 
		+ String.format("%,.3f", experimentalP) + "%!");
		System.out.println("*According to the internet, the theoretical probablity is  about 0.198%.  What do you think -- "
				+ "is the internet correct?");
		System.out.println();
		System.out.print("Press <ENTER> to continue. ");
		
		s.nextLine();  // remove the carriage that is still in buffer
		s.nextLine();
	}
}
