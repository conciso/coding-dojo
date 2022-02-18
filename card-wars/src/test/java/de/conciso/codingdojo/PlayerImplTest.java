package de.conciso.codingdojo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PlayerImplTest {

    private PlayerImpl cut = new PlayerImpl();

    @Test
    void initCardsWithNullThrowsNPE() {
        assertThatThrownBy(() -> cut.initCards(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void initializedCardsAreReturnedInTheSameOrder() {
        var cards = List.of(new Card(Values.ACE, Suits.DIAMONDS), new Card(Values.KING, Suits.DIAMONDS));
        cut.initCards(cards);
        assertThat(cut.getCardDeck()).containsExactlyElementsOf(cards);
    }

    @Test
    void initializedCardsWithEmptyCardDeckReturnsEmptyCardDeck() {
        var cards = new ArrayList<Card>();
        cut.initCards(cards);
        assertThat(cut.getCardDeck()).isEmpty();
    }

    @Test
    void whenInitializingWithTwoDistinctCardDecksTheLastOneIsGivenBack() {
        var firstCardDeck = List.of(new Card(Values.ACE, Suits.DIAMONDS), new Card(Values.KING, Suits.DIAMONDS));
        var secondCardDeck = List.of(new Card(Values.QUEEN, Suits.SPADES), new Card(Values.JACK, Suits.CLUBS));
        cut.initCards(firstCardDeck);
        cut.initCards(secondCardDeck);

        assertThat(cut.getCardDeck()).isEqualTo(secondCardDeck);
    }
}