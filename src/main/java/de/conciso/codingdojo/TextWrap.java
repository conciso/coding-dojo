package de.conciso.codingdojo;

public interface TextWrap {
    /**
     * This method takes a text and wraps it at the whitespaces that it will fit into the given length. The text is
     * normally split at whitespaces only.
     * If the length of a word will not fit into the given maximum text length it will be split at the maximum line
     * length position minus one too and at the upper line a minus sign is added.
     *
     * @param text text to be wrapped
     * @param length maximum length of one line of the text.
     * @return text with the wrapped text
     */
    String wrapText(String text, int length);
}
