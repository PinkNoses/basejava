package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void save(Resume newResume) {
        int index = findIndex(newResume.getUuid());
        if (isExist(index)) {
            throw new ExistStorageException(newResume.getUuid());
        } else {
            saveResume(newResume, index);
        }
    }

    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (isExist(index)) {
            updateResume(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            deleteResume(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            return getResume(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected boolean isExist(int index) {
        return index > -1;
    }

    public abstract Resume[] getAll();

    public abstract void clear();

    public abstract int size();

    protected abstract int findIndex(String uuid);

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void updateResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

    protected abstract Resume getResume(int index);
}