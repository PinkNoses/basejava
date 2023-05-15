package com.urise.webapp.model;

import java.util.List;

public class ListSection extends Section {
    private final List<String> list;

    public ListSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String element : list) {
            sb.append(element).append("\n");
        }
        return sb.toString();
    }
}
