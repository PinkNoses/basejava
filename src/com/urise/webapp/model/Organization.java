package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final String organizationName;
    private final String website;
    private final List<Period> periods;

    public Organization(String organizationName, String website, List<Period> periods) {
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
        int result = organizationName != null ? organizationName.hashCode() : 0;
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
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
}