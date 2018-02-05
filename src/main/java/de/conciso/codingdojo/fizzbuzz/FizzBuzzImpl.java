package de.conciso.codingdojo.fizzbuzz;

public class FizzBuzzImpl implements FizzBuzz {

    private String numberToFizzBuzz(int number) {

        if (number % 15 == 0) {
            return "fizzbuzz";
        }
        if (number % 5 == 0) {
            return "buzz";
        }
        if (number % 3 == 0) {
            return "fizz";
        }

        return Integer.toString(number);
    }

    @Override
    public String[] createFizzBuzz(int first, int last) {
        if(first > last)    {

            throw new IllegalArgumentException("First must not be larger than last");
        }

        String[] result = new String[last - first + 1];

        for (int i = first; i <= last; i++) {
            result[i - first] = numberToFizzBuzz(i);
        }

        return result;
    }

}
