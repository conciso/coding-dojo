package de.conciso.codingdojo.reversi.implementation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import de.conciso.codingdojo.reversi.Position;
import java.util.Random;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CharacterBoardTest {

  private CharacterBoard cut;
  private CharacterDisc disc = new CharacterDisc('D');

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

    @RepeatedTest(CharacterBoard.DEFAULT_MAXIMUM_VERTICAL * CharacterBoard.DEFAULT_MAXIMUM_HORIZONTAL)
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
      var devideBy = cut.getMaximumPosition().x() + 1;
      var fieldX = (info.getCurrentRepetition() - 1) % devideBy;
      var fieldY = (info.getCurrentRepetition() - 1) / devideBy;

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
                  new Random().nextInt(cut.getMaximumPosition().x() + 1))));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 7})
    void withGetAndLegalXRangeReturnsBoardValue(int xPosition) {
      cut = new CharacterBoard();

      assertThat(cut.getPosition(new Position(
          xPosition,
          new Random().nextInt(cut.getMaximumPosition().x() + 1))))
          .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 8})
    void withGetAndIllegalYRangeThrowsException(int yPosition) {
      cut = new CharacterBoard();

      assertThatIllegalArgumentException()
          .isThrownBy(() ->
              cut.getPosition(new Position(
                  new Random().nextInt(cut.getMaximumPosition().y() + 1),
                  yPosition)));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 7})
    void withGetAndLegalYRangeReturnsBoardValue(int yPosition) {
      cut = new CharacterBoard();

      assertThat(cut.getPosition(new Position(
          new Random().nextInt(cut.getMaximumPosition().y() + 1),
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
                  new Random().nextInt(cut.getMaximumPosition().x() + 1)), disc));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 7})
    void withSetAndLegalXRangeReturnsBoardValue(int xPosition) {
      cut = new CharacterBoard();

      assertThatNoException()
          .isThrownBy(() ->
              cut.setPosition(new Position(
                  xPosition,
                  new Random().nextInt(cut.getMaximumPosition().x() + 1)), disc));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 8})
    void withSetAndIllegalYRangeThrowsException(int yPosition) {
      cut = new CharacterBoard();

      assertThatIllegalArgumentException()
          .isThrownBy(() ->
              cut.setPosition(new Position(
                  new Random().nextInt(cut.getMaximumPosition().y() + 1),
                  yPosition), disc));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 7})
    void withSetAndLegalYRangeReturnsBoardValue(int yPosition) {
      cut = new CharacterBoard();

      assertThatNoException()
          .isThrownBy(() ->
              cut.setPosition(new Position(
                  new Random().nextInt(cut.getMaximumPosition().y() + 1),
                  yPosition), disc));
    }
  }

  @RepeatedTest(10)
  void setPositionSetsDisc() {
    cut = new CharacterBoard();
    var rnd = new Random();
    var position = new Position(
        rnd.nextInt(CharacterBoard.DEFAULT_MAXIMUM_HORIZONTAL),
        rnd.nextInt(CharacterBoard.DEFAULT_MAXIMUM_VERTICAL));

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

      assertThat(cut.toString())
          .isEqualTo(
              "........\n"
                  + "........\n"
                  + "........\n"
                  + "........\n"
                  + "........\n"
                  + "........\n"
                  + "........\n"
                  + "........");
    }

    @Test
    void withRectangularConstructorReturnsRectangularBoard() {
      cut = new CharacterBoard(5, 4);

      assertThat(cut.toString())
          .isEqualTo(
              ".....\n"
                  + ".....\n"
                  + ".....\n"
                  + "....."
          );
    }
  }
}