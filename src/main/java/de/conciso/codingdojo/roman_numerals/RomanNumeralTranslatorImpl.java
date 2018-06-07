package de.conciso.codingdojo.roman_numerals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RomanNumeralTranslatorImpl implements RomanNumeralTranslator {
    Map<String, Integer> numerals;

    public RomanNumeralTranslatorImpl() {
        numerals = new HashMap<>();
        numerals.put("I", 1);
        numerals.put("V", 5);
        numerals.put("X", 10);
        numerals.put("L", 50);
        numerals.put("C", 100);
        numerals.put("D", 500);
        numerals.put("M", 1000);
    }

    @Override
    public int translateRomanNumeral(String numeral) {
        if(numeral==null) {
            throw new IllegalArgumentException();
        }
        int sum = 0;
        for (int i = 0; i < numeral.length(); i++) {
            String character = "" + numeral.charAt(i);
            if (!numerals.containsKey(character)) {
                throw new IllegalArgumentException();
            }
            sum += numerals.get(character);
        }
        return sum;
    }

}
