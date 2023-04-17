package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {

    void save(Resume newResume);

    void update(Resume resume);

    void delete(String uuid);

    Resume get(String uuid);

    Resume[] getAll();

    void clear();

    int size();
}