package de.conciso.codingdojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableResultMessage.class)
@JsonDeserialize(as = ImmutableResultMessage.class)
public interface ResultMessage {
    String message();
}
