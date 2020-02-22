package de.conciso.math.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CalculatorImpl implements Calculator {

  @Override
  public int addition(int firstSummand, int secondSummand) {
    return firstSummand + secondSummand;
  }

  @Override
  public int substraction(int minuend, int substrahend) {
    return 0;
  }

  @Override
  public int division(int dividend, int divisor) {
    return 0;
  }

  @Override
  public int multiplication(int multiplier, int multiplicand) {
    return multiplicand * multiplier;
  }
}
