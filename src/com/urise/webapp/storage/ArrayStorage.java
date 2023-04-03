package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int countResume;

    public void update(Resume resume) {
        if (isPresent(resume.getUuid())) {
            resume.setUuid("newUuid");
        } else {
            System.out.println("Невозможно обновить резюме. Резюме " + resume.getUuid() + " отсутствует в базе данных.");
        }

    }

    public void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    public void save(Resume newResume) {
        if (!isPresent(newResume.getUuid())) {
            if (countResume <= storage.length) {
                storage[countResume] = newResume;
                countResume++;
            } else {
                System.out.println("\nНевозможно добавить резюме. База данных переполнена.");
            }
        } else {
            System.out.println("Резюме " + newResume.getUuid() + " уже существует в базе данных.");
        }
    }

    public Resume get(String uuid) {
        if (isPresent(uuid)) {
            int index = findIndex(uuid);
            return storage[index];
        }
        System.out.println("Резюме " + uuid + " не существует в базе данных.");
        return null;
    }

    public void delete(String uuid) {
        if (isPresent(uuid)) {
            int index = findIndex(uuid);
            System.arraycopy(storage, index + 1, storage, index, countResume - index - 1);
            countResume--;
            storage[countResume] = null;
        } else {
            System.out.println("Невозможно удалить резюме. Резюме " + uuid + " не существует в базе данных.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    public int size() {
        return countResume;
    }

    private boolean isPresent(String uuid) {
        return findIndex(uuid) > -1;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
