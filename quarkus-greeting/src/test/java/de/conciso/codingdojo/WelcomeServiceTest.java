package de.conciso.codingdojo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WelcomeServiceTest {

  @Mock
  WelcomeRepository welcomeRepository;

  @InjectMocks
  WelcomeService cut;



  @Nested
  class WhenAddingName {


    @Test
    public void withNullExpectException() {
        assertThatThrownBy(() -> cut.addName(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void withValidValueExpectNoException() {
      assertThatNoException().isThrownBy(() -> cut.addName("abc"));
    }

    @Test
    public void withEmptyStringExpectException() {
      assertThatThrownBy(() -> cut.addName("")).isInstanceOf(IllegalArgumentException.class);
    }
  }

  @Nested
  class WhenGettingAll {
    @Test
    void expectInitialListIsEmpty() {
      assertThat(cut.getAll()).isEmpty();
    }

    @Test
    void withAddedNameExpectNameInList() {
      String name = "abc";
      cut.addName("abc");
      assertThat(cut.getAll()).contains(name);
    }

    @Test
    void withAddedNameExpectSizeOne() {
      String name = "abc";
      cut.addName("abc");
      assertThat(cut.getAll()).hasSize(1);
    }
  }
}
