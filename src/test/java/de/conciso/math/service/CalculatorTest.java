package de.conciso.math.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CalculatorTest {

  private Calculator cut = new CalculatorImpl();

  @Nested
  class AdditionOf {
    @Test
    public void twoPositiveNumbersShouldBeTheSum() {
      int result = cut.addition(1, 2);
      assertEquals(3, result);
    }

    @Test
    public void twoNegativeNumbersShouldBeTheSum() {
      int result = cut.addition(-1, -2);
      assertEquals(-3, result);
    }
  }

  @Nested
  class MultiplicationOf {
    @Test
    public void twoPositiveNumbersShouldBeTheProduct() {
      int result = cut.multiplication(1, 2);
      assertEquals(2, result);
    }

    @Test
    public void twoNegativeNumbersShouldBeTheProduct() {
      int result = cut.multiplication(-1, -2);
      assertEquals(2, result);
    }
  }
}
