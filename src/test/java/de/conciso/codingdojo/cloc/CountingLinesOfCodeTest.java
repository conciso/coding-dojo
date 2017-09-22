package de.conciso.codingdojo.cloc;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CountingLinesOfCodeTest {

    private CountingLinesOfCodesImpl countingLinesOfCode;

    @BeforeEach
    public void setup() {
        countingLinesOfCode = new CountingLinesOfCodesImpl();
    }

    @Nested
    class WhenCalledWithNull {
        @Test
        public void thenExceptionIsThrown() {
            assertThrows(NullPointerException.class, () -> countingLinesOfCode.countLinesOfCode(null));
        }
    }

    @Nested
    class WhenFileNotExists {
        @Test
        public void testFileDoesNotExist() {
            assertThrows(IOException.class, () -> countingLinesOfCode.countLinesOfCode(new File("dummy.java")));
        }
    }

    @Nested
    class WhenCalledWithEmptyFile {
        @Test
        public void then0IsReturned() throws IOException {
            assertEquals(0, countingLinesOfCode.countLinesOfCode(new File("src/test/resources/empty.java")));
        }
    }

    @Nested
    class WhenCalledWithJavaFile {
        @Test
        public void thenSizeIsReturned() throws IOException {
            assertEquals(36, countingLinesOfCode.countLinesOfCode(new File("src/test/resources/CountingLinesOfCodeTest2.java")));
        }
        @Test
        public void thenEmptyLinesIgnored() throws IOException {
            assertEquals(36, countingLinesOfCode.countLinesOfCode(new File("src/test/resources/CountingLinesOfCodeTest1.java")));
        }

        @Test
        public void thenCommentLinesIgnored() throws IOException {
            assertEquals(0, countingLinesOfCode.countLinesOfCode(new File("src/test/resources/LineComment.java")));
        }
    }

    @Nested
    class WhenCalledFilterLiner
    {
        private final String EMPTY_TEST_STRING = "";
        private final String NON_EMPTY_TEST_STRING = "lkdjsfhkjsdafh";

        @Test
        void filterEmptyLine()
        {
            assertEquals(EMPTY_TEST_STRING, countingLinesOfCode.filterLine(EMPTY_TEST_STRING));
        }

        @Test
        void filterNonEmpty()
        {
            assertEquals(NON_EMPTY_TEST_STRING, countingLinesOfCode.filterLine(NON_EMPTY_TEST_STRING));
        }
    }
}
