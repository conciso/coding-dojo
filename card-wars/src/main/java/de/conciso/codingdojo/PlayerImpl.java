package de.conciso.codingdojo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayerImpl implements Player {

    private LinkedList<Card> cardDeck;

    public PlayerImpl() {
        cardDeck = new LinkedList<>();
    }
    @Override
    public void initCards(List<Card> cardDeck) {
        if (cardDeck.isEmpty()) {
            throw new NoCardsException();
        }
        this.cardDeck = new LinkedList<>(cardDeck);
    }

    @Override
    public Optional<Card> getTopOneCard() {
        return cardDeck.isEmpty() ? Optional.empty() : Optional.of(cardDeck.removeFirst());
    }

    @Override
    public List<Card> getTopThreeCards() {
        return cardDeck.size() < 3 ?
                Collections.emptyList() :
                IntStream.range(0, 3).mapToObj((i) -> cardDeck.removeFirst()).collect(Collectors.toList());
    }

    @Override
    public void won(List<Card> wonCards) {
        Objects.requireNonNull(wonCards);
        cardDeck.addAll(wonCards);
    }

    @Override
    public List<Card> getCardDeck() {
        return cardDeck;
    }
}
