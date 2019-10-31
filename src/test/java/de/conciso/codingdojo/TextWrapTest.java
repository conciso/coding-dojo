package de.conciso.codingdojo;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TextWrapTest {
    public TextWrap cut;
    public static final String TEST_TEXT = "Dies ist ein Test";

    @BeforeEach
    public void setUp() {
        cut = new TextWrapImpl();
    }

    @Test
    public void thatShortTextIsNotWrapped() {
        String result = cut.wrapText(TEST_TEXT, 20);

        assertEquals(StringUtils.rightPad(TEST_TEXT, 20), result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -1})
    public void thatLengthLessThanTwoThrowsIllegalArgumentException(int argument) {
        assertThrows(IllegalArgumentException.class, () -> cut.wrapText(TEST_TEXT, argument));
    }

    @Test
    public void thatTextIsWrapped() {
        String result = cut.wrapText(TEST_TEXT, 10);
        String expectation = "Dies ist  \nein Test  ";
        assertEquals(expectation, result);
    }

    @Test
    public void thatTextContainsNoLeadingWhitespacesAfterWrapping() {
        String result = cut.wrapText("aaa aa", 3);
        String expectation = "aaa\naa ";
        assertEquals(expectation, result);
    }
}