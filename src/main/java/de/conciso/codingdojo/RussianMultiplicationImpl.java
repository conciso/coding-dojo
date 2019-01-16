package de.conciso.codingdojo;

public class RussianMultiplicationImpl implements RussianMultiplication {
    @Override
    public long multiply(long firstFactor, long secondFactor) {
        return (0 == firstFactor ? 0 : (long)multiply(firstFactor / 2, 2 * secondFactor) + (firstFactor % 2 == 0 ? 0 : secondFactor));
    }
}
