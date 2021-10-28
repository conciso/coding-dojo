Card-War
========
The card war game is a simple card game, where two player play against
each other. For this game a normal card deck with 52 cards is used. Each player
gets the same number of cards mixed randomly.
Each player reveals the top card of his card stack and compares it with
the other one. The highest card wins and the player gets both cards
and puts them at the bottom of his card stack.
If the cards have the same value then each player puts the next three
cards of his stack and reveals the next card. The highest card wins 
and the winning player gets all cards.

Aces are high and suits are ignored.

As Algorithm
------------
1. Shuffle all 52 cards
2. Deal cards
3. Each player shows the top card of his card stack
    1. Different cards
        1. The player with the highest card wins
        2. The winner takes all cards and puts them at the bottom of his card stack
        3. Restart with 3.
    2. Same cards
        1. Each player takes the first 3 cards of his stack and puts them on the table
        2. Restart with 3. until one player is out of cards or has not enough for the next move
4. The player with all cards has won