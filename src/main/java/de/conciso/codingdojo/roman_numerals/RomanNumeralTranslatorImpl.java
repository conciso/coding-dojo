package de.conciso.codingdojo.roman_numerals;

import java.util.HashMap;
import java.util.Map;

public class RomanNumeralTranslatorImpl implements RomanNumeralTranslator {

    final static Map<String, Integer> atomsToArabicMap = new HashMap<>();
    static {atomsToArabicMap.put("I", 1);
    atomsToArabicMap.put("V", 5);
    atomsToArabicMap.put("X", 10);
    atomsToArabicMap.put("L", 50);
    atomsToArabicMap.put("C", 100);
    atomsToArabicMap.put("D", 500);
    atomsToArabicMap.put("M", 1000);
    }

    @Override
    public int translateRomanNumeral(String numeral) {
        if (numeral == null) {
            throw new IllegalArgumentException("'" + numeral + "' is not a roman number");
        }
        int result = 0;
        for (Character atom : numeral.toCharArray()) {
            if (!atomsToArabicMap.containsKey(atom.toString())){
                throw new IllegalArgumentException("'" + numeral + "' is not a roman number");
            }
            result += atomsToArabicMap.get(atom.toString());
        }
        return result;
    }

}
