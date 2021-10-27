package de.conciso.codingdojo;


import static org.assertj.core.api.Assertions.assertThat;

import de.conciso.codingdojo.rest.HelloController;
import de.conciso.codingdojo.rest.ImmutableMessage;
import de.conciso.codingdojo.rest.ImmutableName;
import de.conciso.codingdojo.rest.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HelloControllerTest {

  HelloController cut;

  @Nested
  class PostWelcome {

    @BeforeEach
    void setUp() {
      cut = new HelloController();
    }

    @Test
    public void withNamePeterReturn200() {
      ResponseEntity<Message> responseEntity = cut.postGreeting(ImmutableName.builder().name("Peter").build());
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void withNamePeterReturnHisName() {
      ResponseEntity<Message> responseEntity = cut.postGreeting(ImmutableName.builder().name("Peter").build());
      assertThat(responseEntity.getBody()).isEqualTo(ImmutableMessage.builder().message("Welcome Peter").build());
    }

    @Test
    public void withNullNameReturn400() {
      ResponseEntity<Message> responseEntity = cut.postGreeting(ImmutableName.builder().name(null).build());
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
  }

}
