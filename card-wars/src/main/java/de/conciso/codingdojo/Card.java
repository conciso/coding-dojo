package de.conciso.codingdojo;

import java.util.Objects;

/**
 * Class with Card representation (value and Suit)
 */
public class Card {

    public Card(Values value, Suits suit) {
        this.value = value;
        this.suit = suit;
    }

    public Values getValue() {
        return value;
    }

    public void setValue(Values value) {
        this.value = value;
    }

    public Suits getSuit() {
        return suit;
    }

    public void setSuit(Suits suit) {
        this.suit = suit;
    }

    private Values value;
    private Suits suit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }
}
