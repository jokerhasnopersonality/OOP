package io.github.jokerhasnopersonality.controller;

import java.io.File;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

/**
 * Class for running jobs like build, test, javadoc with gradle.
 */
public class GradleInspector {
    /**
     * Compiles a specified project, runs tests and generates a documentation.

     * @param pathToProject path to project that needs to be checked
     */
    public static void inspectGradleTasks(String pathToProject) {
        // compile a task, run tests, generate documentation
        ProjectConnection connection = GradleConnector
                .newConnector()
                .forProjectDirectory(new File(pathToProject))
                .connect();
        try {
            BuildLauncher build = connection.newBuild();
            build.forTasks("build", "test", "javadoc");
            build.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
