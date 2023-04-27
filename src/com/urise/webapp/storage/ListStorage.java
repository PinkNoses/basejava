package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void saveResume(Resume newResume, Object searchKey) {
        storage.add(newResume);
    }

    @Override
    public void updateResume(Resume resume, Object searchKey) {
        storage.set((Integer) searchKey, resume);
    }

    @Override
    public void deleteResume(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    public Resume getResume(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return storage;
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey > -1;
    }
}