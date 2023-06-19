package io.github.jokerhasnopersonality.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class representing a group of students.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Group extends GroovyConfigurable {
    private String id;
    private List<Student> students;
}
