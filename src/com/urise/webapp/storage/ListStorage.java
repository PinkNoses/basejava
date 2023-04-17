package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> listStorage = new ArrayList<>();

    @Override
    public void saveResume(Resume newResume, int index) {
        listStorage.add(newResume);
    }

    @Override
    public void updateResume(Resume resume, int index) {
        listStorage.set(index, resume);
    }

    @Override
    public void deleteResume(int index) {
        listStorage.remove(index);
    }

    @Override
    public Resume getResume(int index) {
        return listStorage.get(index);
    }

    @Override
    public Resume[] getAll() {
        return listStorage.toArray(listStorage.toArray(new Resume[0]));
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public int size() {
        return listStorage.size();
    }

    @Override
    protected int findIndex(String uuid) {
        return listStorage.indexOf(new Resume(uuid));
    }
}