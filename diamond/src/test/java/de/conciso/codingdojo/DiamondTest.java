package de.conciso.codingdojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DiamondTest {

    private static final String AB_DIAMOND = " a\nb b\n a\n";
    private static final String ABC_DIAMOND = "  a\n b b\nc   c\n b b\n  a\n";

    Diamond cut;

    @BeforeEach
    void before_each() {
        cut = new DiamondImpl();
    }

    @Test
    void should_not_create_diamond_if_input_is_not_az() {
        var result = cut.createDiamond('1');
        assertEquals("", result);
    }

    @Test
    void should_return_a_if_input_is_a() {
        var result = cut.createDiamond('a');
        assertEquals("a\n", result);
    }

    @Test
    void should_return_ab_diamond_if_input_is_b() {
        var result = cut.createDiamond('b');
        assertEquals(AB_DIAMOND, result);
    }

    @Test
    void should_return_abc_diamond_if_input_is_c() {
        var result = cut.createDiamond('c');
        assertEquals(ABC_DIAMOND, result);
    }

}