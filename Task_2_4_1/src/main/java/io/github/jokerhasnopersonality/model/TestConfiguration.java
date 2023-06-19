package io.github.jokerhasnopersonality.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class for structuring test configuration parameters.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestConfiguration extends GroovyConfigurable {
    private ArrayList<Task> tasks;
    private List<Group> groups;
    private List<String> handout;
    private List<String> checkpoints;
    private List<String> seminars;
}
