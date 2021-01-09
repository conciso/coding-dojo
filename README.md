# coding-dojo
## Hangman Kata
Hangman is a paper and pencil guessing game for two or more players. 
One player thinks of a word, phrase or sentence and the other(s) tries to guess 
it by suggesting letters or numbers, within a certain number of guesses. (see [Wikipedia][1])

### Exercise
Develop a class which should implement the following methods
1. Method **startNewGame** takes a word or phrase which resets the game and
takes a new phrase to guess.
2. Method **guessCharacter** takes a character to insert and returns
*true* or *false* depending on whether the character is contained in the phrase or not.
If the the game is already ended or is not initialized an exception is thrown.
3. Method **getNumberOfTries** returns the number of tries.
4. Method **getNumberOfUnsuccessfulTries** returns the number of unsuccessful tries.
5. Method **getMaskedPhrase** returns the phrase where the characters which are not
already guessed are masked with "_". If the phrase contains whitespaces or punctuation
characters then these are not masked.
6. Method **isGameRunning** returns *false* if the phrase is completely guessed or the game is not
initialized. Otherwise it returns *true*.
7. Method **getResolution** returns the solution phrase used for the initialisation of the game. When
using this method the game is stopped.

## Rules for Programming
### Easy
There are no constraints for the keywords which could be used to solve the problem

### Advanced
- for, while and do..while are not allowed.
- if..else is not allowed.

### Hardcore
- streams are not allowed.
- collections are not allowed.

[1]: https://en.wikipedia.org/wiki/Hangman_(game)