package de.conciso.codingdojo.rest;

import de.conciso.codingdojo.model.Greeting;
import de.conciso.codingdojo.model.Person;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class HelloController {

  GreetingRepository greetingRepository;

  public HelloController(GreetingRepository greetingRepository) {
    this.greetingRepository = greetingRepository;
  }

  @PostMapping(path = "/",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Greeting> postGreeting(Person person) {
    return ResponseEntity.ok(new Greeting("Hallo " + person.getName()));
  }

  @GetMapping(path = "/",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<Greeting>> getPastGreetings() {
    List<Greeting> result = greetingRepository.findAll();
    return ResponseEntity.ok(result);
  }
}
