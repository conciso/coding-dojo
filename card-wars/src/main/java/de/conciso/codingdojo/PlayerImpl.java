package de.conciso.codingdojo;

import java.util.*;

public class PlayerImpl implements Player {

    private LinkedList<Card> cardDeck;

    public PlayerImpl(List<Card> initalCards) {
        if (initalCards.isEmpty()) {
            throw new NoCardsException();
        }
        cardDeck = new LinkedList<>(initalCards);
    }

    @Override
    public Optional<Card> getTopOneCard() {
        return cardDeck.isEmpty() ? Optional.empty() : Optional.of(cardDeck.removeFirst());
    }

    @Override
    public List<Card> getTopThreeCards() {
        return Collections.emptyList();
    }

    @Override
    public void won(List<Card> wonCards) {

    }

    @Override
    public List<Card> getCardDeck() {
        return cardDeck;
    }
}
