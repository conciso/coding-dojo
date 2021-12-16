package de.conciso.codingdojo;

import java.util.Objects;

public record GameImpl(CardDeck cardDeck) implements Game {

    public GameImpl {
        Objects.requireNonNull(cardDeck);
    }

    @Override
    public void dealCards(Player one, Player two) {
        Objects.requireNonNull(one);
        Objects.requireNonNull(two);
        cardDeck.shuffleCards();

        for (Card card :cardDeck.getCards()) {

        }
    }

    @Override
    public boolean doMove(Player one, Player two) {
        return false;
    }

    @Override
    public Player getWinner(Player one, Player two) {
        return null;
    }
}
