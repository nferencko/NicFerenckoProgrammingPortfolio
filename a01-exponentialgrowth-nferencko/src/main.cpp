//******************************************************************************
// File name:   main.cpp
// Author:      Nic Ferencko
// Date:        06/15/2024
// Class:       CS150
// Assignment:  Assignement 3 -- Code Refactoring and Functions
// Purpose:     To use functions and optimize assignment 1
// Hours:       6
//******************************************************************************

#include <iostream>
#include <iomanip>
#include <exception>

using namespace std;

// Column widths in table
const int NUMBER_OF_MONTHS = 12;
const int BEGGINNING_BALANCE_WIDTH = 12;
const int INVESTMENT_WIDTH = 15;
const int MONEY_WIDTH = 15;
const int ENDING_BALANCE_WIDTH = 17;
const int TIME_PERIOD_WIDTH = 9;
const int SPACE_WIDTH = 5;
const char UPPER_CASE_MONTH = 'M';
const char LOWER_CASE_MONTH = 'm';
const string MONTH = "Month";
const char UPPER_CASE_YEAR = 'Y';
const char LOWER_CASE_YEAR = 'y';
const string YEAR = "Year";


// Function Names
double getDouble (string prompt);
int getInt (string prompt);
char getChar (string prompt);
void printHeader (string timePeriod);
void printMonthResults (double annualSalary, double percentOfAnnualSalary,
  int yearsUntilRetirement, double yearlyRateOfReturn);
void printYearResults (double annualSalary, double percentOfAnnualSalary,
  int yearsUntilRetirement, double yearlyRateOfReturn);
  void printTableResults (double annualSalary, double percentOfAnnualSalary,
  int yearsUntilRetirement, double yearlyRateOfReturn, char printResults);
  void printTableRow (double beginningBalance, double amountInvested, 
                      double moneyEarned, double endingBalance, int count);

int main () {

  double annualSalary;
  double percentOfAnnualSalary; // 3% stored as 0.03
  double yearlyRateOfReturn; // 3% stored as 0.03
  int yearsUntilRetirement;
  char printResults; // Character for monthly or yearly results

  cout << "*********************************" << endl;
  cout << "*      When Can I Retire        *" << endl;
  cout << "*********************************" << endl << endl;

  // Gathering all user input
  // Turn percentages into percent decimal values

  // Gather users annual salary
  while (true) {
    try {
      annualSalary = getDouble ("Enter Annual Salary: $ ");

      if (annualSalary < 0.0) {
        throw exception ();
      }
      break;
    }
    catch (const exception& e) {
      // Go back into while loop
    }
  }

  // Get percent of salary to be invested from user
  while (true) {
    try {
      percentOfAnnualSalary = getDouble ("Enter Percentage of Salary Invested: % ");

      if (percentOfAnnualSalary < 0.0) {
        throw exception ();
      }
      // Store percentage as decimal equivalent
      percentOfAnnualSalary = percentOfAnnualSalary / 100.0;
      break;
    }
    catch (const exception& e) {
      // Do nothing
    }
  }

  // Get annual rate of return from user 

  while (true) {
    try {
      yearlyRateOfReturn = getDouble ("Enter Annual Rate of Return: % ");

      if (yearlyRateOfReturn < 0.0) {
        throw exception ();
      }
      yearlyRateOfReturn = yearlyRateOfReturn / 100;
      break;
    }
    catch (const exception& e) {
      // Do nothing
    }
  }

  // Get years until retirement from user
  while (true) {
    try {
      yearsUntilRetirement = getInt ("Enter Years Until Retirement: ");
      if (yearsUntilRetirement < 0) {
        throw exception ();
      }
      break;
    }
    catch (const exception& e) {
      // Do nothing
    }

  }

  // Get if user wants results printed in months or years
  while (true) {
    try {
      printResults = getChar ("Print Results [ M)onthly, Y)early ] :");
      if (printResults != UPPER_CASE_MONTH && printResults != LOWER_CASE_MONTH 
        && printResults != UPPER_CASE_YEAR && printResults != LOWER_CASE_YEAR) {
        throw exception ();
      }
      break;
    }
    catch (const exception e) {
      // Do nothing
    }
  }

  // If we get to here all the user input must be valid

  // Print header of the table
  // Then print results of year or month

    printTableResults (annualSalary, percentOfAnnualSalary,
                      yearsUntilRetirement, yearlyRateOfReturn, printResults);

  return EXIT_SUCCESS;
}

/****************************************************************************
Function:     getDouble

Description:  Prints a prompt and takes the users input and returns it as a
              double
Parameters:   prompt - The prompt that the user answers
Returned:     double entered by user
****************************************************************************/
double getDouble (string prompt) {
  double resultantDouble;
  string userInput;
  size_t index;
  cout << prompt;

  getline (cin, userInput);
  resultantDouble = stod (userInput, &index);
  if (userInput.substr (index).length () != 0) {
    throw exception ();
  }
  return resultantDouble;
}


/****************************************************************************
Function:     getInt

Description:  Prints a prompt and takes the users input and returns it as a int
Parameters:   prompt - The prompt that the user answers
Returned:     int entered by user
****************************************************************************/
int getInt (string prompt) {
  int resultantInt;
  string userInput;
  size_t index;
  cout << prompt;

  getline (cin, userInput);
  resultantInt = stoi (userInput, &index);

  if (userInput.substr (index).length () != 0) {
    throw exception ();
  }
  return resultantInt;
}

/****************************************************************************
Function:     getChar

Description:  Prints a prompt and takes the users input and returns it as a char
Parameters:   prompt - The prompt that the user answers
Returned:     char entered by user
****************************************************************************/
char getChar (string prompt) {
  string userInput;
  cout << prompt;

  getline (cin, userInput);
  if (userInput.length () != 1) {
    throw exception ();
  }
  // If here there must on be one character in string array
  // Return that character
  return userInput[0];
}

/****************************************************************************
Function:     getHeader

Description:  Prints a header for the results
Parameters:   timePeriod - The text displayed in for column five
Returned:     None
****************************************************************************/
void printHeader (string timePeriod) {
  cout << setw (BEGGINNING_BALANCE_WIDTH) << "Beginning"
    << setw (INVESTMENT_WIDTH) << "Additional" << setw (MONEY_WIDTH)
    << "Money" << setw (ENDING_BALANCE_WIDTH) << "Ending"
    << setw (TIME_PERIOD_WIDTH) << timePeriod << endl;
  cout << setw (BEGGINNING_BALANCE_WIDTH) << "Balance"
    << setw (INVESTMENT_WIDTH)
    << "Investment" << setw (MONEY_WIDTH) << "Earned"
    << setw (ENDING_BALANCE_WIDTH) << "Balance" << endl;
  cout << "------------" << setw (INVESTMENT_WIDTH) << "----------"
    << setw (MONEY_WIDTH) << "----------" << setw (ENDING_BALANCE_WIDTH)
    << "------------" << setw (TIME_PERIOD_WIDTH) << " ----" << endl;
}

/****************************************************************************
Function:     printTableResults

Description:  Prints the yearly and monthly
Parameters:   annualSalary - The annual salary that the user enters in
              percentOfAnnualSalary - Percent of salary invested that user
              enters in
              YearsUntilRetirment - User enters in years until they retire
              yearlyRateOfReturn - Annual rate of return user enters
              printResults - A char representing monthly or yearly
Returned:     None
****************************************************************************/

void printTableResults (double annualSalary, double percentOfAnnualSalary,
  int yearsUntilRetirement, double yearlyRateOfReturn, char printResults){


    if(printResults == UPPER_CASE_MONTH || printResults == LOWER_CASE_MONTH){
      printHeader(MONTH);
    }
    else{
      printHeader(YEAR);
    }

  double beginningBalance = 0;  // Balance at beginning of each month
  double endingBalance = 0;  // Balance at the end of each month
  double moneyEarned = 0; // Money earned each months
  double amountInvested = (annualSalary * percentOfAnnualSalary)
    / NUMBER_OF_MONTHS; // Money invested each month
  double yearlyMoneyEarned = 0.0;
  double yearlyAmountInvested = 0.0;
  double monthlyBeginningBalance = 0.0;

  for (int count = 1; count <= yearsUntilRetirement * NUMBER_OF_MONTHS;
    count++) {

    moneyEarned = (monthlyBeginningBalance + amountInvested)
      * (yearlyRateOfReturn/NUMBER_OF_MONTHS);
    endingBalance = monthlyBeginningBalance + amountInvested + moneyEarned;
    yearlyMoneyEarned += moneyEarned;
    yearlyAmountInvested += amountInvested;

    if(printResults == 'Y' || printResults == 'y'){
      if(count%NUMBER_OF_MONTHS == 0){
        printTableRow (beginningBalance, yearlyAmountInvested, 
                      yearlyMoneyEarned, endingBalance, count/NUMBER_OF_MONTHS);
        yearlyMoneyEarned = 0.0;
        yearlyAmountInvested = 0.0;
        beginningBalance = endingBalance;
      } 
    }
    else{
        printTableRow (beginningBalance, amountInvested, moneyEarned, endingBalance, 
                  count);
        beginningBalance = endingBalance;
    }
    
    // Update the beginning balance
    monthlyBeginningBalance = endingBalance;
  }


  }

  /****************************************************************************
Function:     printTableRow

Description:  Prints a row of the yearly and monthly results
Parameters:   annualSalary - The annual salary that the user enters in
              percentOfAnnualSalary - Percent of salary invested that user
              enters in
              YearsUntilRetirment - User enters in years until they retire
              yearlyRateOfReturn - Annual rate of return user enters
              printResults - A char representing monthly or yearly
              count - What number month it is
Returned:     None
****************************************************************************/
  void printTableRow (double beginningBalance, double amountInvested, 
                      double moneyEarned, double endingBalance, int count){
    
    
    cout << fixed << setprecision (2) << "$"
      << setw (BEGGINNING_BALANCE_WIDTH - 1) << beginningBalance
      << setw (SPACE_WIDTH) << "" << "$"
      << setw (INVESTMENT_WIDTH - SPACE_WIDTH - 1)
      << amountInvested << setw (SPACE_WIDTH) << "" << "$"
      << setw (MONEY_WIDTH - SPACE_WIDTH - 1) << moneyEarned
      << setw (SPACE_WIDTH) << "" << "$"
      << setw (ENDING_BALANCE_WIDTH - SPACE_WIDTH - 1)
      << endingBalance << setw (TIME_PERIOD_WIDTH) << count << endl;
  }