package io.github.jokerhasnopersonality;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Note {
    private final String title;
    private final String note;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm", timezone = "VST")
    private final LocalDateTime time;

    public Note(String title, String note, LocalDateTime time) {
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

    public LocalDateTime getTime() {
        return time;
    }

}
