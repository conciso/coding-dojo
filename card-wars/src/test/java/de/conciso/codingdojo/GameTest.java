package de.conciso.codingdojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    public static final int NUMBER_OF_CARDS = 52;
    private Game cut;

    @BeforeEach
    public void setUp() {
        cut = new GameImpl(new CardDeckImpl(NUMBER_OF_CARDS));
    }

    @Nested
    class WhenCreatingGame {
        @Test
        public void withNullCardDeckThrowsException() {
            assertThrows(NullPointerException.class, () -> new GameImpl(null));
        }
    }

    @Nested
    class WhenDealingCards {

        @Test
        public void withPlayerOneNullThrowsException() {
            assertThrows(NullPointerException.class, () -> cut.dealCards(null, new PlayerImpl()));
        }

        @Test
        public void withPlayerTwoNullThrowsException() {
            assertThrows(NullPointerException.class, () -> cut.dealCards(new PlayerImpl(), null));
        }

        @Test
        public void withValidPlayersDealsCards() {
            Player one = new PlayerImpl();
            Player two = new PlayerImpl();

            cut.dealCards(one, two);

            assertThat(one.getCardDeck().size()).isEqualTo(NUMBER_OF_CARDS / 2);
            assertThat(two.getCardDeck().size()).isEqualTo(NUMBER_OF_CARDS / 2);
        }
    }

}