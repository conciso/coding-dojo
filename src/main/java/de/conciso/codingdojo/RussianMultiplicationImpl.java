package de.conciso.codingdojo;

public class RussianMultiplicationImpl implements RussianMultiplication {
    @Override
    public int multiply(int firstFactor, int secondFactor) {
        return 0;
    }

    public int multiplyByTwo(int initialValue) {
        if(initialValue > Integer.MAX_VALUE / 2 || initialValue < Integer.MIN_VALUE / 2) {
            throw new IllegalArgumentException();
        }

        return initialValue*2;
    }

    public int divisionByTwoAndRoundDown(int initialValue) {
        return initialValue/2;
    }
}
