//******************************************************************************
// File name:   main.cpp
// Author:      Nic Ferencko
// Date:        5/3/25
// Class:       CS 250
// Assignment:  Card Games 05
// Purpose:     Driver for InBetween game
// Hours:       3
//******************************************************************************

#include <iostream>
#include <sstream>

#include "../include/SampleClass.h"
#include "../include/Deck.h"
#include "../include/Card.h"
#include "../include/Hand.h"
#include "../include/InBetweenHand.h"
#include "../include/Bank.h"
#include "../include/HumanInBetweenPlayer.h"
#include "../include/InBetweenPlayer.h"
#include "../include/InBetweenGamePlay.h"
#include "../include/AIConservativeInBetweenPlayer.h"

// Menu constants
const std::string MC_ADD_AI_PLAYER = "A";
const std::string MC_ADD_HUMAN_PLAYER = "H";
const std::string MC_DONE = "D";

void addPlayers (InBetweenGamePlay& rcGamePlay);
void printAddPlayerMenu ();
std::string converToUpper (std::string menuChoice);
void addAIPlayer (InBetweenGamePlay& rcGamePlay);
void addHumanPlayer (InBetweenGamePlay& rcGamePlay);
void initializeBankBalance (InBetweenPlayer* pcPlayer);


//******************************************************************************
// Function:    main
// 
// Description: Implimentation of all the Classes
//
// Paramters:   None
//
// Returned:    EXIT_SUCCESS
//******************************************************************************
int main () {

  const int SEED_VALUE = 3;
  const InBetweenGamePlay::RiskLevel RISK_LEVEL =
    InBetweenGamePlay::RiskLevel { InBetweenGamePlay::LOW };

  bool bPlayAgain = true;
  int roundCount = 1;

  srand (SEED_VALUE);
  InBetweenGamePlay cGamePlay (RISK_LEVEL);

  std::cout << "Welcome to InBetween!" << std::endl << std::endl;

  addPlayers (cGamePlay);
  std::cout << std::endl;
  bPlayAgain = cGamePlay.playAgain ();

  while (bPlayAgain) {

    std::cout << std::endl << "*** Round #" << roundCount << " ***"
      << std::endl << std::endl;

    cGamePlay.playRound ();
    bPlayAgain = cGamePlay.playAgain ();
    roundCount++;

  }

  std::cout << std::endl << std::endl << "*** Final Bank Values: ***"
    << std::endl << std::endl;

  cGamePlay.printBanks ();

  return EXIT_SUCCESS;

}

//******************************************************************************
// Function:    addPlayers
// 
// Description: Adds all the players to the game
//
// Paramters:   rcGamePlayer - The game object
//
// Returned:    None
//******************************************************************************
void addPlayers (InBetweenGamePlay& rcGamePlay) {

  std::string menuChoice = "";

  std::cout << "*** Adding Players ***" << std::endl << std::endl;

  while (menuChoice != MC_DONE) {
    printAddPlayerMenu ();

    std::getline (std::cin, menuChoice);
    menuChoice = converToUpper (menuChoice);

    if (menuChoice == MC_ADD_AI_PLAYER) {
      addAIPlayer (rcGamePlay);
    }
    else if (menuChoice == MC_ADD_HUMAN_PLAYER) {
      addHumanPlayer (rcGamePlay);
    }
  }
}

//******************************************************************************
// Function:    printAddPlayerMenu
// 
// Description: Prints the add player menu
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void printAddPlayerMenu () {

  std::cout << "A)I player add" << std::endl;
  std::cout << "H)uman player add" << std::endl;
  std::cout << "D)one adding players" << std::endl << std::endl;

  std::cout << "Enter your choice: ";
}

//******************************************************************************
// Function:    converToUpper
// 
// Description: Converts string to uppercase
//
// Paramters:   menuChoice - The users menu choice
//
// Returned:    An uppercase version of the menuChoice
//******************************************************************************
std::string converToUpper (std::string menuChoice) {

  // A valid choice should only be one character
  for (int i = 0; i < menuChoice.size (); i++) {
    menuChoice[i] = toupper (menuChoice[i]);
  }

  return menuChoice;
}

//******************************************************************************
// Function:    addAIPlayer
// 
// Description: Adds an AI player to the game
//
// Paramters:   rcGamePlay - The game object
//
// Returned:    None
//******************************************************************************
void addAIPlayer (InBetweenGamePlay& rcGamePlay) {

  const int DEFAULT_BANK = 10;
  
  InBetweenPlayer* pcPlayer;

  std::cout << std::endl << "*** Adding Conservative AI Player ***" << std::endl
    << std::endl;
  pcPlayer = new AIConservativeInBetweenPlayer ();
  std::cout << "Enter Name: " << pcPlayer->getName () << std::endl;

  pcPlayer->addToBank(DEFAULT_BANK); 

  std::cout << "Enter starting bank: $" << pcPlayer->getBank().getBalance()
    << std::endl << std::endl;

  rcGamePlay.addPlayer (pcPlayer);
}

//******************************************************************************
// Function:    addHumanPlayer
// 
// Description: Adds a Human player to the game
//
// Paramters:   rcGamePlay - The game object
//
// Returned:    None
//******************************************************************************
void addHumanPlayer (InBetweenGamePlay& rcGamePlay) {

  InBetweenPlayer* pcPlayer;
  int startBalance = 0;
  std::string cName;

  std::cout << std::endl << "*** Adding Human Player ***" << std::endl
    << std::endl;
  std::cout << "Enter name: ";
  std::getline (std::cin, cName);
  pcPlayer = new HumanInBetweenPlayer (cName, Bank (startBalance),
    InBetweenHand ());

  initializeBankBalance (pcPlayer);

  rcGamePlay.addPlayer (pcPlayer);
}

//******************************************************************************
// Function:    initializeBankBalance
// 
// Description: Get Bank balance from user and update InBetweenPlayer's
//              Bank balance
//
// Paramters:   pcPlayer - point to Player whose Bank balance will be updated
//
// Returned:    none
//******************************************************************************
void initializeBankBalance (InBetweenPlayer* pcPlayer) {

  int startBalance = -1;
  std::string userCheck = "";

  // get a positive int from user.  Keep trying until good input.
  while (startBalance < 0) {

    std::cout << "Enter starting bank: $";

    std::cin >> startBalance;
    std::cin.clear ();  // clear error flags if int can't be read
    std::getline (std::cin, userCheck); // store any remaining input in userCheck

    std::cout << std::endl; // Add blank line after user input

    // If userCheck is not the empty string, user input was not an int.
    // e.g, they might have typed "hello" or "54.53".  If this
    // is the case, set startBalance = -1 so that we enter the while loop
    // again.  Otherewise, we should update the bank balance.
    if (0 != userCheck.length () || startBalance < 0) {

      startBalance = -1; // Make sure we go back into loop
    }
    else {
      pcPlayer->addToBank (startBalance);  // exception of startBalance < 0

    }

  }

}

