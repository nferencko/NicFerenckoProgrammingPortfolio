//******************************************************************************
// File name:  InBetweenHand.cpp
// Author:     Nic Ferencko
// Date:       4/10/2025
// Class:      CS250
// Assignment: Card Games 05
// Purpose:    Implementation of the InBetweenHand interface.  
//******************************************************************************

#include "../include/Hand.h"
#include "../include/InBetweenHand.h"

//******************************************************************************
// Constructor: Hand
// 
// Description: Creates an InBetweenHand object with no Cards in the hand
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
InBetweenHand::InBetweenHand () : Hand () {


}

//******************************************************************************
// Constructor: InBetweenHand
// 
// Description: Copy Construcotr for InBetweenHand
//
// Paramters:   rcHand - An InBetweenHand object
//
// Returned:    None
//******************************************************************************
InBetweenHand::InBetweenHand (const InBetweenHand& rcHand) : Hand (rcHand) {


}

//******************************************************************************
// Destructor: ~InBetweenHand
// 
// Description: Default destructor for an InBetweenHand object
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
InBetweenHand:: ~InBetweenHand () {

  // No dynamic memory
}

//******************************************************************************
// Function:    addCard
// 
// Description: Add a Card to this InBetweenHand in descending order
//
// Paramters:   rcCard - A Card object
//
// Returned:    This Hand
//******************************************************************************
Hand InBetweenHand::addCard (const Card& rcCard) {

  const int MAX_SIZE = 2;

  Card cTemp ("S", "A");

  if (this->size () >= MAX_SIZE) {
    throw std::runtime_error
    ("InBetweenHand Exception: Hand can not have more than two cards");
  }

  if (this->size () == 0) {
    this->Hand::addCard (rcCard);
  }
  else {
    cTemp = this->getCard (0);
    if (rcCard.getDenominationValue () < cTemp.getDenominationValue ()) {
      this->Hand::addCard (rcCard, 0);
    }
    else {
      this->Hand::addCard (rcCard);
    }
  }

  return *this;
}

//******************************************************************************
// Function:    getDistance
// 
// Description: Gets difference in denomination values of the two Cards in 
//              this InBetweenHand
//
// Paramters:   None
//
// Returned:    The difference in denomination values of the two Cards in this
//              InBetweenHand
//******************************************************************************
size_t InBetweenHand::getDistance () const {

  const int HAND_SIZE = 2;

  int distance;

  if (this->size () != HAND_SIZE) {
    throw std::runtime_error
    ("BetweenHand Exception: Hand must have exactly 2 cards");
  }

  if (this->getCard (0).getDenominationValue () ==
    this->getCard (1).getDenominationValue ()) {
    distance = 0;
  }
  else {
    distance = this->getCard (1).getDenominationValue () -
      this->getCard (0).getDenominationValue ();
  }

  return distance;
}