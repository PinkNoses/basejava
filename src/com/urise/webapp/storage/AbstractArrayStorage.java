package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResume;

    public final void save(Resume newResume) {
        int index = findIndex(newResume.getUuid());
        if (countResume >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", newResume.getUuid());
        } else if (isExist(index)) {
            throw new ExistStorageException(newResume.getUuid());
        } else {
            saveResume(newResume, index);
            countResume++;
        }
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            deleteResume(index);
            storage[countResume - 1] = null;
            countResume--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (isExist(index)) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    public int size() {
        return countResume;
    }

    protected boolean isExist(int index) {
        return index > -1;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
}
