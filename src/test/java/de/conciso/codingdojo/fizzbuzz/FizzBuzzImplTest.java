package de.conciso.codingdojo.fizzbuzz;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FizzBuzzImplTest {

    private FizzBuzz cut;

    @BeforeEach
    public void setUp() {
        cut = new FizzBuzzImpl();
    }

    @Test
    public void test_fizzbuzz_15to15_returns_fizzbuzz() {
        String[] result = cut.createFizzBuzz(15, 15);

        assertTrue(result[0].equals("fizzbuzz"));
    }

    @Test
    public void test_fizzbuzz_30to30_returns_fizzbuzz() {
        String[] result = cut.createFizzBuzz(30, 30);

        assertTrue(result[0].equals("fizzbuzz"));
    }

    @Test
    public void test_fizzbuzz_3to3_returns_fizz() {
        String[] result = cut.createFizzBuzz(3, 3);

        assertTrue(result[0].equals("fizz"));
    }

    @Test
    public void test_fizzbuzz_5to5_returns_buzz() {
        String[] result = cut.createFizzBuzz(5, 5);

        assertTrue(result[0].equals("buzz"));
    }

    @Test
    public void test_fizzbuzz_7to7_returns_7() {
        String[] result = cut.createFizzBuzz(7, 7);

        assertEquals("7", result[0]);
    }

    @Test
    public void test_fizzbuzz_0to99_returns_100_strings() {
        String[] result = cut.createFizzBuzz(0, 99);

        assertEquals (100, result.length);
    }

    @Test
    public void test_fizzbuzz_6to6_returns_fizz() {
        String[] result = cut.createFizzBuzz(6, 6);

        assertEquals ("fizz", result[0]);
    }

    @Test
    public void test_fizzbuzz_10to10_returns_buzz() {
        String[] result = cut.createFizzBuzz(10, 10);

        assertEquals ("buzz", result[0]);
    }

    @Test
    public void test_fizzbuzz_0to0_returns_fizzbuzz() {
        String[] result = cut.createFizzBuzz(0, 0);

        assertEquals ("fizzbuzz", result[0]);
    }

    @Test
    public void test_fizzbuzz_10to5_throws_IllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, ()-> cut.createFizzBuzz(10, 5));
    }

    @Test
    public void test_fizzbuzz_10to10_returns_oneElement() {
        String[] result = cut.createFizzBuzz(10, 10);

        assertEquals (1, result.length);
    }



}