package de.conciso.codingdojo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class XmasTreeDrawerTest {
    XmasTreeDrawer cut = new XmasTreeDrawerImpl();

    @Nested
    class GivenWithStarIsSet {
        private final boolean WITH_STAR = true;

        @Test
        public void shouldThrowExceptionWhenNegativeNumberOfBranches() {
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> cut.drawXmasTree(-1, WITH_STAR));
        }

        @Test
        public void shouldReturnNoBranchesWhenZeroNumberOfBranches() {
            assertThat(cut.drawXmasTree(0, WITH_STAR)).isEqualTo("*\nI");
        }
    }

    @Nested
    class GivenWithStarNotSet {
        private final boolean WITHOUT_STAR = false;

        @Test
        public void shouldReturnNoBranchesWhenZeroNumberOfBranches() {
            assertThat(cut.drawXmasTree(0, WITHOUT_STAR)).isEqualTo("I");
        }

        @Test
        public void shouldThrowExceptionWhenNegativeNumberOfBranches() {
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> cut.drawXmasTree(-1, WITHOUT_STAR));
        }

        @Test
        public void shouldReturnTreeWhenFiveNumberOfBranches() {
            String expectedTree = "  X\n XXX\nXXXXX\n  I";
            assertThat(cut.drawXmasTree(3, WITHOUT_STAR)).isEqualTo(expectedTree);
        }
    }
}