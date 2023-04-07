package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {

    void update(Resume resume);

    Resume get(String uuid);

    Resume[] getAll();

    void clear();

    void save(Resume newResume);

    void delete(String uuid);

    int size();
}
