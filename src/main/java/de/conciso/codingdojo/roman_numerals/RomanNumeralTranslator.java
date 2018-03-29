package de.conciso.codingdojo.roman_numerals;

public interface RomanNumeralTranslator {
    /**
     * Translates the given numeral into an integer.
     *
     * The numerals can contain the following characters:
     * <style type="text/css">
     *     table, td {border: 1px solid black}
     * </style>
     * <table>
     *     <tr>
     *         <td>I</td>
     *         <td>V</td>
     *         <td>X</td>
     *         <td>L</td>
     *         <td>C</td>
     *         <td>D</td>
     *         <td>M</td>
     *     </tr>
     *     <tr>
     *         <td>1</td>
     *         <td>5</td>
     *         <td>10</td>
     *         <td>50</td>
     *         <td>100</td>
     *         <td>500</td>
     *         <td>1000</td>
     *     </tr>
     * </table>
     *
     * @param numeral numeral to translate
     * @return the translated number
     *
     * @throws IllegalArgumentException if the numeral parameter does not contain a valid numeral
     */
    public int translateRomanNumeral(final String numeral);
}
