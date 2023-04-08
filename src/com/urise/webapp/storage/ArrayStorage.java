package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveResume(Resume newResume) {
        int index = findIndex(newResume.getUuid());
        storage[countResume] = newResume;
        countResume++;
    }

    @Override
    public void deleteResume(String uuid) {
        int index = findIndex(uuid);
        storage[index] = storage[countResume - 1];
        storage[countResume - 1] = null;
        countResume--;
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}