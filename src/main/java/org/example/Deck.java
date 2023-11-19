package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck;
    private int next; // Holds position of next card to be dealt

    // creates deck
    public Deck(boolean startShuffled) {
        deck = new ArrayList<Card>();
        int tablePosition = -1;
        for (int value = 1; value < 4; value++) {
            for (int shape = 1; shape < 4; shape++) {
                for (int color = 1; color < 4; color++) {
                    for (int shading = 1; shading < 4; shading++) {
                        deck.add(new Card(value, shape, color, shading, tablePosition));
                    }
                }
            }
        }

        next = 0;

        if (startShuffled == true) {
            shuffle();
        }
    }

    // Methods
    public void shuffle() {
        Collections.shuffle(deck);
    }

    public boolean hasCards() {
        if (next > 80) {
            return false;
        }
        return true;
    }

    // use try catch whenever dealing a card
    public Card deal() throws OutOfCardsException {
        if (next > 80) {
            throw new OutOfCardsException("No more cards in the deck!\n");
        }
        Card card = deck.get(next);
        next++;
        return card;
    }

}