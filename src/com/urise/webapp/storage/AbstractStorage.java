package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    final Comparator<Resume> resumeComparator = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    public final void save(Resume newResume) {
        SK searchKey = getNotExistingSearchKey(newResume.getUuid());
        saveResume(newResume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        SK searchKey = getExistingSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
    }

    @Override
    public final void delete(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        deleteResume(searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listResume = doCopyAll();
        listResume.sort(resumeComparator);
        return listResume;
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        }
        throw new NotExistStorageException(uuid);
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract SK findSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void saveResume(Resume resume, SK searchKey);

    protected abstract void updateResume(Resume resume, SK searchKey);

    protected abstract void deleteResume(SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract List<Resume> doCopyAll();
}