package com.urise.webapp.model;

import java.time.LocalDate;

public class Period {
    private final LocalDate dateStart;
    private final LocalDate dateEnd;
    private final String title;
    private final String description;

    public Period(LocalDate dateStart, LocalDate dateEnd, String title, String description) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        if (description == null) {
            return dateStart + " - " + dateEnd + "\n" + title + "\n";
        }
        return dateStart + " - " + dateEnd + "\n" + title + "\n" + description + "\n";
    }
}