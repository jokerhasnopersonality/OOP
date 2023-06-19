package io.github.jokerhasnopersonality.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import io.github.jokerhasnopersonality.model.Group;
import io.github.jokerhasnopersonality.model.Task;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Constructor for report tables in html format.
 */
public class HtmlReportConstructor {
    private Map<String, Object> context;

    public HtmlReportConstructor() {
        context = new HashMap<>();
    }

    /**
     * Generates html report for a group using a template.

     * @param group group to generate a report for
     * @param tasks tasks to generate a report about
     */
    public void generateReport(Group group, List<Task> tasks, String resourcesPath) {
        context.put("group", group);
        context.put("tasks", tasks);
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setDirectoryForTemplateLoading(new File(resourcesPath));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            Template template = cfg.getTemplate("template.ftl");

            // File output
            Writer file = new FileWriter(resourcesPath + "reports/"
                    + String.format("report%s.html", group.getId()));
            template.process(context, file);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
