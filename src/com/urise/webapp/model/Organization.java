package com.urise.webapp.model;

import java.util.List;

public class Organization {

    private final String organizationName;
    private final String website;
    private final List<Period> periods;

    public Organization(String organizationName, String website, List<Period> periods) {
        this.organizationName = organizationName;
        this.website = website;
        this.periods = periods;
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