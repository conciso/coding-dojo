package de.conciso.codingdojo;

import java.util.List;

public interface WelcomeRepository {

  void add(NameEntity name);

  List<NameEntity> getAll();

}
