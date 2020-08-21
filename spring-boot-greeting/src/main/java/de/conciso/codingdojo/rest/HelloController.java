package de.conciso.codingdojo.rest;

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

  @PostMapping(path = "/",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<String> postGreeting() {
    return ResponseEntity.ok().build();
  }

  @GetMapping(path = "/",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<String> getPastGreetings() {
    return ResponseEntity.ok().build();
  }
}
