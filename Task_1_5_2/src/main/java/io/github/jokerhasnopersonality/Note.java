package io.github.jokerhasnopersonality;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Note {
    private final String title;
    private final String note;
    private final Instant time;

    public Note(String title, String note, Instant time) {
        this.title = title;
        this.note = note;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public Instant getTime() {
        return time;
    }

}
