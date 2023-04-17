package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveResumeToArray(Resume newResume, int index) {
        storage[countResume] = newResume;
    }

    @Override
    public void deleteResumeToArray(int index) {
        storage[index] = storage[countResume - 1];
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