//******************************************************************************
// File name:  InBetweenGamePlay.cpp
// Author:     Nic Ferencko
// Date:       5/2/2025
// Class:      CS250
// Assignment: Card Games 05
// Purpose:    Implementation of the InBetweenGamePlay interface.  
//******************************************************************************

#include "../include/InBetweenGamePlay.h"
#include "../include/HumanInBetweenPlayer.h"

//******************************************************************************
// Constructor: InBetweenGamePlay
// 
// Description: Creates a InBetweenGamePlay object
//
// Paramters:   rcRiskLevel - Risk Level AI players will play with
//
// Returned:    None
//******************************************************************************
InBetweenGamePlay::InBetweenGamePlay (const RiskLevel& rcRiskLevel) {

  Deck cDeck;

  mcRiskLevel = rcRiskLevel;
  mcPot = 0;

  cDeck.shuffle ();
  mcDeck = cDeck;

}

//******************************************************************************
// Destructor:  InBetweenGamePlay
// 
// Description: Default destructor for InBetweenGamePlay
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
InBetweenGamePlay:: ~InBetweenGamePlay () {
  for (auto cPlayer : mcInBetweenPlayers) {
    delete(cPlayer);
  }
}

//******************************************************************************
// Function:    addPlayer
// 
// Description: Adds a player to the game
//
// Paramters:   pcInBetweenPlayer - Pointer to the player being added
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::addPlayer (InBetweenPlayer* pcInBetweenPlayer) {

  mcInBetweenPlayers.push_back (pcInBetweenPlayer);
}

//******************************************************************************
// Function:    printBanks
// 
// Description: Prints the player's Bank
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::printBanks () const {


  for (auto cPlayer : mcInBetweenPlayers) {
    std::cout << cPlayer->getName () << "'s Bank: $"
      << cPlayer->getBank ().getBalance () << std::endl;
  }
}

//******************************************************************************
// Function:    addAntesToPot
// 
// Description: Adds player's antes to the pot
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::addAntesToPot () {

  int ante = mcRiskLevel;

  for (auto cPlayer : mcInBetweenPlayers) {
    cPlayer->subtractFromBank (ante);
    mcPot.add (ante);
  }
}

//******************************************************************************
// Function:    dealCards
// 
// Description: Deals the cards to the players
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::dealCards () {

  for (int i = 0; i < 2; i++) {
    for (auto cPlayer : mcInBetweenPlayers) {
      cPlayer->addToInBetweenHand (mcDeck.dealCard ());
    }
  }
}

//******************************************************************************
// Function:    isInBetween
// 
// Description: Checks to see if the InBetween Card's value is in between
//              the player's hand
//
// Paramters:   rcCard1 - Smaller Card in the player's hand
//              rcCard2 - Bigger Card in the player's hand
//              rcInBetweenCard - The InBetween Card
//
// Returned:    The player's bet
//******************************************************************************
bool InBetweenGamePlay::isInBetween (const Card& rcCard1,
  const Card& rcCard2, const Card& rcInBetweenCard) const {

  bool bIsBetween;

  if (rcInBetweenCard.getDenominationValue () > rcCard1.getDenominationValue () &&
    rcInBetweenCard.getDenominationValue () < rcCard2.getDenominationValue ()) {
    bIsBetween = true;
  }
  else {
    bIsBetween = false;
  }

  return bIsBetween;
}

//******************************************************************************
// Function:    playRound
// 
// Description: Plays a round in the game
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::playRound () {

  int bet;
  int cardDistance;
  bool bValidBet;
  Card cInBetweenCard ("S", "5"); // InBetweenCard value will change
  std::string cTemp;
  InBetweenPlayer* pcPlayer;

  // Ensures players start with no cards
  clearInBetweenHand ();
  // Remove players with balance of 0 from the game
  bootPlayers ();

  addAntesToPot ();
  // If there's not enough cards to play round, get a new Deck
  if (mcDeck.size () < mcInBetweenPlayers.size () * 3) {
    mcDeck = Deck ();
    mcDeck.shuffle ();
  }
  dealCards ();

  // while the pot is not empty, each player plays their turn
  for (int i = 0; i < mcInBetweenPlayers.size () && mcPot.getBalance () != 0;
    i++) {

    pcPlayer = mcInBetweenPlayers[i];
    cardDistance = pcPlayer->getInBetweenHand ().getDistance ();

    printPot ();
    printPlayerPreBet (pcPlayer);

    if (cardDistance == 0 || cardDistance == 1) {
      printNoBetResults (pcPlayer, cardDistance);
    }

    // Otherwise, player players turn
    else {

      bValidBet = getPlayerBet (pcPlayer, bet);

      if (bValidBet) {
        cInBetweenCard = mcDeck.dealCard ();
        std::cout << "InBetween Card: " << cInBetweenCard << std::endl;

        // Determines if player wins or loses and handles outcome
        playerResult (pcPlayer, cInBetweenCard, bet);

      }

    }

    std::cout << pcPlayer->getName () << "'s Bank: "
      << pcPlayer->getBank ().getBalance () << std::endl << std::endl;
  }

  printPot ();

}

//******************************************************************************
// Function:    playAgain
// 
// Description: Asks the user if they want to play another round
//
// Paramters:   None
//
// Returned:    Returns true if another round will be played, false otherwise
//******************************************************************************
bool InBetweenGamePlay::playAgain () const {

  const char PLAY_AGAIN = 'P';
  const char QUIT = 'Q';

  char menuChoice;
  bool bPlayAgain;
  bool bValidMenuChoice = false;
  std::string cTemp;  // temp variable for getline to clear buffer

  while (!bValidMenuChoice) {

    cTemp = "";

    std::cout << "P)lay game" << std::endl << "Q)uit" << std::endl << std::endl;

    std::cout << "Enter your choice: ";

    std::cin >> menuChoice;
    menuChoice = std::toupper (menuChoice);
    std::cin.clear ();  //clear any error flags
    getline (std::cin, cTemp);  // clear carraige from buffer

    if (cTemp != "") {
      bValidMenuChoice = false;
    }
    else if (menuChoice == PLAY_AGAIN) {
      bPlayAgain = true;
      bValidMenuChoice = true;
    }
    else if (menuChoice == QUIT) {
      bPlayAgain = false;
      bValidMenuChoice = true;
    }
  }

  return bPlayAgain;
}

//******************************************************************************
// Function:    clearInBetweenHand
// 
// Description: Clears the player's hand
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::clearInBetweenHand () {

  for (auto cPlayer : mcInBetweenPlayers) {
    cPlayer->clearInBetweenHand ();
  }
}

//******************************************************************************
// Function:    bootPlayers
// 
// Description: Removes players from the game if their Bank balance is 0
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::bootPlayers () {

  int index = 0;

  while (index < mcInBetweenPlayers.size ()) {
    if (mcInBetweenPlayers[index]->getBank ().getBalance () == 0) {
      delete(mcInBetweenPlayers[index]); // Free memory
      mcInBetweenPlayers.erase (mcInBetweenPlayers.begin () + index);
    }
    else {
      index++; // Only increment if we don't remove from vector
    }
  }
}

//******************************************************************************
// Function:    playerResult
// 
// Description: Evaluates the player's results from the game and executes
//              accordingly
//
// Paramters:   pcPlayer - The player object
//              cCard - The InBetween Card
//              bet - the player's bet
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::playerResult (InBetweenPlayer* pcPlayer,
  const Card& cCard, int bet) {

  if (isInBetween (pcPlayer->getInBetweenHand ().getCard (0),
    pcPlayer->getInBetweenHand ().getCard (1), cCard)) {
    std::cout << pcPlayer->getName () << " wins!" << std::endl;
    pcPlayer->addToBank (bet);
    this->mcPot.subtract (bet);
  }
  else {
    std::cout << pcPlayer->getName () << " loses." << std::endl;
    pcPlayer->subtractFromBank (bet);
    this->mcPot.add (bet);
  }
}

//******************************************************************************
// Function:    printPlayerPreBet
// 
// Description: Prints the player's title before the getting the bet
//
// Paramters:   pcPlayer- The player object
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::printPlayerPreBet (InBetweenPlayer* pcPlayer) {

  std::cout << pcPlayer->getName () << "'s Turn" << std::endl;
  std::cout << pcPlayer->getName () << "'s Bank: "
    << pcPlayer->getBank ().getBalance () << std::endl;
  std::cout << "Cards: " << pcPlayer->getInBetweenHand () << std::endl;
}

//******************************************************************************
// Function:    printPot
// 
// Description: Prints pot
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::printPot () const {
  std::cout << "Pot Size: $" << mcPot.getBalance () << std::endl << std::endl;
}

//******************************************************************************
// Function:    getPlayerBet
// 
// Description: Gets the player's bet
//
// Paramters:   pcPlayer - The player object
//              bet - The player's bet
//
// Returned:    False if the enter an invalid bet, true otherwise
//******************************************************************************
bool InBetweenGamePlay::getPlayerBet (InBetweenPlayer* pcPlayer, int& bet) {

  const int PENALTY = 1; // Penalty for bad bet

  bool bValidBet = true;

  std::cout << "Place your bet: ";

  try {
    // If HumanInBetweenPlayer get their bet
    if (dynamic_cast<HumanInBetweenPlayer*>(pcPlayer) != nullptr) {
      bet = pcPlayer->getBet (mcPot.getBalance ());
    }
    // Otherwise get bet and print output properly
    else {
      bet = pcPlayer->getBet (mcPot.getBalance ());
      std::cout << bet << std::endl;
    }
  }
  catch (std::runtime_error) {
    bValidBet = false;
    if (pcPlayer->getBank ().getBalance () >= PENALTY) {
      std::cout << "Invalid bet amount. You lose your turn and 1 chip"
        << std::endl;
      pcPlayer->subtractFromBank (PENALTY);
    }
  }

  return bValidBet;
}

//******************************************************************************
// Function:    printNoBetResults
// 
// Description: Prints player results for special cases with no InBetween Card
//
// Paramters:   pcPlayer - The player object
//              distance - The distance between denomination values of the cards
//
// Returned:    None
//******************************************************************************
void InBetweenGamePlay::printNoBetResults (InBetweenPlayer* pcPlayer, int distance) {

  const int AUTO_WIN = 2;
  const int AUTO_LOSS = 1;

  if (distance == 0) {
    std::cout << pcPlayer->getName () << " wins!" << std::endl;
    if (mcPot.getBalance () >= AUTO_WIN) {
      pcPlayer->addToBank (AUTO_WIN);
      this->mcPot.subtract (AUTO_WIN);
    }
    else {
      std::cout << "Bank doesn't have enough fund. Ask Doug for money."
        << std::endl;
    }

  }
  else {
    std::cout << pcPlayer->getName () << " loses." << std::endl;
    pcPlayer->subtractFromBank (AUTO_LOSS);
    this->mcPot.add (AUTO_LOSS);
  }
}

