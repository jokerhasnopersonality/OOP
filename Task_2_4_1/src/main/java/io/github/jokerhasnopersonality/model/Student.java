package io.github.jokerhasnopersonality.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Student extends GroovyConfigurable {
    private String id;
    private String fullName;
    private String repository;
}
