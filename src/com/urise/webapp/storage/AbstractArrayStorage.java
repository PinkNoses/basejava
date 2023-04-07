package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResume;

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (isExist(index)) {
            resume.setUuid("newUuid");
        } else {
            System.out.println("Невозможно обновить резюме. Резюме " + resume.getUuid() + " отсутствует в базе данных.");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            return storage[index];
        }
        System.out.println("Резюме " + uuid + " не существует в базе данных.");
        return null;
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
}
