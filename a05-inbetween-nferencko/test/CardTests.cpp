//****************************************************************************** 
// File name:		CardTests.cpp
// Author:			Nic Ferencko
// Date:				4/16
// Class:				CS 250
// Assignment:  Card Games 05
// Purpose:			This file is written to unit test all functions that are part
//              of the included interface.
// Hours:       1
//******************************************************************************

#include <gtest/gtest.h>
#include "../include/Card.h"

TEST (CardTest, constructor) {

  Card cCard ("H", "7");
  Card cCard2 ("S", "K");
  Card cCard3 ("C", "A");
  Card cCard4 ("D", "J");
  Card cCardCopy (cCard); // Test when written == method

  EXPECT_EQ ("H", cCard.getSuit ());
  EXPECT_EQ ("7", cCard.getDenomination ());
  EXPECT_EQ (7, cCard.getDenominationValue ());

  EXPECT_EQ ("S", cCard2.getSuit ());
  EXPECT_EQ ("K", cCard2.getDenomination ());
  EXPECT_EQ (13, cCard2.getDenominationValue ());

  EXPECT_EQ ("C", cCard3.getSuit ());
  EXPECT_EQ ("A", cCard3.getDenomination ());
  EXPECT_EQ (1, cCard3.getDenominationValue ());

  EXPECT_EQ ("D", cCard4.getSuit ());
  EXPECT_EQ ("J", cCard4.getDenomination ());
  EXPECT_EQ (11, cCard4.getDenominationValue ());

  EXPECT_EQ (true, cCard == cCardCopy);
  EXPECT_EQ (false, cCard2 == cCardCopy);
}

TEST (CardTest, constructorException) {

  EXPECT_THROW (Card ("K", "K"), std::invalid_argument);
  EXPECT_THROW (Card ("H", "P"), std::invalid_argument);
  EXPECT_THROW (Card ("H", "Joker"), std::invalid_argument);
  EXPECT_THROW (Card ("Heart", "K"), std::invalid_argument);
  EXPECT_THROW (Card ("", "P"), std::invalid_argument);

}

TEST (CardTest, write) {

  Card cCard ("S", "A");

  std::ostringstream cOut;

  cOut << cCard;

  EXPECT_EQ ("AS", cOut.str ());
}