package io.github.jokerhasnopersonality.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class representing a task, assigned to a student.
 * Contains information about student's progress.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AssignedTask extends GroovyConfigurable {
    private String student;
    private boolean build;
    private boolean docGenerated;
    private boolean softDeadline = false;
    private boolean hardDeadline = false;
    private int testsCount;
    private int testsPassed;
    private String score;
}
