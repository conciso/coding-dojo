package de.conciso.codingdojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
public class Person {

    @NonNull
    private String name;
}
