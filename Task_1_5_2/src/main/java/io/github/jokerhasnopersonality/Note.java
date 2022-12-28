package io.github.jokerhasnopersonality;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * Note class for Notebook realization.
 */
public class Note {
    private final String title;
    private final String note;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm", timezone = "VST")
    private final LocalDateTime time;

    /**
     * Note constructor.
     */
    public Note(String title, String note, LocalDateTime time) throws NullPointerException {
        if ((title == null) || (note == null) || (time == null)) {
            throw new NullPointerException();
        }
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
