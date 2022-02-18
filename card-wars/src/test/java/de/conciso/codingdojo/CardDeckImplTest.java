package de.conciso.codingdojo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CardDeckImplTest {

    private CardDeckImpl cut = new CardDeckImpl();

    @Test
    void cardDeckContainsFiftyTwoCards() {
        assertThat(cut.getCards().size()).isEqualTo(52);
    }

    @Test
    void cardDeckContainsNoDuplicates() {
        assertThat(new HashSet<>(cut.getCards()).size()).isEqualTo(52);
    }

    @Test
    public void afterShuffle52CardsAreNotInTheSameOrderAsBefore() {
        var oldList = new ArrayList(cut.getCards());

        cut.shuffleCards();
        var shuffledCards = cut.getCards();

        assertThat(oldList).doesNotContainSequence(shuffledCards);
    }

    @Test
    public void afterShuffle52AreStillPresent() {
        cut.shuffleCards();
        assertThat(cut.getCards().size()).isEqualTo(52);
    }

}