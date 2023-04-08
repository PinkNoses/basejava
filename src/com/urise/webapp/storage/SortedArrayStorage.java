package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveResume(Resume newResume) {
        int index = findIndex(newResume.getUuid());
        System.arraycopy(storage, Math.abs(index) - 1, storage, Math.abs(index), countResume - (Math.abs(index) - 1));
        storage[Math.abs(index) - 1] = newResume;
        countResume++;
    }

    @Override
    public void deleteResume(String uuid) {
        int index = findIndex(uuid);
        System.arraycopy(storage, index + 1, storage, index, size() - index - 1);
        storage[size() - 1] = null;
        countResume--;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResume, searchKey);
    }
}