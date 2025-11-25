//******************************************************************************
// File name:  Bank.cpp
// Author:     Nic Ferencko
// Date:       5/1/2025
// Class:      CS250
// Assignment: Card Games 05
// Purpose:    Implementation of the Bank interface.  
//******************************************************************************

#include "../include/Bank.h"

//******************************************************************************
// Constructor: Bank
// 
// Description: Create a default Bank objet
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
Bank::Bank () {

  setBalance (0);
}

//******************************************************************************
// Constructor: Bank
// 
// Description: Create a Bank objet
//
// Paramters:   amount - Balance player is starting with
//
// Returned:    None
//******************************************************************************
Bank::Bank (int amount) {

  // Amount must be non negative
  if (amount < 0) {
    throw std::invalid_argument ("Bank Exception::Bank: amount must be positive");
  }

  setBalance (amount);
}

//******************************************************************************
// Constructor: Bank
// 
// Description: Copy constructor
//
// Paramters:   rcBank - Bank to be copied
//
// Returned:    None
//******************************************************************************
Bank::Bank (const Bank& rcBank) {

  setBalance (rcBank.mBalance);
}

//******************************************************************************
// Destructor: ~Bank
// 
// Description: Default destructor for a Bank object
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
Bank:: ~Bank () {
  // No dynamic memory
}

//******************************************************************************
// Function:    setBalance
// 
// Description: Sets the bank balance for a player
//
// Paramters:   iAmount - Amount balance is being set to
//
// Returned:    None
//******************************************************************************
void Bank::setBalance (int iAmount) {

  mBalance = iAmount;
}

//******************************************************************************
// Function:    getBalance
// 
// Description: Get the bank balance
//
// Paramters:   None
//
// Returned:    the bank balance
//******************************************************************************
int Bank::getBalance () const {

  return mBalance;
}

//******************************************************************************
// Function:    add
// 
// Description: Add to bank balance
//
// Paramters:   amount - Amount to be added to bank balance. Must be >= 0
//
// Returned:    None
//******************************************************************************
void Bank::add (int amount) {

  if (amount < 0) {
    throw std::invalid_argument ("Bank Exception::add: amount must be positive");
  }

  mBalance += amount;
}

//******************************************************************************
// Function:    subtract
// 
// Description: Subtract from bank balance
//
// Paramters:   amount - Amount to be subtracted from bank balance. Must be >= 0
//
// Returned:    None
//******************************************************************************
void Bank::subtract (int amount) {

  if (amount < 0) {
    throw std::invalid_argument
    ("Bank Exception::subtract: amount must be positive");
  }

  if (amount > mBalance) {

    throw std::invalid_argument ("Bank Exception::subtract: insufficient funds");
  }

  mBalance -= amount;
}

//******************************************************************************
// Function:    clear
// 
// Description: Set Bank balance to 0
//
// Paramters:   None
//
// Returned:    None
//******************************************************************************
void Bank::clear () {

  mBalance = 0;
}

//******************************************************************************
// Function:    operator <<
// 
// Description: outputs a bank to an ostream
//
// Paramters:   rcOutStream - the output stream
//              rcBank - the Bank object being outputted
//
// Returned:    rcOutStream
//******************************************************************************
std::ostream& operator<< (std::ostream& rcOutStream, const Bank& rcBank) {

  rcOutStream << rcBank.getBalance ();

  return rcOutStream;
}