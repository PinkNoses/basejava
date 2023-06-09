package com.urise.webapp.model;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<Organization> list;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... list) {
        this(Arrays.asList(list));
    }

    public OrganizationSection(List<Organization> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public List<Organization> getListOrganization() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization organisation : list) {
            sb.append(organisation);
        }
        return sb.toString();
    }
}
