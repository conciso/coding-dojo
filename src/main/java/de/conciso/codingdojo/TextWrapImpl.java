package de.conciso.codingdojo;

import org.apache.commons.lang3.StringUtils;

public class TextWrapImpl implements TextWrap {

    @Override
    public String wrapText(String text, int length) {
        if (length < 2) {
            throw new IllegalArgumentException("Length must not be less than 2.");
        }

        if (text.length() < length) {
            return StringUtils.rightPad(text, length);
        }

        String resultString = "";
        int index = 0;
        while (index + length - 1 < text.length()) {
            String currentSubstring = text.substring(index, index + length - 1);
            int last = currentSubstring.lastIndexOf(" ");

            resultString += StringUtils.rightPad(currentSubstring.substring(0, last), length);
            resultString += "\n";

            index += last + 1;
        }
        return resultString + StringUtils.rightPad(text.substring(index), length);
    }
}
