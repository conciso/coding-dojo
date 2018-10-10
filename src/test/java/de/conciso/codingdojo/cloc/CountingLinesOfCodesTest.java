package de.conciso.codingdojo.cloc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CountingLinesOfCodesTest {

    private CountingLinesOfCodes cut;
    private final File resourcesPath = new File( "./src/test/resources/" );

    @BeforeEach
    private void init() {
        cut = new CountingLinesOfCodeImpl();
    }

    @Test
    public void testThatNullParameterThrowsRuntimeException() {
        assertThrows(RuntimeException.class, () -> cut.countLinesOfCode(null));
    }

    @Test
    public void testThatNonExistentFileThrowsRunTimeException() {
        final File sourceCodeFile = new File(resourcesPath, "emptydirectory/does-not-exist.java");
        assertThrows(RuntimeException.class, () -> {
            cut.countLinesOfCode(sourceCodeFile);
        });
    }

    @Test
    public void testThatDirectoryThrowsRunTimeException() {
        final File sourceCodeFile = new File(resourcesPath, "emptydirectory");
        assertThrows(RuntimeException.class, () -> {
            cut.countLinesOfCode(sourceCodeFile);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "JustCode.java, 1",
            "LineComment.java, 0",
            "LineCommentWithCode.java, 1",
            "LineCommentWithSpace.java, 0",
            "BlockComment.java, 0"
                })
    public void testThatValidJavaFileReturnsCorrectNumberOfLinesOfCode(final String fileName, final int expectedNumber) {
        assertEquals(expectedNumber, cut.countLinesOfCode(new File(resourcesPath, fileName)));
    }
}