package io.github.jokerhasnopersonality.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Task extends GroovyConfigurable {
    private String id;
    private String name;
    private int maxPoints;
    private String deadline;
}
