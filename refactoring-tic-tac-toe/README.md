# Tic Tac Toe Refactoring Kata

The exercise in this kata is the refactoring of the tic tac toe game. The story is the following:

The programmer who implemented this game has left the company and the game has to be ready in the
next few days. In his last phone call the programmer said that he was nearly ready with it and there
are only some minor changes and tasks left.

The task for this kata is to review the source code of the game interface and check it. To do this
you first have to write the test code for a method and then refactor it that the functionality stays
the same (if it is correct). Please repeat this until all methods are tested. Because the original
programmer has not written any test please additionally check the functionality against the
interface description which has been defined by the architect together with the customer.

As add-on please do the same with the simple console interface. The game should work with the
following behaviour:

1. Ask for the names of the players
2. Ask for the size of the game grid
3. Show the grid and the players with their game tile and their number of moves.
4. Ask in changing order for the game move of the actual player and then repeat with step 3.
5. If a player has won the game show the name of the winner together with the needed moves.
6. Ask if the game should be played again and restart at step 1 if needed.

This add on is for using mock frameworks. Try to mock the game and use the expected behaviour to
test the console gui.
