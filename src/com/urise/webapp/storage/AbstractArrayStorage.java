package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResume;

    @Override
    public final void saveResume(Resume newResume, Object index) {
        if (countResume >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", newResume.getUuid());
        }
        saveResumeInArray(newResume, (int) index);
        countResume++;
    }

    @Override
    public void updateResume(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    public void deleteResume(Object index) {
        deleteResumeInArray((int) index);
        storage[countResume - 1] = null;
        countResume--;
    }

    @Override
    public Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    @Override
    public int size() {
        return countResume;
    }

    @Override
    protected boolean isExist(Object index) {
        return (int) index > -1;
    }

    protected abstract void saveResumeInArray(Resume resume, int index);

    protected abstract void deleteResumeInArray(int index);
}