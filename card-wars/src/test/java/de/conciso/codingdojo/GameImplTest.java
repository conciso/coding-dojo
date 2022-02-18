package de.conciso.codingdojo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameImplTest {

    private GameImpl cut = new GameImpl();


    @Test
    void dealsCardsEvenlyBetweenBothPlayers() {
        Player one = new PlayerImpl();
        Player two = new PlayerImpl();

        cut.dealCards(one, two);

        assertThat(one.getCardDeck().size()).isEqualTo(two.getCardDeck().size());
    }

    @Test
    void bothPlayerHaveTwentySixCards() {
        Player one = new PlayerImpl();
        Player two = new PlayerImpl();

        cut.dealCards(one, two);

        assertThat(one.getCardDeck().size()).isEqualTo(26);
        assertThat(two.getCardDeck().size()).isEqualTo(26);
    }

}