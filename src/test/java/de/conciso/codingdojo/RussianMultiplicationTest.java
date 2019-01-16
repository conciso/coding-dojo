package de.conciso.codingdojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RussianMultiplicationTest {
    RussianMultiplication cut;

    @BeforeEach
    void setup() {
        cut = new RussianMultiplicationImpl();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0",
            "2, 2, 4",
            "15, 16, 240",
            "27, 82, 2214"
    })
    void testFirstFactorMultipliedBySecondFactorGivesCorrectResult(
            long firstFactor, long secondFactor, long expectedResult
    ) {
        assertEquals(expectedResult, cut.multiply(firstFactor, secondFactor));
    }
}