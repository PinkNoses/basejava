package com.urise.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String organizationName;
    private final String website;
    private final List<Period> periods;

    public Organization(String organizationName, String website, Period... periods) {
        this(organizationName, website, Arrays.asList(periods));
    }

    public Organization(String organizationName, String website, List<Period> periods) {
        Objects.requireNonNull(organizationName, "organizationName must not be null");
        Objects.requireNonNull(website, "website must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.organizationName = organizationName;
        this.website = website;
        this.periods = periods;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!Objects.equals(organizationName, that.organizationName))
            return false;
        if (!Objects.equals(website, that.website)) return false;
        return Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        int result = organizationName.hashCode();
        result = 31 * result + website.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Period period : periods) {
            sb.append(period).append("\n");
        }
        return organizationName + "\n" + sb;
    }

    public static class Period implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

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
}