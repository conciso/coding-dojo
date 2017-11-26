package de.conciso.codingdojo.cloc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class LineReaderImplTest {

    private LineReader cut;

    @Nested
    class WhenReadingTestOneLine {

        @BeforeEach
        public void setUp() throws FileNotFoundException {
            cut = new LineReaderImpl();
        }

        @Test
        public void shouldReturnHello() {
            assertThat(cut.read(), is("Hello"));
        }

        @Test
        public void secondCallShouldReturnNull() {
            cut.read();
            assertThat(cut.read(), is(null));
        }
    }

}