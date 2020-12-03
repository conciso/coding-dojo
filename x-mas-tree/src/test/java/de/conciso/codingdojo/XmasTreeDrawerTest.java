package de.conciso.codingdojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class XmasTreeDrawerTest {

  XmasTreeDrawer xmastreetest = new XmasTreeDrawerImpl();

  @Test
  void numberOfBranchLowerOneThenEmptyString() {
    assertEquals("", xmastreetest.drawXmasTree(0, false));

  }

  @Test
  void numberOfBranchOneThenDrawXmasTreeWithOneBranch() {
    assertEquals(" X\n I\n", xmastreetest.drawXmasTree(1, false));
  }

  @Test
  void numberOfBranchOneWithStarThenDrawXmasTreeWithOneBranchAndStarOnTop() {
    assertEquals(" *\n X\n I\n", xmastreetest.drawXmasTree(1, true));
  }

  @ParameterizedTest
  @MethodSource("stringIntAndListProvider")
  void numberOfBranchHigherThanOne(int branch,String result) {
    assertEquals(result, xmastreetest.drawXmasTree(branch, true));
  }

  static Stream<Arguments> stringIntAndListProvider() {
    return Stream.of(
        arguments(1," *\n X\n I\n"),
        arguments(2,"  *\n  X\n XXX\n  I\n")
    );
  }
}




