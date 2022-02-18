package de.conciso.codingdojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PlayerImpl implements Player {
    private List<Card> cardDeck;

    @Override
    public void initCards(List<Card> cardDeck) {
        Objects.requireNonNull(cardDeck);
        this.cardDeck = new ArrayList<>(cardDeck);
    }

    @Override
    public Optional<Card> getTopOneCard() {
        return Optional.empty();
    }

    @Override
    public List<Card> getTopThreeCards() {
        return null;
    }

    @Override
    public void won(List<Card> wonCards) {

    }

    @Override
    public List<Card> getCardDeck() {
        return this.cardDeck;
    }
}
