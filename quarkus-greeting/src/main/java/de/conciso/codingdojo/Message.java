package de.conciso.codingdojo;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class Message {
    @NotBlank
    String name;

    public static MessageEntity toStoredMessage(Message name) {
        return new MessageEntity(name.getName());
    }
}
