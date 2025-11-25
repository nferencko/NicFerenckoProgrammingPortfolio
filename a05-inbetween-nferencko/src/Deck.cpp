//******************************************************************************
// File name:  Deck.cpp
// Author:     Nic Ferencko
// Date:       4/10/2025
// Class:      CS250
// Assignment: Card Games 05
// Purpose:    Implementation of the Deck interface.  
//******************************************************************************

#include "../include/Deck.h"
#include "../include/Card.h"
#include <random>
#include <iostream>

//******************************************************************************
// Constructor: Deck
// 
// Description: Create a regular 52 Card Deck objet
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
Deck::Deck () {

  for (std::string suit : Card::SUITS) {
    for (auto denom : Card::DENOMINATION_VALUES) {
      mcCards.push_back (Card (suit, denom.first));
    }
  }
}

//******************************************************************************
// Constructor: Deck
// 
// Description: Copy constructor for Deck
//
// Paramters:   rcDeck - a Deck object
//
// Returned:    None
//******************************************************************************
Deck::Deck (const Deck& rcDeck) {

  mcCards = rcDeck.mcCards;
}

//******************************************************************************
// Destructor: ~Deck
// 
// Description: Default destructor for a Deck object
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
Deck:: ~Deck () {

}

//******************************************************************************
// Function:    addCard
// 
// Description: Add a Card to the end of this Deck
//
// Paramters:   rcCard - A Card object
//
// Returned:    None
//******************************************************************************
void Deck::addCard (const Card& rcCard) {

  mcCards.push_back (rcCard);
}

//******************************************************************************
// Function:    dealCard
// 
// Description: Deals a Card from the back of this Deck
//
// Paramters:   None
//
// Returned:    The Card removed from the back of this Deck
//******************************************************************************
Card Deck::dealCard () {

  if (size () == 0) {
    throw std::out_of_range ("Deck Exception: No cards in deck");
  }

  Card cCard = mcCards[mcCards.size () - 1];
  mcCards.pop_back ();

  return cCard;
}

//******************************************************************************
// Function:    size
// 
// Description: Get the number of Cards in this Deck
//
// Paramters:   None
//
// Returned:    The number of Cards in this Deck
//******************************************************************************
size_t Deck::size () const {
  return mcCards.size ();
}

//******************************************************************************
// Function:    shuffle
// 
// Description: Shuffles a Deck
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void Deck::shuffle () {

  int cardIndex = size () - 1;
  int randomIndex;
  Card cTemp = mcCards[cardIndex];


  for (int i = cardIndex; i > 0; i--) {

    randomIndex = rand () % i;

    cTemp = mcCards[i];

    mcCards[i] = mcCards[randomIndex];
    mcCards[randomIndex] = cTemp;


  }
}

//******************************************************************************
// Function:    operator<<
// 
// Description: Outputs a Deck to an output stream
//
// Paramters:   rcOutStream - An output stream
//              rcDeck - The Deck being outputted
//
// Returned:    rcOutStream
//******************************************************************************
std::ostream& operator<< (std::ostream& rcOutStream, const Deck& rcDeck) {


  for (int i = 0; i < rcDeck.size (); i++) {
    if (i != rcDeck.size () - 1) {
      rcOutStream << rcDeck.mcCards[i] << " ";
    }
    else {
      rcOutStream << rcDeck.mcCards[i];
    }

  }

  return rcOutStream;
}

//******************************************************************************
// Function:    clear
// 
// Description: Empties this Deck
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void Deck::clear () {

  mcCards.clear ();
}

