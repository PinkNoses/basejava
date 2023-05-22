package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private final LocalDate dateStart;
    private final LocalDate dateEnd;
    private final String title;
    private final String description;

    public Period(LocalDate dateStart, LocalDate dateEnd, String title, String description) {
        Objects.requireNonNull(dateStart, "dateStart must not be null");
        Objects.requireNonNull(dateEnd, "dateEnd must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
        this.description = description;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!Objects.equals(dateStart, period.dateStart)) return false;
        if (!Objects.equals(dateEnd, period.dateEnd)) return false;
        if (!Objects.equals(title, period.title)) return false;
        return Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        int result = dateStart.hashCode();
        result = 31 * result + dateEnd.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (description == null) {
            return dateStart + " - " + dateEnd + "\n" + title + "\n";
        }
        return dateStart + " - " + dateEnd + "\n" + title + "\n" + description + "\n";
    }
}