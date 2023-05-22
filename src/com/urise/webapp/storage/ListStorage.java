package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void saveResume(Resume newResume, Integer searchKey) {
        storage.add(newResume);
    }

    @Override
    public void updateResume(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
    }

    @Override
    public void deleteResume(Integer searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    public Resume getResume(Integer searchKey) {
        return storage.get(searchKey);
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
    protected boolean isExist(Integer searchKey) {
        return searchKey > -1;
    }
}