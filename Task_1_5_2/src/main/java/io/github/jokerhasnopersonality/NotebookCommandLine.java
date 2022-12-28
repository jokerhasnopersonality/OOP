package io.github.jokerhasnopersonality;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Notebook realization for command line.
 */
@Command(name = "notebook")
public class NotebookCommandLine implements Callable<Integer> {
    private Notebook notebook;

    public NotebookCommandLine(Notebook notebook) {
        this.notebook = notebook;
    }

    @Command(name = "-add")
    public void add(@Parameters() String title, @Parameters() String note) {
        notebook.addNote(title, note);
    }

    @Command(name = "-rm")
    public void remove(@Parameters() String title) {
        notebook.removeNote(title);
    }

    /**
     * Subcommand for showing all the notes or showing
     * a filtered list of notes according to the parameters.
     */
    @Command(name = "-show")
    public void show(@Option(names = "-show") boolean all,
                     @Parameters String[] keywords) throws JsonProcessingException {
        List<Note> list = null;
        LocalDateTime s;
        LocalDateTime t;
        if (keywords != null && keywords.length > 2) {
            try {
                s = LocalDateTime.parse(keywords[0],
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                t = LocalDateTime.parse(keywords[1],
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                list = notebook.getNotes(s, t, Arrays.copyOfRange(keywords, 2, keywords.length));
            } catch (DateTimeParseException e) {
                throw new IllegalStateException("Couldn't parse time boundaries.");
            }
        } else {
            list = notebook.getNotes();
        }
        notebook.prettyPrint(list);
    }

    @Override
    public Integer call() throws Exception {
        return 0;
    }

    /**
     * Creates a new notebook, loads contents from a json file,
     * executes subcommands and saves changes in notebook.
     */
    public static void main(String[] args) {
        Notebook book = new Notebook();
        try {
            book.loadNotes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load book.");
        }

        int exitCode = new CommandLine(new NotebookCommandLine(book)).execute(args);

        try {
            book.saveNotes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save book.");
        }
        System.exit(exitCode);
    }
}
