package io.github.jokerhasnopersonality.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class TestConfiguration extends GroovyConfigurable {
    private ArrayList<Task> tasks;
    private Group group;
    private List<String> handout;
    private List<String> checkpoints;
    private List<String> seminars;
}
