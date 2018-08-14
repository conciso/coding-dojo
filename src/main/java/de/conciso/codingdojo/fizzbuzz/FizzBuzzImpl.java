package de.conciso.codingdojo.fizzbuzz;

import java.util.Arrays;

public class FizzBuzzImpl implements FizzBuzz {

    @Override
    public String[] createFizzBuzz(int first, int last) {
        final String[] result = new String[calculateLength(first, last)];
        for(int i=first; i<=last; i++) {
            result[i-first] = mapNumber(i);
        }
        return result;
    }

    String mapNumber( int value )
    {
        if (value == 3) {
            return "Fizz";
        } else if (value == 5) {
            return "Buzz";
        } else if (value == 15) {
            return "FizzBuzz";
        }
        return Integer.toString( value );
    }

    private int calculateLength(int first, int last) {
        final int length = last - first + 1;
        return length > 0 ? length : 0;
    }
}
