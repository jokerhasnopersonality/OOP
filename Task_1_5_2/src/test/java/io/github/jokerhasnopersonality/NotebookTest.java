package io.github.jokerhasnopersonality;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for Notebook realization.
 */
public class NotebookTest {
    @Test
    public void simple_test() throws IOException {
        Notebook notebook = new Notebook();
        notebook.addNote("Title", "Lorem ipsum dolor note");
        notebook.saveNotes();
        Assertions.assertEquals("Lorem ipsum dolor note", notebook.getNotes().get(0).getNote());
        notebook.addNote("Interesting fact",
                "Hummingbirds are the only known birds that can also fly backwards");
        notebook.addNote("Another title", "Another note");
        notebook.addNote("Hotel", "Trivago");
        Assertions.assertEquals(4, notebook.getNotes().size());
        notebook.removeNote("Title");
        Assertions.assertEquals(3, notebook.getNotes().size());
        notebook.saveNotes();

        LocalDateTime time = notebook.getNotes().get(0).getTime();
        List<Note> notes = notebook.getNotes(
                time.minusMinutes(2), LocalDateTime.now(), new String[]{"Hummingbirds"});
        Assertions.assertEquals(1, notes.size());
        Assertions.assertEquals("Interesting fact", notes.get(0).getTitle());
    }


}