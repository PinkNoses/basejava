package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveResume(Resume newResume, int index) {
        System.arraycopy(storage, Math.abs(index) - 1, storage, Math.abs(index), countResume - (Math.abs(index) - 1));
        storage[Math.abs(index) - 1] = newResume;
    }

    @Override
    public void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size() - index - 1);
        storage[size() - 1] = null;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResume, searchKey);
    }
}