package de.conciso.codingdojo.rest;

import de.conciso.codingdojo.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingRepository extends JpaRepository<Greeting, Integer> {

}
