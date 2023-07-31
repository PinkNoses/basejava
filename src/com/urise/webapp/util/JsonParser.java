package com.urise.webapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.urise.webapp.model.Section;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

    public static String write(Section section, Class<Section> sectionClass) {
        return GSON.toJson(section, sectionClass);
    }

    public static Section read(String value, Class<Section> sectionClass) {
        return GSON.fromJson(value, sectionClass);
    }
}