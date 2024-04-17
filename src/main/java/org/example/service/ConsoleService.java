package org.example.service;

import org.example.exception.InvalidTablePositionException;
import org.example.exception.OutOfCardsException;
import org.example.model.Deck;
import org.example.model.Table;

import java.util.Scanner;

public class ConsoleService {

    Scanner scanner = new Scanner(System.in);

    public void printMainMenu() {
        System.out.println("If there are no SETs, enter '+' to add three additional cards to the table."
                + "\nIf you need help finding a SET, type 'help'."
                + "\nIf you would like to shuffle the cards, type 'shuffle'."
                + "\nIf you would like to select a hand, type 'select'."
                + "\nIf you would like to quit the game, type 'quit'."
                + "\nIf at any point you would like to see this menu again, type 'menu'.\n");
    }

    public String promptForMainMenuSelection() {
        // if single player, then single player prompt
        // if two player, then two player prompt
        // keep track of player turn so prompt is correct
        String userInput = scanner.nextLine();
        String menuSelection = "";
        if (userInput.equals("+")) {
            menuSelection = "+";
        } else if (userInput.trim().toLowerCase().equals("help")) {
            menuSelection = "help";
        } else if (userInput.trim().toLowerCase().equals("shuffle")) {
            menuSelection = "shuffle";
        } else if (userInput.trim().toLowerCase().equals("select")) {
            menuSelection = "select";
        } else if (userInput.trim().toLowerCase().equals("menu")) {
            menuSelection = "menu";
        } else if (userInput.trim().toLowerCase().equals("quit")) {
            menuSelection = "quit";
        }
        return menuSelection;
    }

    public int promptForPlayerModeSelection() {
        int menuSelection;
        System.out.print("Would you like to play 1 or 2 player SET? (Enter 1 or 2): ");
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public String[] promptForPlayerNames() {
        String[] playerNames = new String[2];
        String p1 = "";
        String p2 = "";
        System.out.print("Player 1, please enter your name: ");
        while (true) {
            p1 = scanner.nextLine();
            // do not permit blank names
            if (p1.trim().length() == 0) {
                System.out.println("Your name cannot be blank! Please try again: ");
                continue;
            }
            playerNames[0] = p1;
            break;
        }
        System.out.print("Player 2, please enter your name: ");
        while (true) {
            p2 = scanner.nextLine();
            if (p2.trim().length() == 0) {
                System.out.println("Your name cannot be blank! Please try again: ");
                continue;
            }
            playerNames[1] = p2;
            break;
        }
        return playerNames;
    }

    public Integer[] promptForCardsSelection() {
        System.out.print("\nPlease enter the position numbers of three cards (comma-separated): ");
        String[] userInput = scanner.nextLine().split(",");
        Integer[] cardsSelection = new Integer[3];
        try {
            // create variables to store user's numbers
            int position1 = Integer.valueOf(userInput[0]);
            int position2 = Integer.valueOf(userInput[1]);
            int position3 = Integer.valueOf(userInput[2]);
            // set each position of cardSelection array to value user entered
            cardsSelection[0] = position1;
            cardsSelection[1] = position2;
            cardsSelection[2] = position3;
        } catch (NumberFormatException e) {
            System.out.print("\nInvalid entry. Please try again: ");
        }
        return cardsSelection;
    }

    public String promptPlayAgain() {
        System.out.print("\nWould you like to play again? ( Y / N ): ");
        String selection = scanner.nextLine();
        if (selection.trim().toUpperCase().equals("Y")) {
            selection = "yes";
        } else if (selection.trim().toUpperCase().equals("N")) {
            selection = "no";
        }
        return selection;
    }

    public void displayMessage(String s) {
        System.out.print(s);
    }
}
