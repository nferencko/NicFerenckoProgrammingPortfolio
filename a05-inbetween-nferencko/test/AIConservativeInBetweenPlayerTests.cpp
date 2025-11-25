
#include <gtest/gtest.h>
#include "../include/AIConservativeInBetweenPlayer.h"
#include "../include/InBetweenPlayer.h"

TEST(AIConservativeInBetweenPlayerTests, constructor){

  AIConservativeInBetweenPlayer cP0;
  AIConservativeInBetweenPlayer cP1;
  Bank cBank(5);
  InBetweenHand cHand;
  cHand.addCard(Card("S", "A"));
  cHand.addCard(Card("S", "10"));

  EXPECT_EQ("Player_0", cP0.getName());
  EXPECT_EQ("Player_1", cP1.getName());

  EXPECT_EQ(0, cP0.getBank().getBalance());
  EXPECT_EQ(0, cP0.getInBetweenHand().size());


  AIConservativeInBetweenPlayer cAIP("Ricky", cBank, cHand);

  EXPECT_EQ("Ricky", cAIP.getName());
  EXPECT_EQ(cBank.getBalance(), cAIP.getBank().getBalance());
  EXPECT_EQ(cHand.getDistance(), cAIP.getInBetweenHand().getDistance());
}