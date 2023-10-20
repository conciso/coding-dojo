package de.conciso.codingdojo.reversi.implementation;

import de.conciso.codingdojo.reversi.Disc;
import java.util.Objects;
import java.util.StringJoiner;

public class CharacterDisc implements Disc<Character> {

  private final Character symbol;

  public CharacterDisc(Character c) {
    this.symbol = c;
  }

  public Character getSymbol(){
    return symbol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CharacterDisc that)) {
      return false;
    }
    return Objects.equals(symbol, that.symbol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(symbol);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", CharacterDisc.class.getSimpleName() + "[", "]")
        .add("symbol=" + symbol)
        .toString();
  }
}
