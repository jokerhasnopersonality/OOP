package io.github.jokerhasnopersonality;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class NotebookTest {
    @Test
    public void simple_test() throws IOException {
        Notebook notebook = new Notebook();
        notebook.addNote("Title", "Noooooooote");
        notebook.saveNotes("notebook.json");
    }


}