package com.urise.webapp.model;


import java.util.List;

public class OrganisationSection extends Section {
    private final List<Organisation> list;

    public OrganisationSection(List<Organisation> list) {
        this.list = list;
    }

    public List<Organisation> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organisation organisation : list) {
            sb.append(organisation).append("\n");
        }
        return sb.toString();
    }
}
