package de.conciso.codingdojo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.conciso.codingdojo.model.Greeting;
import de.conciso.codingdojo.model.Person;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HelloControllerTest {

  @Mock
  GreetingRepository greetingRepository;

  @InjectMocks
  HelloController cut;

  @Test
  public void postGreeting_shouldRespond_Hallo_Andrej() {
    Greeting expected = new Greeting("Hallo Andrej");
    Person person = new Person("Andrej");
    assertThat(cut.createGreeting(person).getBody()).isEqualTo(expected);
  }

  @Test
  public void postGreeting_shouldSaveGreetingAndrej() {
    Greeting expected = new Greeting("Hallo Andrej");
    Person person = new Person("Andrej");

    cut.createGreeting(person);
    verify(greetingRepository).save(expected);
  }

  @Test
  public void postGreeting_shouldRespond_Hallo_Dimi() {
    Greeting expected = new Greeting("Hallo Dimi");
    Person person = new Person("Dimi");
    assertThat(cut.createGreeting(person).getBody()).isEqualTo(expected);
  }

  @Test
  public void getPastGreetings_shouldRespond_AllPastGreetings() {
    when(greetingRepository.findAll()).thenReturn(Arrays.asList(new Greeting("Hallo Dimi"), new Greeting("Hallo Andrej")));

    assertThat(cut.getPastGreetings().getBody())
        .containsExactlyInAnyOrder(new Greeting("Hallo Dimi"), new Greeting("Hallo Andrej"));
  }
}