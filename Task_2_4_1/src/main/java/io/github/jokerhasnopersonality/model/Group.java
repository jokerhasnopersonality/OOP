package io.github.jokerhasnopersonality.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Group extends GroovyConfigurable {
    private String id;
    private List<Student> students;
}
