package de.conciso.codingdojo;

import java.util.ArrayList;
import java.util.Iterator;

public class GameImpl implements Game {
    @Override
    public void dealCards(Player one, Player two) {
        var cardDeck = new CardDeckImpl();
        var cardDeckForPlayerOne = new ArrayList<Card>();
        var cardDeckForPlayerTwo = new ArrayList<Card>();

        var iter = cardDeck.getCards().iterator();
        while(iter.hasNext()) {
            cardDeckForPlayerOne.add(iter.next());
            cardDeckForPlayerTwo.add(iter.next());
        }

        //Robins
        /*
        IntStream.range(0, cardDeck.getCards().size()).foreach...index ->
         */
        one.initCards(cardDeckForPlayerOne);
        two.initCards(cardDeckForPlayerTwo);
    }

    @Override
    public boolean doMove(Player one, Player two) {
        return false;
    }

    @Override
    public Player getWinner(Player one, Player two) {
        return null;
    }
}
