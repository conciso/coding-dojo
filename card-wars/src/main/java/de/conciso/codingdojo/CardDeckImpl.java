package de.conciso.codingdojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CardDeckImpl implements CardDeck {

    private final List<Card> cardDeck;

    public CardDeckImpl(int numberOfCards) {
        cardDeck = createCardDeck(numberOfCards);

    }
    public CardDeckImpl(List<Card> cardDeck) {
       this.cardDeck = new ArrayList<>(cardDeck);
    }

    @Override
    public List<Card> getCards() {
        return Collections.unmodifiableList(cardDeck);
    }

    @Override
    public void shuffleCards() {
        final Random random = new Random(System.currentTimeMillis());
        IntStream.range(0, cardDeck.size()).forEach((index) -> exchangeCards(index, random.nextInt(cardDeck.size())));
    }

    private void exchangeCards(int first, int second) {
        Card temp = cardDeck.get(first);
        cardDeck.set(first, cardDeck.get(second));
        cardDeck.set(second, temp);
    }

    private List<Card> createCardDeck(int numberOfCards) {
        return IntStream.range(0, numberOfCards)
                .mapToObj((i) -> new Card(
                        Values.values()[i % Values.values().length],
                        Suits.values()[i % Suits.values().length]
                ))
                .collect(Collectors.toList());
    }
}

