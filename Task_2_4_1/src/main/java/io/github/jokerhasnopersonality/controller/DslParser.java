package io.github.jokerhasnopersonality.controller;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import io.github.jokerhasnopersonality.TaskInspector;
import io.github.jokerhasnopersonality.model.TestConfiguration;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Objects;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * Class for parsing groovy files to DSL models.
 */
public class DslParser {
    /**
     * Parses a groovy file of the specified path into a Test Configuration model.

     * @param path location of the groovy file
     */
    public static TestConfiguration parseConfiguration(String path) throws URISyntaxException {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(TaskInspector.class.getClassLoader(), new Binding(), cc);
        DelegatingScript script = (DelegatingScript) sh.parse(
                new InputStreamReader(Objects.requireNonNull(
                        TaskInspector.class.getResourceAsStream(path))));

        TestConfiguration config = new TestConfiguration();
        config.setScriptPath(Objects.requireNonNull(TaskInspector.class.getResource(path)).toURI());
        script.setDelegate(config);
        script.run();
        config.postProcess();
        return config;
    }
}
