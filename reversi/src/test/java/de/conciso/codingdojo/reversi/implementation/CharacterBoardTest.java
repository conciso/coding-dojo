package de.conciso.codingdojo.reversi.implementation;

import static de.conciso.codingdojo.reversi.implementation.CharacterBoard.DEFAULT_MAXIMUM_HORIZONTAL;
import static de.conciso.codingdojo.reversi.implementation.CharacterBoard.DEFAULT_MAXIMUM_VERTICAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import de.conciso.codingdojo.reversi.Position;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CharacterBoardTest {

  private CharacterBoard cut;
  private final CharacterDisc disc = new CharacterDisc('D');

  @Nested
  class BoardCreation {

    @Test
    void withDefaultConstructorCreatesBoard8x8() {
      cut = new CharacterBoard();

      assertThat(cut.getMaximumPosition()).isEqualTo(new Position(7, 7));
      assertThat(cut.getMinimumPosition()).isEqualTo(new Position(0, 0));
    }

    @Test
    void withSizeConstructorCreatesBoardWithGivenSize() {
      int maxX = 3;
      int maxY = 4;
      cut = new CharacterBoard(maxX, maxY);

      assertThat(cut.getMaximumPosition()).isEqualTo(new Position(maxX - 1, maxY - 1));
      assertThat(cut.getMinimumPosition()).isEqualTo(new Position(0, 0));
    }

    @RepeatedTest(DEFAULT_MAXIMUM_VERTICAL * CharacterBoard.DEFAULT_MAXIMUM_HORIZONTAL)
    void createsEmptyBoard(RepetitionInfo info) {
      cut = new CharacterBoard();
      var devideBy = cut.getMaximumPosition().x() + 1;
      var fieldX = (info.getCurrentRepetition() - 1) % devideBy;
      var fieldY = (info.getCurrentRepetition() - 1) / devideBy;

      assertThat(cut.getPosition(
          new Position(
              fieldX,
              fieldY))
      ).isEmpty();
    }

    @RepeatedTest(5 * 3)
    void createsEmptyRectangularBoard(RepetitionInfo info) {
      cut = new CharacterBoard(5, 3);
      var divideBy = cut.getMaximumPosition().x() + 1;
      var fieldX = (info.getCurrentRepetition() - 1) % divideBy;
      var fieldY = (info.getCurrentRepetition() - 1) / divideBy;

      assertThat(cut.getPosition(
          new Position(
              fieldX,
              fieldY)))
          .isEmpty();
    }
  }

  @Nested
  class ChecksPositionRanges {

    @ParameterizedTest
    @ValueSource(ints = {-1, 8})
    void withGetAndIllegalXRangeThrowsException(int xPosition) {
      cut = new CharacterBoard();

      assertThatIllegalArgumentException()
          .isThrownBy(() ->
              cut.getPosition(new Position(
                  xPosition,
                  0)));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 7})
    void withGetAndLegalXRangeReturnsBoardValue(int xPosition) {
      cut = new CharacterBoard();

      assertThat(cut.getPosition(new Position(
          xPosition,
          DEFAULT_MAXIMUM_VERTICAL - 1)))
          .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 8})
    void withGetAndIllegalYRangeThrowsException(int yPosition) {
      cut = new CharacterBoard();

      assertThatIllegalArgumentException()
          .isThrownBy(() ->
              cut.getPosition(new Position(
                  DEFAULT_MAXIMUM_HORIZONTAL - 1,
                  yPosition)));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 7})
    void withGetAndLegalYRangeReturnsBoardValue(int yPosition) {
      cut = new CharacterBoard();

      assertThat(cut.getPosition(new Position(
          0,
          yPosition)))
          .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 8})
    void withSetAndIllegalXRangeThrowsException(int xPosition) {
      cut = new CharacterBoard();

      assertThatIllegalArgumentException()
          .isThrownBy(() ->
              cut.setPosition(new Position(
                  xPosition,
                  0), disc));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 7})
    void withSetAndLegalXRangeReturnsBoardValue(int xPosition) {
      cut = new CharacterBoard();

      assertThatNoException()
          .isThrownBy(() ->
              cut.setPosition(new Position(
                  xPosition,
                  DEFAULT_MAXIMUM_VERTICAL - 1), disc));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 8})
    void withSetAndIllegalYRangeThrowsException(int yPosition) {
      cut = new CharacterBoard();

      assertThatIllegalArgumentException()
          .isThrownBy(() ->
              cut.setPosition(new Position(
                  0,
                  yPosition), disc));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 7})
    void withSetAndLegalYRangeReturnsBoardValue(int yPosition) {
      cut = new CharacterBoard();

      assertThatNoException()
          .isThrownBy(() ->
              cut.setPosition(new Position(
                  DEFAULT_MAXIMUM_HORIZONTAL - 1,
                  yPosition), disc));
    }
  }

  @RepeatedTest(DEFAULT_MAXIMUM_VERTICAL * DEFAULT_MAXIMUM_HORIZONTAL)
  void setPositionSetsDisc(RepetitionInfo info) {
    cut = new CharacterBoard();
    var value = info.getCurrentRepetition() - 1;
    var position = new Position(
         value % DEFAULT_MAXIMUM_VERTICAL,
         value / DEFAULT_MAXIMUM_VERTICAL);

    cut.setPosition(position, disc);
    assertThat(cut.getPosition(position))
        .isPresent()
        .get()
        .isEqualTo(disc);
  }

  @Nested
  class ToString {

    @Test
    void withDefaultConstructorReturns8x8BoardString() {
      cut = new CharacterBoard();

      assertThat(cut)
          .hasToString(
              """
                  ........
                  ........
                  ........
                  ........
                  ........
                  ........
                  ........
                  ........""");
    }

    @Test
    void withRectangularConstructorReturnsRectangularBoard() {
      cut = new CharacterBoard(5, 4);

      assertThat(cut).hasToString(
          """
              .....
              .....
              .....
              ....."""
      );
    }
  }
}