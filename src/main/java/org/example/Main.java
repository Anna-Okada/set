package org.example;

import java.util.Scanner;

public class Main {

    // variables for setting color values
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Deck deck = new Deck(true); // create shuffled deck
        Table table = new Table(); // create empty table
        // fill table with 12 cards to start game
        try {
            table.fillTableWithCards(deck);
        } catch (OutOfCardsException e) {
            System.out.println(e.getMessage());
        }

        // keep track of SETs collected and incorrect guesses
        int setCount = 0;
        int p1SetCount = 0;
        int p2SetCount = 0;
        int unassistedSetCount = 0;
        int p1UnassistedSetCount = 0;
        int p2UnassistedSetCount = 0;
        int incorrectGuesses = 0;
        int p1IncorrectGuesses = 0;
        int p2IncorrectGuesses = 0;
        boolean usedHelp = false;
        boolean p1Turn = true;
        boolean twoPlayerMode = false;
        String p1 = "";
        String p2 = "";

        System.out.print("Would you like to play 1 or 2 player SET? (Enter 1 or 2): ");
        String response = scanner.nextLine();

        if (Integer.valueOf(response) == 2) {
            System.out.print("Player 1, please enter your name: ");
            while (true) {
                p1 = scanner.nextLine();
                // do not permit blank names
                if (p1.trim().length() == 0) {
                    System.out.println("Your name cannot be blank! Please try again: ");
                    continue;
                }
                break;
            }
            System.out.print("Player 2, please enter your name: ");
            while (true) {
                p2 = scanner.nextLine();
                if (p2.trim().length() == 0) {
                    System.out.println("Your name cannot be blank! Please try again: ");
                    continue;
                }
                break;
            }
            twoPlayerMode = true;
        }

        System.out.println("To select a hand, enter the position numbers of three cards (comma-separated)."
                + "\nIf there are no SETs, enter '+' to add three additional cards to the table."
                + "\nIf you need help finding a SET, type 'help'."
                + "\nIf you would like to shuffle the cards, type 'shuffle'.\n");

        // display table
        table.displayTable();

        try {
            while (deck.hasCards() || table.checkForSETs(table) == true) {

                Hand hand = new Hand();

                // while the hand has not been filled with 3 cards
                // present various options for player
                while (hand.getSize() != 3) {

                    if (twoPlayerMode) {
                        if (p1Turn) {
                            System.out.printf("Please enter your command, %s: ", p1);
                        } else {
                            System.out.printf("Please enter your command, %s: ", p2);
                        }
                    } else {
                        System.out.printf("Please enter your command: ");
                    }

                    String[] userInput = scanner.next().split(",");
                    // if user enters "+"
                    if (userInput[0].equals("+")) {
                        // if there are SETs on the table
                        try {
                            if (!table.checkForSETs(table)) {
                                // add 3 additional cards to the table and display
                                try {
                                    table.addExtraCardsToTable(deck);
                                    table.displayTable();
                                } catch (OutOfCardsException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            // otherwise, let user know that there is a SET (or SETs)
                            else {
                                table.oneOrMoreSETs(table);
                            }
                        } catch (InvalidTablePositionException e) {
                            System.out.println(e.getMessage());
                        }

                    }
                    // if user enters "help"
                    else if (userInput[0].toLowerCase().equals("help")) {
                        usedHelp = true;
                        // show the SETs that are on the table
                        try {
                            table.findSET(table);
                        } catch (InvalidTablePositionException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    // if user enters "shuffle"
                    else if (userInput[0].toLowerCase().equals("shuffle")) {
                        System.out.println();
                        table.shuffleTable(table);
                    }
                    // else if the input is 3 valid numbers
                    else if (userInput.length == 3) {
                        try {
                            // create variables to store user's numbers
                            int position1 = Integer.valueOf(userInput[0]);
                            int position2 = Integer.valueOf(userInput[1]);
                            int position3 = Integer.valueOf(userInput[2]);

                            System.out.println();

                            // select those cards by table positions and add to hand
                            try {
                                hand.addCardByTablePosition(table, position1);
                                hand.addCardByTablePosition(table, position2);
                                hand.addCardByTablePosition(table, position3);
                            } catch (InvalidTablePositionException e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid entry. Please try again.");
                        }
                    } else {
                        System.out.println("Invalid entry. Please try again.");
                    }
                }

                // display hand
                hand.displayHand();

                // if hand makes up a SET
                if (hand.isSet()) {
                    // print message letting user know they found a SET
                    System.out.println("You found a SET!\n");
                    // if the table has more than 12 cards
                    if (table.getSize() > 12 || !deck.hasCards()) {
                        // remove the SET cards and rearrange the table but don't add any new cards
                        table.rearrangeCards(table, hand.getCardFromHandByIndex(0).getTablePosition());
                        table.rearrangeCards(table, hand.getCardFromHandByIndex(1).getTablePosition());
                        table.rearrangeCards(table, hand.getCardFromHandByIndex(2).getTablePosition());
                    }
                    // otherwise, replace the cards that made up the SET
                    // with new cards from the deck
                    else {
                        try {
                            table.replaceCardByTablePosition(deck, hand.getCardFromHandByIndex(0).getTablePosition());
                            table.replaceCardByTablePosition(deck, hand.getCardFromHandByIndex(1).getTablePosition());
                            table.replaceCardByTablePosition(deck, hand.getCardFromHandByIndex(2).getTablePosition());
                        } catch (OutOfCardsException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    // display the updated table
                    table.displayTable();
                    // increment the count of SETs for appropriate player
                    if (twoPlayerMode) {
                        if (p1Turn) {
                            p1SetCount++;
                        } else {
                            p2SetCount++;
                        }
                    } else {
                        setCount++;
                    }
                    // if user didn't type "help"
                    if (twoPlayerMode) {
                        if (!usedHelp && p1Turn) {
                            // increment count
                            p1UnassistedSetCount++;
                        } else if (!usedHelp && !p1Turn) {
                            p2UnassistedSetCount++;
                        }
                    } else {
                        if (!usedHelp) {
                            unassistedSetCount++;
                        }
                    }
                    // reset usedHelp to false
                    usedHelp = false;
                    // switch player1Turn to its opposite setting
                    if (p1Turn == true) {
                        p1Turn = false;
                    } else {
                        p1Turn = true;
                    }
                }
                // otherwise, let user know their cards did not make up a SET
                else {
                    System.out.println("Sorry, that's not a SET. Please try again!\n");
                    // display table
                    table.displayTable();
                    // increment the number of incorrect guesses for the appropriate player
                    if (twoPlayerMode) {
                        if (p1Turn) {
                            p1IncorrectGuesses++;
                        } else {
                            p2IncorrectGuesses++;
                        }
                    } else {
                        incorrectGuesses++;
                    }
                }

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (InvalidTablePositionException e) {
            System.out.println(e.getMessage());
        }

        if (twoPlayerMode) {
            System.out.println("Game Over! There are no more SETs to be found.");
            if (p1UnassistedSetCount == p2UnassistedSetCount) {
                System.out.println("It's a tie!");
            } else {
                System.out.printf("%s wins!", p1UnassistedSetCount > p2UnassistedSetCount ? p1 : p2);
            }
            System.out.printf("\n%s made %d incorrect guesses, found %d SETs without any help from the computer, "
                            + "and found %d total SETs this game!",
                    p1, p1IncorrectGuesses, p1UnassistedSetCount, p1SetCount);
            System.out.printf(
                    "\n%s made %d incorrect guesses, found %d SETs without any help from the computer, "
                            + "and found %d total SETs this game!",
                    p2, p2IncorrectGuesses, p2UnassistedSetCount, p2SetCount);
        } else {
            System.out.printf("Game Over! There are no more SETs to be found."
                    + "\nYou made %d incorrect guesses, found %d SETs without any help from the computer, "
                    + "and found %d total SETs this game!", incorrectGuesses, unassistedSetCount, setCount);
        }

    }

}