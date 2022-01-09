# Actions
##  Rules
1. Use Chicago School of TDD, no mockito, usage of test doubles
2. Use meaningful names for test
3. libraries: JUnit

## Proceeding
* Create the interface as class
  * Write test class
  * Create setup method  
  * implement interface
  

* test starting new game with phrase 
  * string with valid input should store string
  * null, empty string should throw exception
  * string with length > 30 should throw exception
  * string with whitespaces only should throw exception
  * string with punctuation only should throw exception
  

* test starting new Game checking state   
  * valid phrase -> number of tries, number of unsuccessful tries, masked phrase, game running is 
    reset
  * invalid phrase -> number of tries, number of unsuccessful tries, masked phrase, game running 
    stay the same
    

* test guessing
  * when guess is null exception is thrown
  * when guess is empty exception is thrown
  * when guess length is > 1 exception is thrown
  * when guess is punctuation/whitespace exception is thrown  
  * when starting masked phrase has same length
  * when starting masked phrase replaces alphabet characters with '_'
  * when starting masked phrase keeps whitespace and punctuation
  * when guessed masked phrase shows characters contained in phrase
  * when guessed masked phrase keeps '_' for not found characters
  * when guessed number of tries increases
  * when guessed right number of unsuccessful tries stays the same
  * when guessed wrong number of unsuccessful tries increases

  
* test resolution
  * when resolution is requested game ends
  * when game is ended guessing throws exception
