package de.conciso.codingdojo;

public interface Diamond {

  /**
   * The method takes a letter as parameter and creates an ascii diamond with the letter as widest range. Valid values
   * are all lowercase letters from the alphabet.
   * E.G. with letter 'c':
   * <pre>
   *   a
   *  b b
   * c   c
   *  b b
   *   a
   * </pre>
   * @param letter valid values are a-z
   * @return ascii diamond with the given letter in in the middle. Lines are divided by newline character <code>
   * '\n'</code>
   * @throws RuntimeException various RuntimeExceptions could be thrown
   */
  String createDiamond(char letter);
}
