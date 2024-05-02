package de.conciso.codingdojo;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class WelcomeService {

  private final WelcomeRepository welcomeRepository;

  public WelcomeService(WelcomeRepository welcomeRepository) {
    this.welcomeRepository = welcomeRepository;
  }

  public void addName(String name) {
    Objects.requireNonNull(name, "name is null");

    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name is empty");
    }
    welcomeRepository.add(new NameEntity(name));
  }

  public List<String> getAll() {
    return welcomeRepository.getAll().stream()
        .map(NameEntity::getName).collect(Collectors.toList());
  }
}
