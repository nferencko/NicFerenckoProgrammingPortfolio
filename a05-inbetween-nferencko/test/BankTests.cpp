
#include <gtest/gtest.h>
#include "../include/Bank.h"

TEST(BankTests, constructor){

  Bank cBank;
  Bank cBank2(5);
  Bank cCopy(cBank2);

  EXPECT_EQ(0, cBank.getBalance());
  EXPECT_EQ(5, cBank2.getBalance());
  EXPECT_EQ(5, cCopy.getBalance());

  EXPECT_THROW(Bank cBad(-1), std::invalid_argument);

  cBank2.clear();

  EXPECT_EQ(0, cBank2.getBalance());
}

TEST(BankTests, addSubtract){

  Bank cBank(0);

  cBank.add(4);

  EXPECT_EQ(4, cBank.getBalance());

  cBank.subtract(3);

  EXPECT_EQ(1, cBank.getBalance());
}