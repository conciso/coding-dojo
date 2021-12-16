package de.conciso.codingdojo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardDeckTest {
    private CardDeck cut;

    @Nested
    class WhenCreating {
        @Test
        public void withZeroCardsGivenResultsEmptyCardDeck() {
            cut = new CardDeckImpl(0);
            assertThat(cut.getCards()).isEmpty();
        }

        @Test
        public void withTenCardsGivenResultsDeckWithTenCards() {
            cut = new CardDeckImpl(10);
            assertThat(cut.getCards().size()).isEqualTo(10);
        }

        @Test
        public void withNullCardDeckThrowsException() {
            assertThrows(NullPointerException.class, () -> new CardDeckImpl(null));
        }

        @Test
        public void withTenCardsResultsTenCardDeck() {
            CardDeck givenCardDeck = new CardDeckImpl(10);
            cut = new CardDeckImpl(givenCardDeck.getCards());

            assertThat(cut.getCards()).isEqualTo(givenCardDeck.getCards());
            assertThat(cut.getCards()).isNotSameAs(givenCardDeck.getCards());
        }
    }
    @Nested
    class WhenShufflingCards {
        @Test
        public void cardOrderWillChange() {
            cut = new CardDeckImpl(52);
            List<Card> cardsBefore = new ArrayList<>(cut.getCards());

            cut.shuffleCards();

            assertThat(cut.getCards()).isNotEqualTo(cardsBefore);
        }
    }
}