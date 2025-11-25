//******************************************************************************
// File name:  Hand.cpp
// Author:     Nic Ferencko
// Date:       4/10/2025
// Class:      CS250
// Assignment: Card Games 05
// Purpose:    Implementation of the Hand interface.  
//******************************************************************************

#include "../include/Card.h"
#include "../include/Hand.h"
#include <vector>

//******************************************************************************
// Constructor: Hand
// 
// Description: Creates a Hand object with no cards in the hand
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
Hand::Hand () {


}

//******************************************************************************
// Constructor: Hand
// 
// Description: Copy constructor for Hand
//
// Paramters:   rcHand - A Hand object
//
// Returned:    None
//******************************************************************************
Hand::Hand (const Hand& rcHand) {

  mcCards = rcHand.mcCards;
}

//******************************************************************************
// Destructor: ~Hand
// 
// Description: Default destructor for a Hand object
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
Hand:: ~Hand () {

  // No dynamic memory needs to be released
}

//******************************************************************************
// Function:    addCard
// 
// Description: Add a Card to the end of this Hand
//
// Paramters:   rcCard - A Card object
//
// Returned:    This Hand
//******************************************************************************
Hand Hand::addCard (const Card& rcCard) {

  mcCards.push_back (rcCard);

  return *this;
}

//******************************************************************************
// Function:    addCard
// 
// Description: Add a Card to a specific index in this Hand
//
// Paramters:   rcCard - A Card object
//              index - The index where the Card is placed
//
// Returned:    this Hand
//******************************************************************************
Hand Hand::addCard (const Card& rcCard, size_t index) {

  if (index < 0 || index > mcCards.size ()) {
    throw std::out_of_range ("Hand Exception: Invalid hand index");
  }

  auto cIt = mcCards.begin () + index;

  mcCards.insert (cIt, rcCard);

  return *this;
}

//******************************************************************************
// Function:    removeCard
// 
// Description: Remove a Card from this Hand at a specific index
//
// Paramters:   index - Location of the Card being removed
//
// Returned:    The Card that is removed
//******************************************************************************
Card Hand::removeCard (size_t index) {

  Card cCard ("S", "A");

  if (index < 0 || index >= mcCards.size ()) {
    throw std::out_of_range ("Hand Exception: Invalid hand index");
  }

  cCard = mcCards[index];

  auto cIt = mcCards.begin () + index;

  mcCards.erase (cIt);

  return cCard;

}

//******************************************************************************
// Function:    size
// 
// Description: Get the amount of Cards in this Hand
//
// Paramters:   None
//
// Returned:    The amount of Cards in this Hand
//******************************************************************************
size_t Hand::size () const {

  return mcCards.size ();
}

//******************************************************************************
// Function:    getCard
// 
// Description: Gets a Card at a specified index in this Hand
//
// Paramters:   index - Location of the Card
//
// Returned:    The Card at the specified index in this Hand
//******************************************************************************
Card Hand::getCard (size_t index) const {

  if (index < 0 || index >= size ()) {
    throw std::out_of_range ("Hand Exception: Invalid hand index");
  }

  return mcCards[index];
}

//******************************************************************************
// Function:    clear
// 
// Description: Removed every Card from this Hand
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void Hand::clear () {

  mcCards.clear ();
}

//******************************************************************************
// Function:    operator<<
// 
// Description: outputs a Hand to an output stream
//
// Paramters:   rcOut - The output stream
//              rcHand - The Hand being outputted
//
// Returned:    rcOut
//******************************************************************************
std::ostream& operator<< (std::ostream& rcOut, const Hand& rcHand) {

  for (int i = 0; i < rcHand.size (); i++) {
    if (i != rcHand.size () - 1) {
      rcOut << rcHand.mcCards[i] << " ";
    }
    else {
      rcOut << rcHand.mcCards[i];
    }

  }

  return rcOut;
}