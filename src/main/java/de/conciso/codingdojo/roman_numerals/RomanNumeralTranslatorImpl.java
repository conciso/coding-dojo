package de.conciso.codingdojo.roman_numerals;

public class RomanNumeralTranslatorImpl implements RomanNumeralTranslator {
    @Override
    public int translateRomanNumeral(String numeral) {
        if ("".equals(numeral)) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < numeral.length(); i++) {
            sum += RomanLiteral.valueOf(numeral.substring(i, i+1)).getValue();
        }

        return sum;
    }
}
