package de.conciso.codingdojo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {

    Player cut;

    @Nested
    class WithConstructor {

        @Test
        public void withoutCardsThrowsException() {
            assertThrows(NoCardsException.class, () -> new PlayerImpl(Collections.emptyList()));
        }

        @Test
        public void withNullThrowsException() {
            assertThrows(NullPointerException.class, () -> new PlayerImpl(null));
        }

        @Test
        public void withMultipleCardsStoresCards() {
            List<Card> deck = List.of(
                    new Card(Values.TWO, Suits.CLUBS),
                    new Card(Values.THREE, Suits.CLUBS),
                    new Card(Values.FOUR, Suits.CLUBS));
            cut = new PlayerImpl(deck);

            assertThat(cut.getCardDeck()).isEqualTo(deck);
        }
    }

    @Nested
    class RevealingOneCard {

        @Test
        public void withOneCardDeckReturnsOneCard() {
            Card returnValue = new Card(Values.TWO, Suits.CLUBS);
            cut = new PlayerImpl(List.of(returnValue));

            assertThat(cut.getTopOneCard())
                    .isPresent()
                    .hasValue(returnValue);
        }

        @Test
        public void withOneCardDeckRemovesCard() {
            Card returnValue = new Card(Values.TWO, Suits.CLUBS);
            cut = new PlayerImpl(List.of(returnValue));
            cut.getTopOneCard();

            assertThat(cut.getTopOneCard()).isNotPresent();
        }

        @Test
        public void withMultiCardDeckReturnsFirstCard() {
            Card returnValue = new Card(Values.TWO, Suits.CLUBS);
            cut = new PlayerImpl(List.of(returnValue, new Card(Values.JACK, Suits.DIAMONDS)));

            assertThat(cut.getTopOneCard())
                    .isPresent()
                    .hasValue(returnValue);
        }

        @Test
        public void withMultiCardDeckRemovesFirstCard() {
            Card notReturned = new Card(Values.TWO, Suits.CLUBS);
            cut = new PlayerImpl(List.of(notReturned, new Card(Values.JACK, Suits.DIAMONDS)));
            cut.getTopOneCard();

            assertThat(cut.getTopOneCard())
                    .isNotEqualTo(Optional.of(notReturned));
        }
    }

}