package org.example;

import java.util.ArrayList;

public class Hand {

    // Attributes
    private ArrayList<Card> hand; // Cards in hand

    // Default constructor
    public Hand() {
        hand = new ArrayList<>(); // An array list of cards
    }

    // Methods
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    // get a card by its tablePosition and add to hand
    public void addCardByTablePosition(Table table, int tablePosition) throws InvalidTablePositionException {
        if (tablePosition < 1 || tablePosition > table.getSize()) {
            throw new InvalidTablePositionException("Invalid position!\n");
        } else {
            hand.add(table.getCardByTablePosition(tablePosition));
        }
    }

    public int getSize() {
        return hand.size();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Card removeCardFromHand() {
        Card card = hand.remove(hand.size() - 1);
        return card;
    }

    public Card getCardFromHandByIndex(int index) {
        Card card = hand.get(index);
        return card;
    }

    public void displayHand() {
        for (int i = 0; i < 3; i++) {
            System.out.printf("%-12s        ", hand.get(i).getShapeAsString());
        }
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.printf("%-12d", hand.get(i).getTablePosition());
        }
        System.out.println();
        System.out.println();
    }

    public void clearHand() {
        hand.clear();
    }

    // logic for determining if cards make a SET
    public boolean isSet() {
        boolean value = false;
        boolean shape = false;
        boolean color = false;
        boolean shading = false;
        Card card1 = hand.get(0);
        Card card2 = hand.get(1);
        Card card3 = hand.get(2);
        // if values are all the same
        if (card1.getValue() == card2.getValue() && card2.getValue() == card3.getValue()) {
            value = true;
        }
        // if suits are all the same
        if (card1.getShape() == card2.getShape() && card2.getShape() == card3.getShape()) {
            shape = true;
        }
        // if colors are all the same
        if (card1.getColor() == card2.getColor() && card2.getColor() == card3.getColor()) {
            color = true;
        }
        // if shadings are all the same
        if (card1.getShading() == card2.getShading() && card2.getShading() == card3.getShading()) {
            shading = true;
        }
        // if values are all different
        if (card1.getValue() != card2.getValue() && card2.getValue() != card3.getValue()
                && card1.getValue() != card3.getValue()) {
            value = true;
        }
        // if shapes are all different
        if (card1.getShape() != card2.getShape() && card2.getShape() != card3.getShape()
                && card1.getShape() != card3.getShape()) {
            shape = true;
        }
        // if colors are all different
        if (card1.getColor() != card2.getColor() && card2.getColor() != card3.getColor()
                && card1.getColor() != card3.getColor()) {
            color = true;
        }
        // if shadings are all different
        if (card1.getShading() != card2.getShading() && card2.getShading() != card3.getShading()
                && card1.getShading() != card3.getShading()) {
            shading = true;
        }
        if (value == true && shape == true && color == true && shading == true) {
//			hand.clear();
            return true;
        }
        hand.clear();
        return false;
    }

}