package de.conciso.codingdojo.rest;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(as = ImmutableName.class)
public interface Name {
  @Nullable
  String name();
}
