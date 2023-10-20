package de.conciso.codingdojo.reversi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import de.conciso.codingdojo.reversi.implementation.CharacterDisc;
import de.conciso.codingdojo.reversi.implementation.ReversiGame;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReversiGameTest {

  public static final CharacterDisc CHARACTER_DISC_P1 = new CharacterDisc('W');
  public static final CharacterDisc CHARACTER_DISC_P2 = new CharacterDisc('B');
  Player player1 = new Player("Player1", CHARACTER_DISC_P1);
  Player player2 = new Player("Player2", CHARACTER_DISC_P2);

  ReversiGame cut;
  @Nested
  class UsingConstructor {

    @Test
    void withPlayer1NullThrowsNPE() {
      assertThatExceptionOfType(NullPointerException.class)
          .isThrownBy(() -> new ReversiGame(null, player2));
    }

    @Test
    void withPlayer2NullThrowsNPE() {
      assertThatExceptionOfType(NullPointerException.class)
          .isThrownBy(() -> new ReversiGame(player1, null));
    }
  }

  @Nested
  class UsingGetBoard{

    @BeforeEach
    void setUp(){
      cut = new ReversiGame(player1, player2);
    }

    @Test
    void withBoardNotNull() {
      assertThat(cut.getBoard()).isNotNull();
    }

    @Test
    void returnsBoardWithProperMaxBoardDimensions(){
      assertThat(cut.getBoard().getMaximumPosition()).isEqualTo(new Position(7,7));
    }

    @Test
    void returnsBoardWithProperMinBoardDimensions(){
      assertThat(cut.getBoard().getMinimumPosition()).isEqualTo(new Position(0,0));
    }

    @ParameterizedTest
    @MethodSource("de.conciso.codingdojo.reversi.ReversiGameTest#startingStateParams")
    void returnBoardWithCorrectStartingState(int x, int y, Optional<CharacterDisc> result){
      assertThat(cut.getBoard().getPosition(new Position(x,y))).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("de.conciso.codingdojo.reversi.ReversiGameTest#startingEmptyStateParams")
    void returnBoardWithCorrectEmptyFields(int x, int y){
      assertThat(cut.getBoard().getPosition(new Position(x,y))).isEmpty();
    }
  }

  private static Stream<Arguments> startingStateParams() {
    return Stream.of(
        Arguments.arguments(3, 3, Optional.of(CHARACTER_DISC_P1)),
        Arguments.arguments(3, 4, Optional.of(CHARACTER_DISC_P2)),
        Arguments.arguments(4, 3, Optional.of(CHARACTER_DISC_P2)),
        Arguments.arguments(4, 4, Optional.of(CHARACTER_DISC_P1))
    );
  }
  private static Stream<Arguments> startingEmptyStateParams(){
    return Stream.of(
        Arguments.arguments(2, 2),
        Arguments.arguments(2, 3),
        Arguments.arguments(2, 4),
        Arguments.arguments(2, 5),
        Arguments.arguments(5, 2),
        Arguments.arguments(5, 3),
        Arguments.arguments(5, 4),
        Arguments.arguments(5, 5),
        Arguments.arguments(3, 2),
        Arguments.arguments(4, 2),
        Arguments.arguments(3, 5),
        Arguments.arguments(4, 5)
    );
  }

  @Nested
  class UsingPlayerTests {

    @BeforeEach
    void setUp() {
      cut = new ReversiGame(player1, player2);
    }

    @Test
    void returnsPlayerOneAsInitialPlayer() {
      assertThat(cut.getCurrentPlayer()).isEqualTo(player1);
    }

    @Test
    void checkIfPlayerSwitchAfterMove() {
      Player<?> player1 = cut.getCurrentPlayer();
      cut.doMove(null);
      Player<?> player2 = cut.getCurrentPlayer();
      assertThat(player1).isNotEqualTo(player2);
    }

    @Test
    void checkIfPlayerSwitchAfterTwoMoves() {
      cut.doMove(null);
      cut.doMove(null);
      Player<?> currentPlayer = cut.getCurrentPlayer();
      assertThat(currentPlayer).isEqualTo(player1);
    }
  }

  @Nested
  class UsingDoMove{
    @BeforeEach
    void setUp() {
      cut = new ReversiGame(player1, player2);
    }

    @Test
    void verifyThatTheBoardChanges(){
      Position position = new Position(0,0);
      cut.doMove(position);
      assertThat(cut.getBoard().getPosition(position)).isEqualTo(Optional.of(player1.disc()));
    }

    @Test
    void verifyThatPositionIsNotAlreadyOccupied() {
      Position position = new Position(0, 0);
      cut.doMove(position);
      assertThatExceptionOfType(AlreadyOccupiedException.class).isThrownBy(()->cut.doMove(position));
    }

  }

}