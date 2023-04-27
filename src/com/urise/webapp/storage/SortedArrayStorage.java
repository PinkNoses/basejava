package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    public void saveResumeInArray(Resume newResume, int index) {
        System.arraycopy(storage, Math.abs(index) - 1, storage, Math.abs(index), countResume - (Math.abs(index) - 1));
        storage[Math.abs(index) - 1] = newResume;
    }

    @Override
    public void deleteResumeInArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResume - index - 1);
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, countResume, searchKey, RESUME_COMPARATOR);
    }
}