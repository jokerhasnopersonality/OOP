package io.github.jokerhasnopersonality;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class CommandLineTest {
    @Test
    public void simpleTest() throws IOException {
        Notebook notebook =  new Notebook();
        NotebookCommandLine cmdnotebook = new NotebookCommandLine(new Notebook());
        CommandLine result = new CommandLine(cmdnotebook);
        result.execute("-add", "Title", "TeXtTeXtTeXt");
        result.execute("-add", "S1", "Sweet dreams are made of this");
        result.execute("-add", "S2", "Who am I to disagree?");
        result.execute("-add", "S3", "I traveled the world and the seven seas");
        result.execute("-add", "S4", "Everybody is looking for something");
        Assertions.assertEquals(5, notebook.getNotes().size());
        result.execute("-rm", "Title");
        Assertions.assertEquals(4, notebook.getNotes().size());
        notebook.saveNotes();

        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        result.execute("-show");
        InputStream in = new FileInputStream("output.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<Object> titles = List.of(reader.lines().filter(x -> x.contains("title")).toArray());
        Assertions.assertEquals(4, titles.size());
        Assertions.assertTrue(titles.get(0).toString().contains("S1"));
        Assertions.assertTrue(titles.get(1).toString().contains("S2"));
        Assertions.assertTrue(titles.get(2).toString().contains("S3"));
        Assertions.assertTrue(titles.get(3).toString().contains("S4"));
    }
}