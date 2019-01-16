package de.conciso.codingdojo;

public interface RussianMultiplication {
    /**
     * <p>Multiply two factors using the russian multiplication. The rules to calculate it manually are
     * the following:</p>
     *
     * <ol>
     * <li>Write both factors next to each other</li>
     * <li>Devide the left side in half, round down the remainder and write the result one among the other
     * until one is reached</li>
     * <li>Double the right side and write the result one among the other</li>
     * <li>delete the number on the right side if the left side is even</li>
     * <li>The sum of the remaining number on the right is the wanted product</li>
     * </ol>
     *
     * @param firstFactor first factor of the multiplication
     * @param secondFactor second factor of the multiplication
     * @return product of the multiplication
     */
    long multiply(long firstFactor, long secondFactor);
}
