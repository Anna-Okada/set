package org.example;

import org.example.exception.InvalidTablePositionException;
import org.example.exception.OutOfCardsException;
import org.example.model.Deck;
import org.example.model.Hand;
import org.example.model.Table;
import org.example.service.ConsoleService;

public class Main {

    // variables for setting color values
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    // variable to keep track of SETs collected and incorrect guesses
    private int setCount = 0;
    private int p1SetCount = 0;
    private int p2SetCount = 0;
    private int unassistedSetCount = 0;
    private int p1UnassistedSetCount = 0;
    private int p2UnassistedSetCount = 0;
    private int incorrectGuesses = 0;
    private int p1IncorrectGuesses = 0;
    private int p2IncorrectGuesses = 0;
    private boolean usedHelp = false;

    // variables to keep track of turns
    private boolean p1Turn = true;
    private boolean twoPlayerMode = false;
    private String p1 = "";
    private String p2 = "";

    private boolean playAgain = true;
    private boolean gameCompleted = false;
    private boolean innerMenu = true;

    // variables to reference other objects
    private Deck deck;
    private Table table;
    private Hand hand;

    ConsoleService consoleService = new ConsoleService();

    public static void main(String[] args) {
        Main application = new Main();
        application.run();
    }

    private void initializeGame() {
        deck = new Deck(true); // create shuffled deck
        table = new Table(); // create empty table
        // fill table with 12 cards to start game
        try {
            table.fillTableWithCards(deck);
        } catch (OutOfCardsException e) {
            System.out.println(e.getMessage());
        }
        table.displayTable();
    }

    private void handleShuffle() {
        table.shuffleTable(table);
    }

    private void handleHelp() {
        usedHelp = true;
        // show the SETs that are on the table
        try {
            table.findSET(table);
        } catch (InvalidTablePositionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleAddCards() {
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

    private void handleSelectCards() {
        Integer[] cardsSelection = consoleService.promptForCardsSelection();

        int position1 = cardsSelection[0];
        int position2 = cardsSelection[1];
        int position3 = cardsSelection[2];

        hand = new Hand();

        // select those cards by table positions and add to hand
        try {
            hand.addCardByTablePosition(table, position1);
            hand.addCardByTablePosition(table, position2);
            hand.addCardByTablePosition(table, position3);
        } catch (InvalidTablePositionException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        hand.displayHand();
        if (hand.isSet()) {
            System.out.println("You found a SET!");
            rearrangeTable();
            updateStats();
            switchPlayerTurn();
        } else {
            System.out.println("Sorry, that's not a SET. Please try again!");
            table.displayTable();
            incrementIncorrectGuesses();
        }
    }

    private void incrementIncorrectGuesses() {
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

    private void switchPlayerTurn() {
        // switch player1Turn to its opposite setting
        if (p1Turn == true) {
            p1Turn = false;
        } else {
            p1Turn = true;
        }
    }

    private void updateStats() {
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
    }

    private void rearrangeTable() {
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
    }

    private void getCommand() {
        if (twoPlayerMode) {
            if (p1Turn) {
                System.out.printf("Please enter your command, %s: ", p1);
            } else {
                System.out.printf("Please enter your command, %s: ", p2);
            }
        } else {
            System.out.print("Please enter your command: ");
        }
    }

    private void getPlayerNames() {
        twoPlayerMode = true; // should this be here?
        String[] playerNames = consoleService.promptForPlayerNames();
        p1 = playerNames[0];
        p2 = playerNames[1];
    }

    private void getStats() {
        // if game was completed
        // System.out.println("Game Over! There are no more SETs to be found.");
        if (twoPlayerMode) {
            if (p1UnassistedSetCount == p2UnassistedSetCount) {
                System.out.println("\nIt's a tie!");
            } else {
                System.out.printf("\n%s wins!", p1UnassistedSetCount > p2UnassistedSetCount ? p1 : p2);
            }
            System.out.printf("\n%s made %d incorrect guess" + (p1IncorrectGuesses == 1 ? ", " : "es, ")
                    + "found %d SET" + (p1UnassistedSetCount == 1 ? " " : "s ")
                    + " without any help from the computer, and found %d total SET"
                    + (p1SetCount == 1 ? " " : "s ") + "this game!", p1, p1IncorrectGuesses, p1UnassistedSetCount, p1SetCount);
            System.out.printf("\n%s made %d incorrect guess" + (p2IncorrectGuesses == 1 ? ", " : "es, ")
                    + "found %d SET" + (p2UnassistedSetCount == 1 ? " " : "s ")
                    + " without any help from the computer, and found %d total SET"
                    + (p2SetCount == 1 ? " " : "s ") + "this game!", p2, p2IncorrectGuesses, p2UnassistedSetCount, p2SetCount);
        } else {
            System.out.printf("\nYou made %d incorrect guess" + (incorrectGuesses == 1 ? ", " : "es, ")
                    + "found %d SET" + (unassistedSetCount == 1 ? " " : "s ")
                    + "without any help from the computer, and found %d total SET"
                    + (setCount == 1 ? " " : "s ") + "this game!", incorrectGuesses, unassistedSetCount, setCount);
        }
    }

    private void handleCompleteGame() {
        System.out.println("Game over! There are no more SETs to be found.");
        getStats();
    }

    private void handlePlayAgain() {
        String selection = consoleService.promptPlayAgain();
        if (selection.equals("no")) {
            playAgain = false;
        } else if (selection.equals("yes")) {
            playAgain = true;
        }
    }
    private void handlePlayerMode() {
        int playerModeSelection = -1;
        while (playerModeSelection != 0) {
            // prompt player to select between single- and two-player modes
            playerModeSelection = consoleService.promptForPlayerModeSelection();
            if (playerModeSelection == 1) {
                initializeGame();
                consoleService.printMainMenu();
                getCommand();
                break;
            } else if (playerModeSelection == 2) {
                getPlayerNames();
                initializeGame();
                consoleService.printMainMenu();
                getCommand();
                break;
            } else {
                System.out.println("SET can only be played in single-player or two-player modes. Please try again!");
            }
        }
    }
    private void handleMainMenu() {
        String mainMenuSelection = "";
        while (mainMenuSelection != null) {
            mainMenuSelection = consoleService.promptForMainMenuSelection();
            if (mainMenuSelection.equals("+")) {
                handleAddCards();
                getCommand();
            } else if (mainMenuSelection.equals("help")) {
                handleHelp();
                getCommand();
            } else if (mainMenuSelection.equals("shuffle")) {
                handleShuffle();
                getCommand();
            } else if (mainMenuSelection.equals("select")) {
                try {
                    handleSelectCards();
                    getCommand();
                } catch (NullPointerException e) {
                    //  user has entered invalid input
                }
            } else if (mainMenuSelection.equals("menu")) {
                consoleService.printMainMenu();
                getCommand();
            } else if (mainMenuSelection.equals("quit")) {
                getStats();
                handlePlayAgain();
                innerMenu = false;
                break;
            } else {
                consoleService.displayMessage("Invalid entry. Please try again: ");
            }
        }
    }
    private void run() {

        while (playAgain) {

            handlePlayerMode();
            // while deck has cards left or table has SETs to be found, continue game play
            // unless user selects 'quit'
            while (innerMenu) {
                try {
                    if (!(deck.hasCards() || table.checkForSETs(table) == true)) {
                        gameCompleted = true;
                        break;
                    }
                } catch (InvalidTablePositionException e) {
                    System.out.println(e.getMessage());
                }
                // prompt player to select their command from main menu
                handleMainMenu();
            }

        }

        if (gameCompleted) {
            handleCompleteGame();
        }

    }

}