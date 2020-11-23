package de.conciso.codingdojo.fizzbuzz;

import java.sql.Struct;

public class FizzBuzzImpl implements FizzBuzz {
    @Override
    public String[] createFizzBuzz(int first, int last) {

        final String f = "Fizz";
        final String b = "Buzz";
        final String fb = "FizzBuzz";
        String[] result = new String[last - first];
        for (int i = first; i <= last; i++) {

            if (first == 3) {
                result[i - first] = f;
            } else if (first == 5) {
                result[i - first] = b;
            } else if (first == 15) {
                result[i - first] = fb;
            } else {
                result[i - first] = String.valueOf(i);
            }
        }

        return result;


    }

//    public String[] createFizzBuzz(int i, int i1) {
//
//    }
}
