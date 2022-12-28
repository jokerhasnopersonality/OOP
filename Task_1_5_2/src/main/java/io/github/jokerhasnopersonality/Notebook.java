package io.github.jokerhasnopersonality;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Notebook class for managing a single json file representing a notebook.
 */
public class Notebook {
    private static List<Note> notes;
    private static ObjectMapper mapper = null;

    /**
     * Notebook constructor.
     */
    public Notebook() {
        notes = new ArrayList<>();
        mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public void addNote(String title, String note) {
        Note add = new Note(title, note, LocalDateTime.now());
        notes.add(add);
    }

    public void removeNote(String title) {
        notes.removeIf(x -> x.getTitle().equals(title));
    }

    public List<Note> getNotes() {
        return notes;
    }

    /**
     * If time boundaries and keywords are specified,
     * getNotes returns a filtered list of notes.
     */
    public List<Note> getNotes(LocalDateTime since, LocalDateTime till, String[] keywords) {
        return notes.stream().filter(
                x -> x.getTime().isAfter(since)
                && x.getTime().isBefore(till)
                && Arrays.stream(keywords).anyMatch(
                        w -> x.getNote().contains(w))).collect(Collectors.toList()
        );
    }

    public void saveNotes() throws IOException {
        mapper.writeValue(Paths.get("notebook.json").toFile(), notes);
    }

    public void loadNotes() throws IOException {
        notes = Arrays.asList(mapper.readValue(Paths.get("notebook.json").toFile(), Note[].class));
    }

    /**
     * Outputs the specified list of notes using default PrettyPrint method.
     */
    public void prettyPrint(List<Note> notes) throws JsonProcessingException {
        for (Note note : notes) {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(note));
        }
    }
}