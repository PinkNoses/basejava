package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResume;

    @Override
    public final void saveResume(Resume newResume, Integer index) {
        if (countResume >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", newResume.getUuid());
        }
        saveResumeInArray(newResume, index);
        countResume++;
    }

    @Override
    public void updateResume(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    public void deleteResume(Integer index) {
        deleteResumeInArray(index);
        storage[countResume - 1] = null;
        countResume--;
    }

    @Override
    public Resume getResume(Integer index) {
        return storage[index];
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
    protected List<Resume> doCopyAll() {
        Resume[] arrayOfResume = Arrays.copyOf(storage, countResume);
        return Arrays.asList(arrayOfResume);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index > -1;
    }

    protected abstract void saveResumeInArray(Resume resume, int index);

    protected abstract void deleteResumeInArray(int index);
}