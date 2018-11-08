package de.conciso.codingdojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RussianMultiplicationImplTest {

    private RussianMultiplicationImpl cut;

    @BeforeEach
    void createCut(){
        cut=new RussianMultiplicationImpl();
    }

    @ParameterizedTest
    @CsvSource({"0, 0", "100, 200", "-1, -2", "1073741823, 2147483646"})
    void thatMultiplyByTwoDoublesValues(int initialValue, int doubledValue){
        int result = cut.multiplyByTwo(initialValue);
        assertEquals(doubledValue, result);
    }

    @ParameterizedTest
    @ValueSource(ints = { Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE / 2 + 1 })
    void thatMultiplyByTwoWithExtremeParametersRaisesException (int initialValue){
        assertThrows(IllegalArgumentException.class, () ->
            cut.multiplyByTwo(initialValue)
        );
    }

    @ParameterizedTest
    @CsvSource({"200, 100", "-2,-1", "2147483646,1073741823", "5,2", "-5,-2", "2147483647,1073741823", "0,0"})
    void thatDivisionByTwoAndRoundDownHalfsValues(int initialValue, int roundedValue){
        int result = cut.divisionByTwoAndRoundDown(initialValue);
        assertEquals(roundedValue, result);
    }
    @ParameterizedTest
    @CsvSource({"12, 3, 36", "43,  21, 903"})
    void thatRussianMultiplicationMultipliesTwoNumbers(int firstFactor, int secondFactor, int expectedResult){
        int result = cut.multiply(firstFactor, secondFactor);
        assertEquals(expectedResult, result);
    }

}