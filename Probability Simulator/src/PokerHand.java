import java.util.*;

/*
 *  Team One:  Nic Ferencko, Finnegan Allen, Jacob Cruz, Jeremy Soriano
 * 
 * Skeleton implementation of a poker hand. Just enough functionality to run the simulation in probability
 * simulator. A valid poker hand is a collection of 5 cards.
 * 
 * @author Nic Ferencko
 */

public class PokerHand 
{
	
	private ArrayList<Card> hand;
	
	 /**
	  * The constructor creates an empty poker hand
	  */
	public PokerHand()
	{
		this.hand = new ArrayList<Card>();
	}
	
	// Returns true if this hand has 5 cards. False otherwise
	public boolean isValidHand()
	{
		return this.hand.size() == 5;
	}
	
	// Adds a card to this hand
	public void addCard(Card c)
	{
		this.hand.add(c);
		Collections.sort(this.hand);
	}
	
	// Returns true if this hand is a flush
	public boolean isFlush()
	{
		if(!isValidHand())
		{
			return false;
		}
		
		// We know that there are five cards in the deck
		// We will know if it's a flush if all the cards have the same suit
		
		// Get suit of first card
		
		int suit = hand.get(0).getSuit();
		
		// Iterate through the rest of the cards and compare them to the first suit
		// If any card doesn't match, return false. 
		
		for(int i = 1; i < hand.size(); i++)
		{
			if(suit != hand.get(i).getSuit())
			{
				return false;
			}
		}
		return true; // If we get here we all the cards must have the same suit
	}
	
	public String toString()
	{
		String s = "";
		for(int i = 0; i<hand.size(); i++)
		{
			s = s + hand.get(i) + " ";
		}
		return s;
	}
	
	
	// Tester method for PokerHand class.
	public static void main(String [] args)
	{
		PokerHand h = new PokerHand();
		
		h.addCard(new Card(5, Card.HEARTS));
		h.addCard(new Card(9, Card.HEARTS));
		h.addCard(new Card(12, Card.HEARTS));
		h.addCard(new Card(11, Card.HEARTS));
		h.addCard(new Card(8, Card.CLUBS));
		
		System.out.println(h.isFlush());
		System.out.println(h);
	}

}
