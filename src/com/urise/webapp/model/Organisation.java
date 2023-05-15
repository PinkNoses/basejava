package com.urise.webapp.model;

import java.time.LocalDate;

public class Organisation extends Section {

    private final LocalDate dateStart;
    private final LocalDate dateEnd;
    private final String organization;
    private final String position;
    private final String comment;

    public Organisation(LocalDate dateStart, LocalDate dateEnd, String organization, String position, String comment) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.organization = organization;
        this.position = position;
        this.comment = comment;
    }

    @Override
    public String toString() {
        if (comment == null) {
            return "\n" + organization + "\n" + dateStart + " - " + dateEnd + "\n" + position;
        }
        return "\n" + organization + "\n" + dateStart + " - " + dateEnd + "\n" + position + "\n" + comment;
    }
}
