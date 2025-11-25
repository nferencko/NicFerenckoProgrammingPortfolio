//******************************************************************************
// File name:  InBetweenPlayer.cpp
// Author:     Nic Ferencko
// Date:       4/23/2025
// Class:      CS250
// Assignment: Card Games 05
// Purpose:    Implementation of the InBetweenPlayer interface (abstract class).  
//******************************************************************************

#include "../include/InBetweenPlayer.h"

// default players are given the name defaultName with counter appended to it.
// counter is incremented each time a default InBetweenPlayer is created.

std::string InBetweenPlayer::defaultName = "Player";
int InBetweenPlayer::counter = 0;


//******************************************************************************
// Constructor: InBetweenPlayer
// 
// Description: Creates a default InBetweenPlayer object
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************

InBetweenPlayer::InBetweenPlayer () {

  std::string cName = defaultName + "_" + std::to_string (counter);

  setName (cName);
  setBank (Bank ());
  setHand (InBetweenHand ());

  counter++;

}

//******************************************************************************
// Constructor: InBetweenPlayer
// 
// Description: Creates an InBetweenPlayer object
//
// Paramters:   rcName - name of player created
//              rcBank - player object's Bank
//              rcHand - player object's InBetweenHand
//
// Returned:    None
//******************************************************************************
InBetweenPlayer::InBetweenPlayer (const std::string& rcName, const Bank& rcBank,
  const InBetweenHand& rcHand) {

  setName (rcName);
  setBank (rcBank);
  setHand (rcHand);

}

//******************************************************************************
// Constructor: InBetweenPlayer
// 
// Description: Copy Constructor for InBetweenPlayer
//
// Paramters:   rcInBetweenPlayer - InBetweenPlayer to be copied
//
// Returned:    None
//******************************************************************************
InBetweenPlayer::InBetweenPlayer (const InBetweenPlayer& rcInBetweenPlayer) {

  setName (rcInBetweenPlayer.mcName);
  setBank (rcInBetweenPlayer.mcBank);
  setHand (rcInBetweenPlayer.mcInBetweenHand);

}

//******************************************************************************
// Destructor:  InBetweenPlayer
// 
// Description: Default destructor for InBetweenPlayer
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
InBetweenPlayer:: ~InBetweenPlayer () {
  // No dynamic memory
}

//******************************************************************************
// Function:    setName
// 
// Description: set the name of this InBetweenPlayer
//
// Paramters:   rcName - Name of the player. Can't be empty string
//
// Returned:    none
//******************************************************************************
void InBetweenPlayer::setName (const std::string& rcName) {

  if (rcName == "") {
    throw std::invalid_argument
    ("InBetweenPlayer Exception::setName: name is empty");
  }
  else {
    mcName = rcName;
  }
}

//******************************************************************************
// Function:    setBank
// 
// Description: assign a Bank to this InBetweenPlayer
//
// Paramters:   rcBank - a Bank object
//
// Returned:    none
//******************************************************************************
void InBetweenPlayer::setBank (const Bank& rcBank) {

  mcBank = rcBank;
}

//******************************************************************************
// Function:    setHand
// 
// Description: assign an InBetweenHand to this InBetweenPlayer
//
// Paramters:   rcHand - an InBetweenHand object
//
// Returned:    none
//******************************************************************************
void InBetweenPlayer::setHand (const InBetweenHand& rcHand) {

  mcInBetweenHand = rcHand;

}

//******************************************************************************
// Function:    getName
// 
// Description: get the name of this InBetweenPlayer
//
// Paramters:   none
//
// Returned:    this InBetweenPlayer's name
//******************************************************************************
std::string InBetweenPlayer::getName () const {
  return mcName;
}

//******************************************************************************
// Function:    getBank
// 
// Description: get the Bank of this InBetweenPlayer
//
// Paramters:   none
//
// Returned:    this InBetweenPlayer's Bank
//******************************************************************************
Bank InBetweenPlayer::getBank () const {
  return mcBank;
}

//******************************************************************************
// Function:    getInBetweenHand
// 
// Description: get the InBetweenHand for this InBetweenPlayer
//
// Paramters:   none
//
// Returned:    this InBetweenPlayer's InBetweenHand
//******************************************************************************
InBetweenHand InBetweenPlayer::getInBetweenHand () const {
  return mcInBetweenHand;
}

//******************************************************************************
// Function:    addToBank
// 
// Description: add money to the Bank and return the Bank
//
// Paramters:   amount - value to be added to the Bank
//
// Returned:    this InBetweenPlayer's Bank
//******************************************************************************
Bank InBetweenPlayer::addToBank (int amount) {

  mcBank.add (amount);

  return this->mcBank;
}

//******************************************************************************
// Function:    subtractFromBank
// 
// Description: remove money from the Bank and return the Bank
//
// Paramters:   amount - value to be subtracted from the Bank
//
// Returned:    this InBetweenPlayer's Bank
//******************************************************************************
Bank InBetweenPlayer::subtractFromBank (int amount) {

  mcBank.subtract (amount);

  return this->mcBank;
}

//******************************************************************************
// Function:    addToInBetweenHand
// 
// Description: add a Card to the Player's hand and return the hand
//
// Paramters:   rcCard - Card to be added
//
// Returned:    this InBetweenPlayer's InBetweenHand
//******************************************************************************
InBetweenHand InBetweenPlayer::addToInBetweenHand (const Card& rcCard) {

  mcInBetweenHand.addCard (rcCard);

  return this->mcInBetweenHand;
}

//******************************************************************************
// Function:    clearInBetweenHand
// 
// Description: clear this InBetweenPlayer's InBetweenHand
//
// Paramters:   none
//
// Returned:    none
//******************************************************************************
void InBetweenPlayer::clearInBetweenHand () {
  mcInBetweenHand.clear ();
}

//******************************************************************************
// Function:    clearBank
// 
// Description: clear this InBetweenPlayer's Bank
//
// Paramters:   none
//
// Returned:    none
//******************************************************************************
void InBetweenPlayer::clearBank () {
  mcInBetweenHand.clear ();
}