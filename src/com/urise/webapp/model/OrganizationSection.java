package com.urise.webapp.model;


import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    private final List<Organization> list;

    public OrganizationSection(List<Organization> list) {
        this.list = list;
    }

    public List<Organization> getList() {
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
        return list != null ? list.hashCode() : 0;
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
