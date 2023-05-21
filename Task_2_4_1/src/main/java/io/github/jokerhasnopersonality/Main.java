package io.github.jokerhasnopersonality;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import io.github.jokerhasnopersonality.model.TestConfiguration;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws URISyntaxException, GitAPIException {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);
        String filePath = "/config.groovy";
        DelegatingScript script = (DelegatingScript)sh.parse(
                new InputStreamReader(Objects.requireNonNull(Main.class.getResourceAsStream(filePath))));

        TestConfiguration config = new TestConfiguration(); // наш бин с конфигурацией
        config.setScriptPath(Objects.requireNonNull(Main.class.getResource(filePath)).toURI());
        script.setDelegate(config);
        script.run();
        config.postProcess();
        //System.out.println(config);
    }
}