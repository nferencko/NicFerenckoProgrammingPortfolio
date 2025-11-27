# Nic Ferencko Programming Portfolio

The projects in this portfolio are some of the most interesting I've worked on.  I hope that they demonstrate my ability to both solve challenging problems and to write clean code.  My primary programming experiences have been using Java and C++, but I have also done some programming using Python. Some of the skills I've developed through these projects are object oriented design, inheritance, polymorphism, exception handling, and creative problem solving.

## BlackJack (Java)

- With an assigned class partner, we designed and implemented a basic game of blackjack.  In our console application, a single user plays against a computer dealer.
-  **My role on this project:** I wrote several classes using object-oriented design principles.  Specifically, the classes I wrote for the project were the [Card](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/BlackJack/src/Card.java) class (representing a single Card), the [DeckOfCards](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/BlackJack/src/DeckOfCards.java) class (a collection of Cards backed by an ArrayList), and the [BlackJackHand](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/BlackJack/src/BlackJackHand.java) class (another collection of cards representing a player’s hand).  Additionally, I helped my partner with some aspects of the [Actions](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/BlackJack/src/Actions.java) class (the program driver).
-  While the written program was fully functional, there were some elements of a typical BlackJack game that were left out (e.g. doubling down and splitting). Additionally, I am currently learning Java Swing and would like to create a GUI version of this program and element and also incorporate sound.

## Probability Simulator (Java)

- With three partners, I designed and wrote a program to run several different Monte Carlo probability simulations.  In this program, the user could choose from a menu of experiments (e.g. Monty Hall simulation, card simulations, or picking marbles from a bag ).  Then, the experiment would be run several times with the results recorded.  The program would output the experimental results along with the theoretical results for comparison.
- **My role on this project:** In this project, I wrote the main menu (driver) along with the card simulations.  In my card simulations, I was able to reuse many of the classes I had written for the BlackJack game. Specifically, the classes I wrote in this project were [BlackJackHand.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/BlackJackHand.java), [Card.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/Card.java), [CardMenu.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/CardMenu.java), [CardSimulations.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/CardSimulations.java), [DeckOfCards.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/DeckOfCards.java), [MainMenu.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/MainMenu.java), and [PokerHand.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/PokerHand.java).

## a01-exponentialgrowth (C++)

- **Individual Project** One of my first C++ projects. In this [project](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/tree/main/a01-exponentialgrowth-nferencko/src) I learned how to use functions, grab user input, format output, and use exceptions in C++. I like to call this program an investment calculator. This console application prompts the user to enter investment information and outputs the value of their account over the specified time period. 

## a05-InBetweenHand (C++)

- **Individual Project:** A console application that allows a group of users to play the card game In Between Hand against each other and/or a group of AI players.  The design of the program was largely provided by the instructor, but it was left to me to write [all the classes](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/tree/main/a05-inbetween-nferencko/src) along with the game driver.  Completing the project required an understanding of inheritance and polymorphism along with the C++ STL.


