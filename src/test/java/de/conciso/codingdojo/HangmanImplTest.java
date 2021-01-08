package de.conciso.codingdojo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HangmanImplTest {
  @Test
  public void whenValidPhraseIsSetThenPhraseIsStored(){
    HangmanImpl cut = new HangmanImpl();
    String phrase = "test";

    cut.setNewPhrase(phrase);

    assertThat(cut.getPhrase()).isEqualTo(phrase);
  }

  @Test
  public void whenNullPhraseIsSetThenExceptionIsThrown() {
    HangmanImpl cut = new HangmanImpl();

    assertThrows(NullPointerException.class, ()-> cut.setNewPhrase(null));
  }
}