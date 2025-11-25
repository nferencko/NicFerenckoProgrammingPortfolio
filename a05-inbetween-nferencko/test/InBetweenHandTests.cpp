//****************************************************************************** 
// File name:		InBetweenHandTests.cpp
// Author:			Nic Ferencko
// Date:				4/16
// Class:				CS 250
// Assignment:  Card Games 05
// Purpose:			This file is written to unit test all functions that are part
//              of the included interface.
// Hours:       5
//******************************************************************************

#include <gtest/gtest.h>
#include "../include/InBetweenHand.h"
#include "../include/Hand.h"
#include <ostream>

TEST (InBetweenTest, constructor) {

  InBetweenHand cInBetweenHand;
  Card cCard ("S", "A");
  Card cCard2 ("H", "K");

  EXPECT_EQ (0, cInBetweenHand.size ());

  cInBetweenHand.addCard (cCard);
  cInBetweenHand.addCard (cCard2);

  EXPECT_EQ (2, cInBetweenHand.size ());

  InBetweenHand cInBetweenHand2 (cInBetweenHand);

  EXPECT_EQ (true, cInBetweenHand.getCard (0) == cInBetweenHand2.getCard (0));
  EXPECT_EQ (true, cInBetweenHand.getCard (1) == cInBetweenHand2.getCard (1));
}

TEST (InBetweenHandTest, addCard) {

  // Test INBetweenHand addCard
  InBetweenHand cInBetweenHand;
  Card cCard ("S", "A");
  Card cCard2 ("H", "K");
  std::ostringstream rcOut;

  EXPECT_EQ (0, cInBetweenHand.size ());

  cInBetweenHand.addCard (cCard);

  EXPECT_EQ (1, cInBetweenHand.size ());

  cInBetweenHand.addCard (cCard2);

  EXPECT_EQ (2, cInBetweenHand.size ());
  EXPECT_EQ (true, cInBetweenHand.getCard (1).getDenomination () >
    cInBetweenHand.getCard (0).getDenomination ());

  // Calling write function in Hand which calls write function in Card
  rcOut << cInBetweenHand;

  EXPECT_EQ ("AS KH", rcOut.str ());

  cInBetweenHand.removeCard (0);

  cInBetweenHand.addCard (cCard2);

  EXPECT_EQ (2, cInBetweenHand.size ());
  EXPECT_EQ (true, cInBetweenHand.getCard (0) == cInBetweenHand.getCard (1));

  cInBetweenHand.clear ();

  cInBetweenHand.addCard (cCard2);
  cInBetweenHand.addCard (cCard);

  EXPECT_EQ (true, cInBetweenHand.getCard (1).getDenominationValue () >
    cInBetweenHand.getCard (0).getDenominationValue ());

  cInBetweenHand.clear ();

  // Testing Hand addCard and polymorphic behavior
  Hand* pcHand;
  Hand cHand;

  pcHand = &cInBetweenHand;

  // Running InBetweenHand addCard
  pcHand->addCard (cCard2);
  pcHand->addCard (cCard);

  EXPECT_EQ (true, pcHand->getCard (1).getDenominationValue () >
    pcHand->getCard (0).getDenominationValue ());

  pcHand = &cHand;

  // Running Hand addCard
  pcHand->addCard (cCard2);
  pcHand->addCard (cCard);

  EXPECT_EQ (true, pcHand->getCard (1).getDenominationValue () <
    pcHand->getCard (0).getDenominationValue ());

  // Adding Card in the middle of Hand
  pcHand->addCard (cCard, 1);

  EXPECT_EQ (3, pcHand->size ());

  EXPECT_EQ (true, pcHand->getCard (1) == pcHand->getCard (2));
  EXPECT_EQ (false, pcHand->getCard (1) == pcHand->getCard (0));
}

TEST (InBetweenHandTest, addCardException) {

  InBetweenHand cBetweenHand;
  Hand* pcHand = &cBetweenHand;

  Card cCard ("S", "A");
  Card cCard2 ("H", "K");
  Card cCard3 ("H", "8");

  cBetweenHand.addCard (cCard);
  cBetweenHand.addCard (cCard2);

  EXPECT_THROW (cBetweenHand.addCard (cCard3), std::runtime_error);
  EXPECT_THROW (pcHand->addCard (cCard3), std::runtime_error);
}

TEST (InBetweenHandTest, getDistance) {

  InBetweenHand cBetweenHand;

  Card cCard ("S", "A");
  Card cCard2 ("H", "K");

  cBetweenHand.addCard (cCard);
  cBetweenHand.addCard (cCard2);

  EXPECT_EQ (11, cBetweenHand.getDistance ());

  cBetweenHand.removeCard (1);

  cBetweenHand.addCard (Card ("D", "2"));
  EXPECT_EQ (0, cBetweenHand.getDistance ());

  cBetweenHand.removeCard (0);

  cBetweenHand.addCard (Card ("H", "A"));
  EXPECT_EQ (0, cBetweenHand.getDistance ());

  cBetweenHand.removeCard (0);

  cBetweenHand.addCard (Card ("H", "2"));
  EXPECT_EQ (0, cBetweenHand.getDistance ());

}


TEST (InBetweenHandTest, getDistanceException) {

  InBetweenHand cInBetweenHand;
  Card cCard ("S", "A");

  EXPECT_THROW (cInBetweenHand.getDistance (), std::runtime_error);

  cInBetweenHand.addCard (cCard);

  EXPECT_THROW (cInBetweenHand.getDistance (), std::runtime_error);
}

TEST (InBetweenHand, write) {
  // Write an InBetweenHand
  Hand* pcHand = new InBetweenHand ();
  std::ostringstream rcOut;

  rcOut << *pcHand;

  EXPECT_EQ ("", rcOut.str ());

  pcHand->addCard (Card ("D", "K"));
  pcHand->addCard (Card ("S", "6"));

  rcOut << *pcHand;

  EXPECT_EQ ("6S KD", rcOut.str ());
}

