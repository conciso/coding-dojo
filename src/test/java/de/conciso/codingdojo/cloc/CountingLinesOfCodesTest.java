package de.conciso.codingdojo.cloc;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.hamcrest.MatcherAssert.assertThat;

public class CountingLinesOfCodesTest {

    private CountingLinesOfCodes cut = new CountingLinesOfCodesImpl();

    @Nested
    class GivenFileBlockComment {

        @Test
        public void shouldReturnZero() {
            assertThat(cut.countLinesOfCode(new File("BlockComment.java")), CoreMatchers.is(0));
        }
    }

    @Nested
    class GivenFileLineComment {

        @Test
        public void shouldReturnZero() {
            assertThat(cut.countLinesOfCode(new File("LineComment.java")), CoreMatchers.is(0));
        }
    }

    @Nested
    class GivenFileBlockCommentWithCode {

        @Test
        public void shouldReturnOne() {
            assertThat(cut.countLinesOfCode(new File("BlockCommentWithCode.java")), CoreMatchers.is(1));
        }
    }




}
