package de.conciso.codingdojo.rest;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize
@JsonDeserialize(as = ImmutableMessage.class)
public interface Message {
  String message();
}
