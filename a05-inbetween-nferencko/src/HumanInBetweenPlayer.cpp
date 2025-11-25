//******************************************************************************
// File name:  HumanInBetweenPlayer.cpp
// Author:     Nic Ferencko
// Date:       5/2/2025
// Class:      CS250
// Assignment: Card Games 05
// Purpose:    Implementation of the HumanInBetweenPlayer interface.  
//******************************************************************************

#include "../include/HumanInBetweenPlayer.h"
#include "../include/InBetweenPlayer.h"
#include "../include/InBetweenGamePlay.h"

//******************************************************************************
// Constructor: HumanInBetweenPlayer
// 
// Description: Creates a default HumanInBetweenPlayer object
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
HumanInBetweenPlayer::HumanInBetweenPlayer () : InBetweenPlayer () {

}

//******************************************************************************
// Constructor: HumanInBetweenPlayer
// 
// Description: Creates a HumanInBetweenPlayer object
//
// Paramters:   rcName - Player's name
//              rcBank - Player's Bank
//              rcInBetweenHand - Player's InBetweenHand
//
// Returned:    None
//******************************************************************************
HumanInBetweenPlayer::HumanInBetweenPlayer
(const std::string& rcName, const Bank& rcBank,
  const InBetweenHand& rcInBetweenHand) :
  InBetweenPlayer (rcName, rcBank, rcInBetweenHand) {

}

//******************************************************************************
// Constructor: HumanInBetweenPlayer
// 
// Description: Copy constructor for HumanInBetweenPlayer
//
// Paramters:   rcHumanInBetweenPlayer - Human player being copied
//
// Returned:    None
//******************************************************************************
HumanInBetweenPlayer::HumanInBetweenPlayer
(const HumanInBetweenPlayer& rcHumanInBetweenPlayer) :
  InBetweenPlayer (rcHumanInBetweenPlayer) {

}

//******************************************************************************
// Destructor:  HumanInBetweenPlayer
// 
// Description: Default destructor for HumanInBetweenPlayer
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
HumanInBetweenPlayer:: ~HumanInBetweenPlayer () {
  // No Dynamic Memory
}

//******************************************************************************
// Function:    getBet
// 
// Description: Gets the player's bet. Bet cannot exceed potSize
//
// Paramters:   potSize - Amount of money in the pot
//
// Returned:    The player's bet
//******************************************************************************
int HumanInBetweenPlayer::getBet (int potSize) const {

  int betSize = -1;
  std::string cTemp;

  if (this->getInBetweenHand ().size () != 2) {
    throw std::runtime_error
    ("HumanInBetweenPlayer Exception::getBet: Invalid hand size");
  }


  std::cin >> betSize;

  // If user doesn't enter an int
  if (!std::cin) {
    betSize = -1;
    std::cin.clear ();
    getline (std::cin, cTemp); // clear the buffer
  }

  if (betSize < 0 || betSize > this->getBank ().getBalance ()) {
    throw std::runtime_error
    ("HumanInBetweenPlayer Exception::getBet:: Invalid bet amount");
  }

  if (betSize > potSize) {
    throw std::runtime_error
    ("HumanInBetweenPlayer Exception::getBet:: Bet cannot exceed pot size");
  }

  return betSize;
}

