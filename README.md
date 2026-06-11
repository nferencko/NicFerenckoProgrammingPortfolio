# Nic Ferencko Programming Portfolio

The projects in this portfolio are among the most interesting I've worked on.  I hope that they demonstrate my ability to both solve challenging problems and to write clean code.  My primary programming experiences have been using Java and C++, but I also have varying levels of experience with C, Python, JavaScript/TypeScript, and Erlang. Some od the skills I've developed through working on these projects are object oriented design, inheritance, polymorphism, exception handling, and creative problem solving.

## Trivia Maze Game (Java)

- With two partners, we designed and wrote a Trivia Maze GUI application.  In this program, a player navigates their way through a maze of closed and locked doors in an attempt ot reach the exit room.  To open a closed door, the user must correctly answer a trivia question.  If they answer incorrectly, the closed door is locked forever.  A player wins the game if they reach the exit. A player loses if all possible paths to the exit room are permanently blocked.
- **My Role on This Project:** In this project, I had a large role in writing and designing the model code (Maze, Room, Player, etc), the save/load functionality of the game, and the JUnit tests for the model classes.  Additionally, I helped with the view/controller code and the DataManager class (connection to an SQLite database).

## BlackJack (Java)

- This was a UW Hackaton project.  For the project, I wrote a 1-player GUI BlackJack application.
-  **Individual Project:** I wrote the model classes using object-oriented design principles.  Specifically, the model classes I wrote for the project were the [Card](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/BlackJack/src/Card.java) class (representing a single Card), the [DeckOfCards](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/BlackJack/src/DeckOfCards.java) class (a collection of Cards backed by an ArrayList), and the [BlackJackHand](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/BlackJack/src/BlackJackHand.java) class (another collection of cards representing a player’s hand).  Additionally, I wrote view/controller classes using Java Swing to implement the user interface
-  While the written program is fully functional, there were some elements of a typical BlackJack game that were left out (e.g. split). Additionally, since the game needed to be developed in a 24-hour time-period, I put all my classes in a single package instead separating classes into model/view/controller packages as is often done.

## Probability Simulator (Java)

- With three partners, I designed and wrote a program to run several different Monte Carlo probability simulations.  In this program, the user could choose from a menu of experiments (e.g. Monty Hall simulation, card simulations, or picking marbles from a bag ).  Then, the experiment would be run several times with the results recorded.  The program would output the experimental results along with the theoretical results for comparison.
- **My Role on This Project:** In this project, I wrote the main menu (driver) along with the card simulations.  In my card simulations, I was able to reuse many of the classes I had written for the BlackJack game. Specifically, the classes I wrote in this project were [BlackJackHand.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/BlackJackHand.java), [Card.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/Card.java), [CardMenu.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/CardMenu.java), [CardSimulations.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/CardSimulations.java), [DeckOfCards.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/DeckOfCards.java), [MainMenu.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/MainMenu.java), and [PokerHand.java](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/blob/main/Probability%20Simulator/src/PokerHand.java).

## a01-exponentialgrowth (C++)

- **Individual Project:** One of my first C++ projects. Through this [project](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/tree/main/a01-exponentialgrowth-nferencko/src) I learned how to use functions, grab user input, format output, and use exceptions in C++. I like to call this program an investment calculator. This console application prompts the user to enter investment information and outputs the value of their account over the specified time period. 

## a05-InBetweenHand (C++)

- **Individual Project:** A console application that allows a group of users to play the card game "In Between Hand" against each other and/or a group of AI players.  The design of the program was largely provided by the instructor, but it was left to me to write [all the classes](https://github.com/nferencko/NicFerenckoProgrammingPortfolio/tree/main/a05-inbetween-nferencko/src) along with the game driver.  Completing the project required an understanding of inheritance and polymorphism along with the C++ STL.




