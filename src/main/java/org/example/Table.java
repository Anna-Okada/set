package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Table {

    private ArrayList<Card> table;

    public Table() {
        table = new ArrayList<>();
    }

    public void addCardToTable(Deck deck) throws OutOfCardsException {
        table.add(deck.deal());
    }

    public int getSize() {
        return table.size();
    }

    public void fillTableWithCards(Deck deck) throws OutOfCardsException {
        for (int i = 0; i < 12; i++) {
            Card card = deck.deal();
            table.add(card);
            card.setTablePosition(i + 1);
        }
    }

    public void addExtraCardsToTable(Deck deck) throws OutOfCardsException {
        for (int i = 0; i < 3; i++) {
            Card card = deck.deal();
            card.setTablePosition(table.size() + 1);
            table.add(card);
        }
    }

    public Card getCardByTablePosition(int tablePosition) throws InvalidTablePositionException {
        Card card = null;
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getTablePosition() == tablePosition) {
                card = table.get(i);
            }
        }
        if (card.equals(null)) {
            throw new InvalidTablePositionException("Invalid position!\n");
        } else {
            return card;
        }
    }

    public void removeCardByTablePosition(int tablePosition) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getTablePosition() == tablePosition) {
                table.remove(i);
            }
        }
    }

    public void replaceCardByTablePosition(Deck deck, int tablePosition) throws OutOfCardsException {
        Card card = deck.deal();
        card.setTablePosition(tablePosition);
        table.set(tablePosition - 1, card);
    }

    public Card getCardByTableIndex(int tableIndex) {
        return table.get(tableIndex);
    }

    // this method rearranges the cards if there were more than 12
    // and 3 were removed so that there are no gaps
    public void rearrangeCards(Table table, int tablePosition) {
        for (int i = 0; i < table.getSize(); i++) {
            table.removeCardByTablePosition(tablePosition);
        }
        for (int i = 0; i < table.getSize(); i++) {
            table.getCardByTableIndex(i).setTablePosition(i + 1);
        }
    }

    // - is it okay to have this method take in a table object?
    public boolean checkForSETs(Table table) throws InvalidTablePositionException {
        Hand hand = new Hand();
        for (int i = 1; i < table.getSize() + 1; i++) {
            for (int j = 2; j < table.getSize() + 1; j++) {
                for (int k = 3; k < table.getSize() + 1; k++) {
                    if (i != j && j != k && i != k) {
                        hand.addCardByTablePosition(table, i);
                        hand.addCardByTablePosition(table, j);
                        hand.addCardByTablePosition(table, k);
                        if (hand.isSet()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // shuffles the cards on the table (reassigns tablePositions)
    public void shuffleTable(Table table) {
        List<Integer> randomNumbers = new ArrayList<>();
        for (int i = 1; i <= table.getSize(); i++) {
            randomNumbers.add(i);
        }
        Collections.shuffle(randomNumbers);
        for (int i = 0; i < table.getSize(); i++) {
            table.getCardByTableIndex(i).setTablePosition(randomNumbers.get(i));
        }
        table.orderCardsByTablePosition();
        table.displayTable();
    }

    public void orderCardsByTablePosition() {
        ArrayList<Card> temp = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            temp.add(table.get(i));
        }
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.size(); j++) {
                if (temp.get(j).getTablePosition() == i + 1) {
                    table.set(i, temp.get(j));
                }
            }
        }
    }

    public void oneOrMoreSETs(Table table) throws InvalidTablePositionException {
        Hand hand = new Hand();
        Set<List<Integer>> positionsSet = new HashSet<>();
        for (int i = 1; i < table.getSize() + 1; i++) {
            for (int j = 2; j < table.getSize() + 1; j++) {
                for (int k = 3; k < table.getSize() + 1; k++) {
                    if (i != j && j != k && i != k) {
                        hand.addCardByTablePosition(table, i);
                        hand.addCardByTablePosition(table, j);
                        hand.addCardByTablePosition(table, k);
                        if (hand.isSet()) {
                            ArrayList<Integer> positions = new ArrayList<>();
                            positions.add(i);
                            positions.add(j);
                            positions.add(k);
                            Collections.sort(positions);
                            positionsSet.add(positions);
                        }
                        hand.clearHand();
                    }
                }
            }
        }
        if (positionsSet.isEmpty()) {
            System.out.println("\nThere are no SETs in here!\n");
        } else if (positionsSet.size() == 1) {
            System.out.println("\nThere actually is a SET here! Can you find it?\n");
        } else {
            System.out.printf("\nThere actually are multiple SETs here! Can you find one?\n", positionsSet.size());
            System.out.println();
        }
    }

    public void findSET(Table table) throws InvalidTablePositionException {
        Hand hand = new Hand();
        Set<List<Integer>> positionsSet = new HashSet<>();
        for (int i = 1; i < table.getSize() + 1; i++) {
            for (int j = 2; j < table.getSize() + 1; j++) {
                for (int k = 3; k < table.getSize() + 1; k++) {
                    if (i != j && j != k && i != k) {
                        hand.addCardByTablePosition(table, i);
                        hand.addCardByTablePosition(table, j);
                        hand.addCardByTablePosition(table, k);
                        if (hand.isSet()) {
                            ArrayList<Integer> positions = new ArrayList<>();
                            positions.add(i);
                            positions.add(j);
                            positions.add(k);
                            Collections.sort(positions);
                            positionsSet.add(positions);
                        }
                        hand.clearHand();
                    }
                }
            }
        }

        if (positionsSet.isEmpty()) {
            System.out.println("\nThere are no SETs in here!\n");
        } else if (positionsSet.size() == 1) {
            System.out.println("\nThere was 1 SET! Here it is:\n");
        } else {
            System.out.printf("\nThere were %d SETs! Here they are:\n", positionsSet.size());
            System.out.println();
        }

        for (List<Integer> element : positionsSet) {
            hand.addCardByTablePosition(table, element.get(0));
            hand.addCardByTablePosition(table, element.get(1));
            hand.addCardByTablePosition(table, element.get(2));
            hand.displayHand();
            hand.clearHand();
        }

    }

    public void displayTable() {
        for (int i = 0; i < table.size(); i += 3) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%-12s        ", table.get(i + j).getShapeAsString());
            }
            System.out.println();
            for (int j = 0; j < 3; j++) {
                System.out.printf("%-12d", table.get(i + j).getTablePosition());
            }
            System.out.println();
            System.out.println();
        }
    }

}