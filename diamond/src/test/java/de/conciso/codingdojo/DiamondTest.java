package de.conciso.codingdojo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class DiamondTest {
  Diamond cut = new DiamondImpl();

  @ParameterizedTest
  @ValueSource(chars = { 'a' - 1, 'z' + 1 })
  void WithInvalidValuesThrowsIllegalArgumentException(char value) {
    assertThatThrownBy(() -> cut.createDiamond(value)).isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @ValueSource(chars = { 'a', 'f', 'z' })
  void WithValidValuesDoesNotThrowException(char value) {
    assertThatNoException().isThrownBy(() -> cut.createDiamond(value));
  }

  @ParameterizedTest
  @CsvSource({
      "a, 'a\n'",
      "f ,'     a\n    b b\n   c   c\n  d     d\n e       e\nf         f\n e       e\n  d     d\n   c   c\n    b b\n     a\n'",
      "c, '  a\n b b\nc   c\n b b\n  a\n'"
  })
  void WithValidValuesCreatesDiamondOutput(char value, String expected) {
    assertThat(cut.createDiamond(value)).isEqualTo(expected);
  }

  @Test
  void WithValidValuesCreatesEnoughLines() {
    assertThat(cut.createDiamond('z').split("\n").length).isEqualTo(51);
  }

}