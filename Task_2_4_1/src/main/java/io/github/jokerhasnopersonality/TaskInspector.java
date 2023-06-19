package io.github.jokerhasnopersonality;

import io.github.jokerhasnopersonality.controller.DslParser;
import io.github.jokerhasnopersonality.controller.GitManager;
import io.github.jokerhasnopersonality.controller.GradleInspector;
import io.github.jokerhasnopersonality.controller.HtmlReportConstructor;
import io.github.jokerhasnopersonality.model.AssignedTask;
import io.github.jokerhasnopersonality.model.Group;
import io.github.jokerhasnopersonality.model.Student;
import io.github.jokerhasnopersonality.model.Task;
import io.github.jokerhasnopersonality.model.TestConfiguration;
import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * Main class for inspecting labs from configuration file.
 */
public class TaskInspector {
    private static final String configPath = "/config.groovy";
    private static final String labsPath = "Task_2_4_1/src/main/resources/labs/";
    private static final String resourcesPath = "Task_2_4_1/src/main/resources/";

    /**
     * Method that analyzes configuration file and
     * generates html report for every group from file.
     */
    public static void main(String[] args) throws URISyntaxException, DocumentException {
        TestConfiguration config = DslParser.parseConfiguration(configPath);
        System.out.println(config);

        List<Task> checkTasks = config.getTasks().stream().filter(task ->
                config.getHandout().contains(task.getId())).collect(Collectors.toList());
        HtmlReportConstructor constructor = new HtmlReportConstructor();
        for (Group group : config.getGroups()) {
            for (Task t : checkTasks) {
                t.setProgress(new ArrayList<>());
            }
            for (Student student : group.getStudents()) {
                // clone a repository from GitHub
                GitManager.getRepo(student.getRepository(), student.getId(), labsPath);

                for (Task task : checkTasks) {
                    // compile a task, run tests, generate documentation
                    GradleInspector.inspectGradleTasks(labsPath
                            + student.getId()
                            + "/"
                            + task.getId());

                    // process results
                    AssignedTask assigned = new AssignedTask();
                    assigned.setStudent(student.getFullName());
                    File testRes = new File(labsPath
                            + student.getId()
                            + "/"
                            + task.getId() + "/build/test-results/test/");
                    File test = Arrays.stream(Objects.requireNonNull(testRes.listFiles()))
                            .filter(x -> x.isFile() && x.getName().endsWith(".xml"))
                            .collect(Collectors.toList())
                            .get(0);
                    assigned.setBuild(testRes.getParentFile().getParentFile()
                            .isDirectory());
                    assigned.setDocGenerated(
                            Arrays.stream(testRes.getParentFile().getParentFile().listFiles())
                            .anyMatch(x -> x.getName().equals("docs")));

                    SAXReader reader = new SAXReader();
                    Document document = reader.read(test);
                    assigned.setTestsCount(Integer.parseInt(document.selectSingleNode("testsuite")
                            .valueOf("@tests")));
                    assigned.setTestsPassed(assigned.getTestsCount()
                            - Integer.parseInt(document.selectSingleNode("testsuite")
                            .valueOf("@failures")));
                    double score = -0.5;
                    if (assigned.isBuild() && assigned.isDocGenerated()
                            && assigned.getTestsCount() == assigned.getTestsPassed()) {
                        score += 0.5;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        formatter = formatter.withLocale(Locale.ENGLISH);
                        if (LocalDate.parse(task.getSoftDeadline(),
                                formatter).isAfter(LocalDate.now())) {
                            assigned.setSoftDeadline(true);
                            score += 0.5;
                        }
                        if (LocalDate.parse(task.getHardDeadline(),
                                formatter).isAfter(LocalDate.now())) {
                            assigned.setHardDeadline(true);
                            score += 0.5;
                        }
                    }
                    assigned.setScore(String.format("%.1f",
                            Math.ceil((score * task.getMaxPoints()) * 2) / 2));

                    task.getProgress().add(assigned);
                }
            }

            constructor.generateReport(group, checkTasks, resourcesPath);
        }
    }
}