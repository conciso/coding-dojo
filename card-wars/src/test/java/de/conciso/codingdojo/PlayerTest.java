package de.conciso.codingdojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {

    Player cut;

    @BeforeEach
    public void setUp() {
        cut = new PlayerImpl();
    }

    @Nested
    class RevealingOneCard {

        @Test
        public void withOneCardDeckReturnsCard() {
            cut.initCards(createCardDeck(1));
            Card returnValue = cut.getCardDeck().get(0);

            assertThat(cut.getTopOneCard())
                    .isPresent()
                    .hasValue(returnValue);
        }

        @Test
        public void withOneCardDeckReturnsFirstAndNextTimeReturnsEmpty() {
            cut.initCards(createCardDeck(1));
            cut.getTopOneCard();

            assertThat(cut.getTopOneCard()).isNotPresent();
        }

        @Test
        public void withMultiCardDeckReturnsFirstCard() {
            cut.initCards(createCardDeck(2));
            Card returnValue = cut.getCardDeck().get(0);

            assertThat(cut.getTopOneCard())
                    .isPresent()
                    .hasValue(returnValue);
        }

        @Test
        public void withMultiCardDeckRemovesFirstCard() {
            cut.initCards(createCardDeck(2));
            Card notReturned = cut.getCardDeck().get(0);
            cut.getTopOneCard();

            assertThat(cut.getTopOneCard())
                    .isNotEqualTo(Optional.of(notReturned));
        }
    }

    @Nested
    class GivingThreeCards {
        @Test
        public void withThreeCardDeckReturnsFirstThreeCards() {
            cut.initCards(createCardDeck(3));
            List<Card> returnValue = new ArrayList<>(cut.getCardDeck().subList(0, 3));

            assertThat(cut.getTopThreeCards())
                    .isEqualTo(returnValue);
        }

        @Test
        public void withThreeCardDeckRemoveAllCards() {
            cut.initCards(createCardDeck(3));
            List<Card> returnValue = cut.getCardDeck().subList(0, 3);
            cut.getTopThreeCards();

            assertThat(cut.getCardDeck()).isEmpty();
        }

        @Test
        public void withMultiCardDeckReturnsFirstThreeCards() {
            cut.initCards(createCardDeck(10));
            List<Card> returnValue = new ArrayList<>(cut.getCardDeck().subList(0, 3));

            assertThat(cut.getTopThreeCards())
                    .isEqualTo(returnValue);
        }

        @Test
        public void withMultiCardDeckRemoveFirstThreeCards() {
            cut.initCards(createCardDeck(10));
            List<Card> notContained = new ArrayList<>(cut.getCardDeck().subList(0, 3));
            cut.getTopThreeCards();

            assertThat(cut.getCardDeck()).doesNotContainAnyElementsOf(notContained);
        }
    }

    @Nested
    class WinningCards {
        @Test
        public void withNullThrowsException() {
            cut.initCards(createCardDeck(10));
            assertThrows(NullPointerException.class, () -> cut.won(null));
        }

        @Test
        public void withMultipleCardsQueuesCardsAtTheBottomOfStack() {
            cut.initCards(createCardDeck(10));
            List<Card> toAdd = cut.getTopThreeCards();

            cut.won(toAdd);

            List<Card> cardDeck = cut.getCardDeck();
            int sizeOfCardDeck = cardDeck.size();
            assertThat(cardDeck.get(sizeOfCardDeck - 3)).isEqualTo(toAdd.get(0));
            assertThat(cardDeck.get(sizeOfCardDeck - 2)).isEqualTo(toAdd.get(1));
            assertThat(cardDeck.get(sizeOfCardDeck - 1)).isEqualTo(toAdd.get(2));
        }

        @Test
        public void withMultipleCardsFirstCardsStayTheSame() {
            cut.initCards(createCardDeck(10));
            List<Card> toAdd = cut.getTopThreeCards();
            List<Card> cardDeck = new ArrayList<>(cut.getCardDeck());

            cut.won(toAdd);

            assertThat(cut.getCardDeck().subList(0, cut.getCardDeck().size() - 3)).isEqualTo(cardDeck);
        }
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