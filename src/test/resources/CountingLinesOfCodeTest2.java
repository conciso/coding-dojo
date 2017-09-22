package de.conciso.codingdojo.cloc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class CountingLinesOfCodeTest2 {
    private CountingLinesOfCodes countingLinesOfCode;
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
}