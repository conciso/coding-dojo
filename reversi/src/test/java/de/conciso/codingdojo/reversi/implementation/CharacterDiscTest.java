package de.conciso.codingdojo.reversi.implementation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CharacterDiscTest {

  private static final Character SYMBOL = 'W';

  @Test
  void constructorStoresSymbol() {
    CharacterDisc cut = new CharacterDisc(SYMBOL);

    assertThat(cut.getSymbol()).isEqualTo(SYMBOL);
  }

}