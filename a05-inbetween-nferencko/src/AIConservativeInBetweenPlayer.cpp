//******************************************************************************
// File name:  AIConservativeInBetweenPlayer.cpp
// Author:     Nic Ferencko
// Date:       5/1/2025
// Class:      CS250
// Assignment: Card Games 05
// Purpose:    Implementation of the AIConservativeInBetweenPlayer interface.  
//******************************************************************************

#include "../include/AIConservativeInBetweenPlayer.h"
#include "../include/InBetweenPlayer.h"
#include "../include/InBetweenGamePlay.h"

//******************************************************************************
// Constructor: AIConservativeInBetweenPlayer
// 
// Description: Create a AIConservativeInBetweenPlayer objet
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
AIConservativeInBetweenPlayer::AIConservativeInBetweenPlayer () :
  InBetweenPlayer () {

}

//******************************************************************************
// Constructor: AIConservativeInBetweenPlayer
// 
// Description: Create a AIConservativeInBetweenPlayer objet
//
// Paramters:   rcName - Player's name
//              rcBank - Player's Bank
//              rcInBetweenHand - Player's InBetweenHand
//
// Returned:    None
//******************************************************************************
AIConservativeInBetweenPlayer::AIConservativeInBetweenPlayer
(const std::string& rcName, const Bank& rcBank,
  const InBetweenHand& rcInBetweenHand) :
  InBetweenPlayer (rcName, rcBank, rcInBetweenHand) {

}

//******************************************************************************
// Constructor: AIConservativeInBetweenPlayer
// 
// Description: Copy Constructor
//
// Paramters:   rcConservativeInBetweenPlayer - Player to be copied
//
// Returned:    None
//******************************************************************************
AIConservativeInBetweenPlayer::AIConservativeInBetweenPlayer
(const AIConservativeInBetweenPlayer& rcConservativeInBetweenPlayer) :
  InBetweenPlayer (rcConservativeInBetweenPlayer) {

}

//******************************************************************************
// Destructor: ~Card
// 
// Description: Default destructor for a AIConservativeInBetweenPlayer object
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
AIConservativeInBetweenPlayer:: ~AIConservativeInBetweenPlayer () {
  // No dynamic Memory
}

//******************************************************************************
// Function:    getBet
// 
// Description: Get the player's bet
//
// Paramters:   None
//
// Returned:    the player's bet
//******************************************************************************
int AIConservativeInBetweenPlayer::getBet (int potSize) const {

  const int RISK_DISTANCE = 10;

  int betSize;

  // Bet size determined by risk distance and how much money player has
  if (this->getInBetweenHand ().getDistance () > RISK_DISTANCE) {
    if (this->getBank ().getBalance () >=
      InBetweenGamePlay::RiskLevel { InBetweenGamePlay::HIGH }) {
      betSize = InBetweenGamePlay::RiskLevel { InBetweenGamePlay::HIGH };
    }
    else if (this->getBank ().getBalance () ==
      InBetweenGamePlay::RiskLevel { InBetweenGamePlay::MEDIUM }) {
      betSize = InBetweenGamePlay::RiskLevel { InBetweenGamePlay::MEDIUM };
    }
    else {
      betSize = InBetweenGamePlay::RiskLevel { InBetweenGamePlay::LOW };
    }

  }

  else {
    betSize = InBetweenGamePlay::RiskLevel { InBetweenGamePlay::LOW };
  }

  if (betSize > potSize) {
    betSize = potSize;
  }

  // If player doesn't have enough, bet the rest
  if (this->getBank ().getBalance () < betSize) {
    betSize = this->getBank ().getBalance ();
  }

  return betSize;
}