package de.conciso.codingdojo.roman_numerals;

public class RomanNumeralTranslatorImpl implements RomanNumeralTranslator {
    @Override
    public int translateRomanNumeral(String numeral) {
        if (null == numeral || numeral.isEmpty() ) {
            throw new IllegalArgumentException();
        }

        int sum = 0;
        char previousCharacter = 0;
        int numberOfSameCharacter = 0;
        for (char romanNumeralCharacter : numeral.toUpperCase().toCharArray()) {
            if( romanNumeralCharacter != previousCharacter ) {
                numberOfSameCharacter = 1;
                previousCharacter = romanNumeralCharacter;
            } else {
                ++numberOfSameCharacter;



                /*
                if( numberOfSameCharacter > maximumNumberOfCharacter( romanNumeralCharacter ) ) {
                    throw new IllegalArgumentException();
                }
                */

            }
            int maxAllowedRepetitions;
            switch (romanNumeralCharacter) {
                case 'I':
                    maxAllowedRepetitions = 3;
                    sum += 1;
                    break;
                case 'V': return 5;
                case 'X': return 10;
                case 'L': return 50;
                case 'C': return 100;
                case 'D': return 500;
                case 'M': return 1000;
                default:
                    throw new IllegalArgumentException();
            }
            sum += translateSingleRomanNumeral(romanNumeralCharacter);
        }
        return sum;
    }

    private int maximumNumberOfCharacter(char romanNumeralCharacter) {
        switch( romanNumeralCharacter ) {
            case 'I': return 3;
            case 'V': return 1;
            case 'X': return 3;
            case 'L': return 1;
            case 'C': return 3;
            case 'D': return 1;
            case 'M': return 3;
        }
        return 0;
    }

    private int translateSingleRomanNumeral(final char romanNumeralCharacter) {
        switch( romanNumeralCharacter ) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;

        }
        throw new IllegalArgumentException();
    }
}
