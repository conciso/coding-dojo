package de.conciso.math.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.conciso.math.service.CalculatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CalculatorControllerTest {

  @Mock
  private CalculatorImpl calculatorMock;

  @InjectMocks
  private CalculatorController cut;

  private static final int FIRST_SUMMAND = 3;
  private static final int SECOND_SUMMAND = 4;

  private static final int MULTIPLIER = 3;
  private static final int MULTIPLICAND = 4;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    when(calculatorMock.addition(FIRST_SUMMAND, SECOND_SUMMAND))
        .thenReturn(FIRST_SUMMAND + SECOND_SUMMAND);

    when(calculatorMock.multiplication(MULTIPLIER, MULTIPLICAND))
        .thenReturn(MULTIPLIER + MULTIPLICAND);
  }

  @Test
  public void additionShouldCallCalculatorAdditionWithCorrectParameters() {
    cut.getAddition(FIRST_SUMMAND, SECOND_SUMMAND);
    verify(calculatorMock).addition(FIRST_SUMMAND, SECOND_SUMMAND);
  }

  @Test
  public void multiplitionShouldCallCalculatorMultiplicationWithCorrectParameters() {
    cut.getMultiplication(MULTIPLIER, MULTIPLICAND);
    verify(calculatorMock).multiplication(MULTIPLIER, MULTIPLICAND);
  }
}