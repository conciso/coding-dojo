package de.conciso.codingdojo;

public class DiamondImpl implements Diamond {

  public static final char START_CHAR = 'a';
  public static final char END_CHAR = 'z';
  public static final String SPACE = " ";

  @Override
  public String createDiamond(char letter) {
    if ((letter < START_CHAR) || (letter > END_CHAR)) {
      throw new IllegalArgumentException("Letter must be between 'a' and 'z'");
    }

    StringBuffer buffer = new StringBuffer();

    for (char i = START_CHAR; i <= letter; i++) {
      buffer.append(createRow(letter, i));
    }

    for (char i = (char) (letter - 1); i >= START_CHAR; i--) {
      buffer.append(createRow(letter, i));
    }

    return buffer.toString();
  }

  private String createRow(char letter, char actualChar) {
    return createSpaces(letter - actualChar)+ actualChar
        + createSecondLetter(actualChar) + "\n";
  }

  private String createSecondLetter(char actualChar) {
    if (actualChar > START_CHAR) {
      return createSpaces((actualChar - START_CHAR) * 2 - 1)
          + actualChar;
    }
    return "";
  }

  private String createSpaces(int count) {
    return new String(SPACE).repeat(count);
  }
}
