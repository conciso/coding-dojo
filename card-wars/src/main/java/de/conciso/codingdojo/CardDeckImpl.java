package de.conciso.codingdojo;

import java.util.*;

public class CardDeckImpl implements CardDeck {

    List<Card> cards = new ArrayList<>();

    public CardDeckImpl() {
        for (Suits suit : Suits.values()) {
            for (Values value : Values.values()) {
                this.cards.add(new Card(value, suit));
            }
        }
    }

    @Override
    public List<Card> getCards() {
        return this.cards;
    }

    @Override
    public void shuffleCards() {
        Collections.shuffle(cards, new Random(System.currentTimeMillis() + 42));
    }
}
