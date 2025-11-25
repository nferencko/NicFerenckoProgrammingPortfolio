//****************************************************************************** 
// File name:		DeckTests.cpp
// Author:			Nic Ferencko
// Date:				4/16
// Class:				CS 250
// Assignment:  Card Games 05
// Purpose:			This file is written to unit test all functions that are part
//              of the included interface.
// Hours:       5
//******************************************************************************

#include <gtest/gtest.h>
#include "../include/Deck.h"

TEST (DeckTest, constructor) {

  Deck cDeck;
  Deck cCopy (cDeck);
  std::string denom, suit;

  EXPECT_EQ (52, cDeck.size ());


  for (int i = Card::SUITS.size () - 1; i > -1; i--) {
    suit = Card::SUITS[i];
    for (auto cIter = --Card::DENOMINATION_VALUES.end ();
      cIter != --Card::DENOMINATION_VALUES.begin ();
      --cIter) {
      denom = cIter->first;

      EXPECT_EQ (true, Card (suit, denom) == cDeck.dealCard ());
      EXPECT_EQ (true, Card (suit, denom) == cCopy.dealCard ());
    }
  }


}

TEST (DeckTest, clear) {

  Deck cDeck;
  std::ostringstream rcOut;

  cDeck.clear ();

  EXPECT_EQ (0, cDeck.size ());

  cDeck.addCard (Card ("S", "A"));
  cDeck.addCard (Card ("S", "A"));
  cDeck.addCard (Card ("S", "A"));

  // Making sure I run << operator and getCard for Card class
  rcOut << cDeck.dealCard ().getCard ();

  EXPECT_EQ ("AS", rcOut.str ());

  cDeck.clear ();

  EXPECT_EQ (0, cDeck.size ());
}

TEST (DeckTest, shuffle) {

  const int SEED = 3;

  srand (SEED);

  Deck cDeck;
  Card cCard ("D", "2"); // Temp Card

  cDeck.shuffle ();

  cCard = cDeck.dealCard ();

  EXPECT_EQ ("6", cCard.getDenomination ());
  EXPECT_EQ ("C", cCard.getSuit ());

  cDeck.clear ();

  srand (3);

  cDeck = Deck ();

  cDeck.shuffle ();

  cCard = cDeck.dealCard ();

  EXPECT_EQ ("6", cCard.getDenomination ());
  EXPECT_EQ ("C", cCard.getSuit ());

  cCard = cDeck.dealCard ();

  EXPECT_EQ ("A", cCard.getDenomination ());
  EXPECT_EQ ("D", cCard.getSuit ());

}

TEST (DeckTest, dealCard) {

  Deck cDeck;
  std::string denom, suit;
  int size = 52;

  for (int i = Card::SUITS.size () - 1; i > -1; i--) {
    suit = Card::SUITS[i];
    for (auto cIter = --Card::DENOMINATION_VALUES.end ();
      cIter != --Card::DENOMINATION_VALUES.begin ();
      --cIter) {
      denom = cIter->first;

      EXPECT_EQ (true, Card (suit, denom) == cDeck.dealCard ());
      size--;
      EXPECT_EQ (size, cDeck.size ());
    }
  }
}

TEST (DeckTest, dealCardException) {

  Deck cDeck;

  cDeck.clear ();

  EXPECT_THROW (cDeck.dealCard (), std::out_of_range);
}

TEST (DeckTest, addCard) {

  Deck cDeck;

  cDeck.addCard (Card ("S", "A"));

  EXPECT_EQ (53, cDeck.size ());
  EXPECT_EQ (Card ("S", "A"), cDeck.dealCard ());
  EXPECT_EQ (52, cDeck.size ());

  cDeck.clear ();

  cDeck.addCard (Card ("D", "J"));

  EXPECT_EQ (1, cDeck.size ());
  EXPECT_EQ (Card ("D", "J"), cDeck.dealCard ());
  EXPECT_EQ (0, cDeck.size ());
}

TEST (DeckTest, size) {

  Deck cDeck;

  EXPECT_EQ (52, cDeck.size ());

  for (int i = 1; i < 6; i++) {
    cDeck.addCard (Card ("S", "A"));
    EXPECT_EQ (52 + i, cDeck.size ());
  }

  for (int i = 1; i < 11; i++) {
    cDeck.dealCard ();
    EXPECT_EQ (57 - i, cDeck.size ());
  }

  cDeck.clear ();
  EXPECT_EQ (0, cDeck.size ());
}

TEST (DeckTest, write) {

  const int SEED = 3;

  Deck cDeck;
  Card cCard ("S", "A");

  srand (SEED);

  std::ostringstream rcCard;
  std::ostringstream rcOut;
  std::ostringstream rcOut2;

  //Print single Card
  rcCard << cCard;
  EXPECT_EQ ("AS", rcCard.str ());

  // Test Regular Deck
  rcOut << cDeck;
  EXPECT_EQ ("10H 2H 3H 4H 5H 6H 7H 8H 9H AH JH KH QH 10C 2C 3C 4C 5C 6C 7C 8C 9C AC JC KC QC 10D 2D 3D 4D 5D 6D 7D 8D 9D AD JD KD QD 10S 2S 3S 4S 5S 6S 7S 8S 9S AS JS KS QS", rcOut.str ());

  // Test shuffled Deck
  cDeck.shuffle ();

  rcOut2 << cDeck;
  EXPECT_EQ ("QS JS 10D 8D KD 3D 4C 9S 7D 2D AS 5S 10H KH 3C QC 2H 6D 3S 4D AC 7C 2C 9H 5C JH JD 4S 7H JC 7S QD 8H 10C 10S 9D AH 5D 9C 4H 5H 8S 6S 3H 2S QH 8C 6H KC KS AD 6C", rcOut2.str ());
}