package de.conciso.codingdojo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class HangmanImplTest {

  public static final String TEST_PHRASE = "Phrase";
  private HangmanImpl cut = new HangmanImpl();

  @Test
  public void setNewPhraseShouldHaveSameLengthAsMaskedPhrase() {
    cut.setNewPhrase(TEST_PHRASE);
    assertEquals(TEST_PHRASE.length(), cut.getMaskedPhrase().length());
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "2398723908"," \r\n\t", "!§$"})
  public void setNewPhraseWithIllegalPhraseThrowsException(String value) {
    assertThrows(IllegalArgumentException.class, () -> cut.setNewPhrase(value));
  }

  @Test
  public void isGameRunningAfterSetPhrase() {
    cut.setNewPhrase(TEST_PHRASE);
    assertTrue(cut.isGameRunning());
  }

  @Test
  public void isGameInitiallyNotRunning() {
    assertFalse(cut.isGameRunning());
  }

  @Test
  public void getMaskedPhraseWithoutSettingPhraseThrowsException() {
    Throwable ex = assertThrows(IllegalArgumentException.class, () -> cut.getMaskedPhrase());

    assertEquals("Phrase not set", ex.getMessage());
  }

  @ParameterizedTest
  @CsvSource(value = {TEST_PHRASE + ",______", "a!§$%&,_!§$%&", "äöüßÖÄÜ,_______", "a123456,_123456"})
  public void getMaskedPhraseReplacesLettersWithUnderscores(String value, String result) {
    cut.setNewPhrase(value);
    assertEquals(result, cut.getMaskedPhrase());
  }
  @Test
  public void getMaskedPhraseDoesNotReplaceWhitespace() {
    String whitespaces = " \t\r\n";
    cut.setNewPhrase(TEST_PHRASE + whitespaces);
    assertEquals("______" + whitespaces, cut.getMaskedPhrase());
  }
}