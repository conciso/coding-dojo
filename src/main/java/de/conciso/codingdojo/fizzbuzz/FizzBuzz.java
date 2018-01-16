package de.conciso.codingdojo.fizzbuzz;

public interface FizzBuzz {
    /**
     * This function generates an array of strings containing the number from first to last with the following
     * exceptions:
     * <ul>
     *     <li>The number will be replaced with Fizz if it can be divided by 3</li>
     *     <li>The number will be replaced with Buzz if it can be divided by 5</li>
     *     <li>It the number can be divided by both numbers then it will be replaced by FizzBuzz</li>
     * </ul>
     * @param first first integer of the fizz buzz generator
     * @param last last integer of the fizz buzz generator
     * @return Array with Strings containing a number or one element of the fizz buzz pattern
     */
    String[] createFizzBuzz(final int first, final int last);
}
