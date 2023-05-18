package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> list;

    public ListSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
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