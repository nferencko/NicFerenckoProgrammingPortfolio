import java.util.Scanner;

/**
 * 
 * Running the main method will play the game of BlackJack.
 *
 */

public class Actions {
	private static final int bj = 21; // score user should be aiming for but not go over
	private static final int dealerMin = 17; // dealer's minimum score before they must stop hitting

	public static void main(String[] args) {
		
		// Create deck of cards and shuffle it
		DeckOfCards deck = new DeckOfCards();
		deck.shuffle();						
											
											
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Blackjack!");

		double balance = 100;  // Player bank balance
		boolean playAgain = true;

		while (playAgain) {
			
			deck.shuffle();  // reset the deck and shuffle
			double bet = placeBet(scanner, balance);  // get player's bet
			
			
			//  Deal 2 cards to dealer, 2 to player.  Show each person's hand.
			
			BlackJackHand playerHand = new BlackJackHand();
			BlackJackHand dealerHand = new BlackJackHand();
			
			playerHand.add( deck.takeCard() );
			dealerHand.add( deck.takeCard() );
			playerHand.add( deck.takeCard() );
			dealerHand.add( deck.takeCard() );
			
			// Print game table with dealer's first card hidden
			// the printInGameTable will print the game table with the dealer's first
			// card showing.
			
			printGameTable(dealerHand, playerHand, bet, balance);
			
			//Check Different Game Scenarios and take appropriate action
			if( dealerHand.isBlackJack() && playerHand.isBlackJack())
			{
				String msg = "It's a Push! You keep your bet.";
				printInGameTable(dealerHand, playerHand, msg, balance);
			}
			else if( dealerHand.isBlackJack() && !playerHand.isBlackJack())
			{
				
				String msg = "Dealer has BlackJack. Sorry you lose.";
				balance = balance - bet;
				printInGameTable(dealerHand, playerHand, msg, balance);
			}
			else if( playerHand.isBlackJack() && !dealerHand.isBlackJack())
			{
				
				String msg = "YOU HAVE BLACKJACK!!! Congrats, you win $" + 1.5*bet;
				balance = balance + 1.5*bet;
				printInGameTable(dealerHand, playerHand, msg, balance);
			}
			
			else
			{
				// Neither player has BlackJack.  First, player makes their move.
				playerMove(dealerHand, playerHand, deck, scanner);
				
				//  If player busted, game over.  Otherwise, dealer makes their move.
				if(playerHand.getValue()>bj)
				{
					balance = balance - bet;
					String msg = "You Busted! Sorry you lose.";
					
					printInGameTable(dealerHand, playerHand, msg, balance);					
				}
				else 
				{
					// Dealer hits until their score is greater than 16
					while(dealerHand.getValue()<dealerMin)
					{
						dealerHand.add(deck.takeCard());
					}
					
					// check different possibilities after dealer done with move
					if(dealerHand.getValue()>bj)
					{
						balance = balance + bet;
						String msg = "Dealer Busted! YOU WIN!!!";
						
						printInGameTable(dealerHand, playerHand, msg, balance);
					}
					else if(dealerHand.getValue()>playerHand.getValue())
					{
						balance = balance - bet;
						String msg = "You lose. Better luck next time.";
						
						printInGameTable(dealerHand, playerHand, msg, balance);
					}
					else if(dealerHand.getValue()<playerHand.getValue())
					{
						balance = balance + bet;
						String msg = "YOU WIN!!!";
						
						printInGameTable(dealerHand, playerHand, msg, balance);
					}
					else
					{
						String msg = "It's a Push. You keep your money.";
						
						printInGameTable(dealerHand, playerHand, msg, balance);
					}
				}
			}
			

			// Current round is over.  If player has no money, exit game.
			// If player has money, see if they want to play another round.
			System.out.println();
			System.out.println("Your current balance: " + balance);

			if (balance <= 0) {
				System.out.println("Well, you wasted all your bad luck here. Time to hit the casino!");
				break;
			}

			System.out.print("Do you want to play again? (Y)es/(N)o): ");
			String playAgainInput = "";
			
			while( !playAgainInput.equalsIgnoreCase("Y") && !playAgainInput.equalsIgnoreCase("N"))
			{
				playAgainInput = scanner.nextLine();
				if(playAgainInput.equalsIgnoreCase("Y"))
				{
					playAgain = true;
					System.out.println();
				}
				else if(playAgainInput.equalsIgnoreCase("N"))
				{
					playAgain = false;
					System.out.println();
					System.out.println("Hope you fun. Time to test your luck at the casino!");
				}
				else
				{
					System.out.println("Bad input. Please enter \"Y\" for Yes and \"N\" for No.");
				}
			}			
		}
	}

	/*
	 * user prompt to input bet in order to begin the game
	 */
	private static double placeBet(Scanner scanner, double balance) {
		double bet = 0;
		boolean validBet = false;

		while (!validBet) {
			System.out.println("Place your bet (current balance: " + balance + "): ");
			if(scanner.hasNextDouble()) {
			bet = scanner.nextDouble();
			scanner.nextLine();

				if (bet > balance) {
				System.out.println(
						"While I would love to take your money, I can't take money you don't have. Place a lower bet.");
				} else {
				validBet = true;
				}
			}else {
			System.out.println("I can only read numbers");
			scanner.nextLine();
			}
		}
		return bet;
	}

	
    /*
     * Prints the game table with the dealer's first card hidden
     */
	private static void printGameTable(BlackJackHand dealerHand, BlackJackHand playerHand,
			double currentBet, double currentBalance)
	{
		int starCount = 60;
		
		for(int i = 0; i<starCount; i++)
		{
			System.out.print("*");
		}
		System.out.println();
		
		System.out.println("Dealer Hand: "+ dealerHand.dealerView());
		System.out.println("Your Hand: "+ playerHand + ", Score = " + playerHand.getValue());
		System.out.println("Current Bet: "+ currentBet+ ", Current Balance: "+ currentBalance);
		
		for(int i = 0; i<starCount; i++)
		{
			System.out.print("*");
		}
		System.out.println();
	}
	
	/*
	 * Prints msg and then prints the game table with the dealer's first card showing
	 */
	private static void printInGameTable(BlackJackHand dealerHand, BlackJackHand playerHand,
			String msg, double currentBalance)
	{
		int starCount = 60;
		
		for(int i = 0; i<starCount; i++)
		{
			System.out.print("*");
		}	
		System.out.println();
		
		System.out.println(msg);
		System.out.println();
		
		System.out.println("Dealer Hand: " + dealerHand + ", Score = " + dealerHand.getValue());
		System.out.println("Your Hand: "+ playerHand + ", Score = " + playerHand.getValue());
		System.out.println("Current Balance: "+ currentBalance);
		
		for(int i = 0; i<starCount; i++)
		{
			System.out.print("*");
		}	
		System.out.println();
	}
	
	/*
	 * Method that handles player hitting or standing
	 */
	private static void playerMove(BlackJackHand dealerHand, BlackJackHand playerHand,
			DeckOfCards deck, Scanner scanner)
	{
		boolean hit = true; // Gets set to false if player chooses to stand
		String action = "";
		
		System.out.println("YOUR MOVE: ");
		
		while(hit == true && playerHand.getValue() < bj)
		{
			System.out.println("Dealer Hand: " + dealerHand.dealerView());
			System.out.println("Your Hand: " + playerHand + ", Score = " + playerHand.getValue());
			System.out.print("What do you want to do, (H)it or (S)tand: ");
			
			action = scanner.nextLine();
			
			if(action.equalsIgnoreCase("h"))
			{
				// Do stuff
				playerHand.add(deck.takeCard());
			}
			else if(action.equalsIgnoreCase("s"))
			{
				// Do stuff
				hit = false;
			}
			else
			{
				// Bad input, ignore input and print a message telling them to try again
				System.out.println();
				System.out.println("Bad input. Please enter \"H\" for hit or \"S\" for stand.");
			}
			
			System.out.println();
			
		}
	}

}