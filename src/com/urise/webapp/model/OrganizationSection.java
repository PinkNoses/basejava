package com.urise.webapp.model;


import java.util.List;

public class OrganizationSection extends Section {
    private final List<Organization> list;

    public OrganizationSection(List<Organization> list) {
        this.list = list;
    }

    public List<Organization> getList() {
        return list;
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
