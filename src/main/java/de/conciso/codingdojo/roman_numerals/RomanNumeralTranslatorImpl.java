package de.conciso.codingdojo.roman_numerals;

import java.util.HashMap;
import java.util.Map;

public class RomanNumeralTranslatorImpl implements RomanNumeralTranslator {
    private Map<String, Integer> numeralMap;
    public RomanNumeralTranslatorImpl(){
        numeralMap = new HashMap<>();
        numeralMap.put("I", 1);
        numeralMap.put("V", 5);
        numeralMap.put("X", 10);
        numeralMap.put("L", 50);
        numeralMap.put("C", 100);
        numeralMap.put("D", 500);
        numeralMap.put("M", 1000);
    }

    @Override
    public int translateRomanNumeral(String numeral) {
        int acc = 0;
        int i = 0;
        while (i < numeral.length()) {
            int firstValue = numeralMap.get(numeral.substring(i, i + 1));
            if (i + 1 < numeral.length()) {
                int secondValue = numeralMap.get(numeral.substring(i + 1, i + 2));
                if (firstValue < secondValue)
                {
                    acc += secondValue - firstValue;
                    i++;
                }
                else
                {
                    acc += firstValue;
                }
            }
            else {
                acc += firstValue;
            }
            i++;
        }
        return acc;
    }
}
