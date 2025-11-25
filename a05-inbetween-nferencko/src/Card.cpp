//******************************************************************************
// File name:  Card.cpp
// Author:     Nic Ferencko
// Date:       4/10/2025
// Class:      CS250
// Assignment: Card Games 05
// Purpose:    Implementation of the Card interface.  
//******************************************************************************

#include "../include/Card.h"
#include<map>
#include <iostream>

// vector containing valid card suits
const std::vector<std::string> Card::SUITS = {
  "H", "C", "D", "S"
};

// map card denomination to card value
const std::map<std::string, int> Card::DENOMINATION_VALUES = {
  {"A", 1},{"2", 2},{"3", 3},{"4", 4},{"5", 5},{"6", 6}, {"7", 7},
  {"8", 8}, {"9", 9}, {"10", 10}, {"J", 11}, {"Q", 12}, {"K", 13}
};

//******************************************************************************
// Function:    setSuit
// 
// Description: Set the suit of a Card object
//
// Paramters:   rcSuit - must be a value contained in vector SUITS
//
// Returned:    None
//******************************************************************************
void Card::setSuit (const std::string& rcSuit) {

  bool isIn = false;

  for (std::string suit : SUITS) {
    if (rcSuit == suit) {
      isIn = true;
    }
  }

  if (!isIn) {
    throw std::invalid_argument ("Card Exception: Invalid suit");
  }
  else {
    mcSuit = rcSuit;
  }
}

//******************************************************************************
// Function:    setDenomination
// 
// Description: Set the Denomination of a Card object
//
// Paramters:   rcDenomination -  must be a key value in the map 
//                                DENOMINATION_VALUES
//
// Returned:    None
//******************************************************************************
void Card::setDenomination (const std::string& rcDenomination) {

  bool isValid = false;


  if (DENOMINATION_VALUES.contains (rcDenomination)) {
    isValid = true;
  }

  if (!isValid) {
    throw std::invalid_argument ("Card Exception: Invalid denomination");
  }
  else {
    mcDenomination = rcDenomination;
  }

}

//******************************************************************************
// Constructor: Card
// 
// Description: Create a Card objet
//
// Paramters:   rcSuit - suit of Card
//              rcDenomination  - denomination of Card
//
// Returned:    None
//******************************************************************************
Card::Card (const std::string& rcSuit, const std::string& rcDenomination) {

  setSuit (rcSuit);
  setDenomination (rcDenomination);
}

//******************************************************************************
// Constructor: Card
// 
// Description: Copy Constructor
//
// Paramters:   rcCard - Card object to be copied
//
// Returned:    None
//******************************************************************************
Card::Card (const Card& rcCard) {

  setSuit (rcCard.mcSuit);
  setDenomination (rcCard.mcDenomination);
}

//******************************************************************************
// Destructor: ~Card
// 
// Description: Default destructor for a Card object
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
Card:: ~Card () {
  // No dynamic memory allocated
}

//******************************************************************************
// Function:    getSuit
// 
// Description: Get the suit of this Card object
//
// Paramters:   None
//
// Returned:    The suit of this Card object
//******************************************************************************
std::string Card::getSuit () const {

  return mcSuit;
}

//******************************************************************************
// Function:    getDenomination
// 
// Description: Get the denomination of this Card object
//
// Paramters:   None
//
// Returned:    The denomination of this Card object
//******************************************************************************
std::string Card::getDenomination () const {

  return mcDenomination;
}

//******************************************************************************
// Function:    getDenominationValue
// 
// Description: Get the denomination value of this Card object
//
// Paramters:   None
//
// Returned:    The denomination value of this Card object
//******************************************************************************
size_t Card::getDenominationValue () const {

  return DENOMINATION_VALUES.at (mcDenomination);
}

//******************************************************************************
// Function:    getCard
// 
// Description: Get this Card object
//
// Paramters:   None
//
// Returned:    This Card object
//******************************************************************************
Card Card::getCard () const {

  return Card (this->getSuit (), this->getDenomination ());

}

//******************************************************************************
// Function:    operator==
// 
// Description: Checks to see if two Card objects are equal
//
// Paramters:   rcCard - A Card object
//
// Returned:    True if both Cards have the same suit and denomination,
//              false otherwise
//******************************************************************************
bool Card:: operator== (const Card& rcCard) const {

  return mcSuit == rcCard.mcSuit && mcDenomination == rcCard.mcDenomination;
}

//******************************************************************************
// Function:    operator<<
// 
// Description: Writes a Card to an output stream
//
// Paramters:   rcOutStream - The ouput stream
//              rcCard - A Card object
//
// Returned:    rcOutStream
//******************************************************************************
std::ostream& operator<< (std::ostream& rcOutStream,
  const Card& rcCard) {

  rcOutStream << rcCard.getDenomination () << rcCard.getSuit ();

  return rcOutStream;
}