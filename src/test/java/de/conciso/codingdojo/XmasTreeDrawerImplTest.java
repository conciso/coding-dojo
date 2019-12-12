package de.conciso.codingdojo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class XmasTreeDrawerImplTest {

  XmasTreeDrawer cut = new XmasTreeDrawerImpl();

  @Test
  public void negativeNumberOfBranchesShouldThrowIllegalArgumentException() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> cut.drawXmasTree(-1, true));
  }

  @Test
  public void largerThanTenBranchesShouldThrowIllegalArgumentException() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> cut.drawXmasTree(11, true));
  }

  @Test
  public void treeWithoutBranchesShouldReturnOnlyTrunk() {
    assertThat(cut.drawXmasTree(0,false)).isEqualTo("I");
  }

  @Test
  public void treeWithoutBrancehsAndWithStarShouldDrawStarAndTrunk() {
    assertThat(cut.drawXmasTree(0,true)).isEqualTo("*\nI");
  }

  @Test
  public void treeWithFiveBranchesShouldDrawTreeWithFiveBranches() {
    String expected =
          "    X\n"
        + "   XXX\n"
        + "  XXXXX\n"
        + " XXXXXXX\n"
        + "XXXXXXXXX\n"
        + "    I";

    assertThat(cut.drawXmasTree(5, false)).isEqualTo(expected);
  }
}