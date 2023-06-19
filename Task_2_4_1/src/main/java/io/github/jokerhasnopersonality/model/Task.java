package io.github.jokerhasnopersonality.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class representing a task.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Task extends GroovyConfigurable {
    private String id;
    private String name;
    private int maxPoints;
    private String softDeadline;
    private String hardDeadline;
    private List<AssignedTask> progress;
}
