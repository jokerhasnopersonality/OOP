package io.github.jokerhasnopersonality;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;


import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Notebook {
    private List<Note> notes;
    private final ObjectMapper mapper;

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

    public List<Note> getNotes(LocalDateTime since, LocalDateTime till, String[] keywords) {
        return notes.stream().filter(
                x -> x.getTime().isAfter(since)
                && x.getTime().isBefore(till)
                && Arrays.stream(keywords).anyMatch(
                        w -> x.getNote().contains(w))).collect(Collectors.toList()
        );
    }

    public void saveNotes(String string) throws IOException {
        mapper.writeValue(Paths.get(string).toFile(), notes);
    }

    public void loadNotes(String string) throws IOException {
        notes = Arrays.asList(mapper.readValue(Paths.get(string).toFile(), Note[].class));
    }
}