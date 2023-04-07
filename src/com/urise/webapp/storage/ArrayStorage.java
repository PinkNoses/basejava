package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume newResume) {
        int index = findIndex(newResume.getUuid());
        if (countResume >= STORAGE_LIMIT) {
            System.out.println("\nНевозможно добавить резюме. База данных переполнена.");
        } else if (isExist(index)) {
            System.out.println("Резюме " + newResume.getUuid() + " уже существует в базе данных.");
        } else {
            storage[countResume] = newResume;
            countResume++;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            storage[index] = storage[countResume - 1];
            storage[countResume - 1] = null;
            countResume--;
        } else {
            System.out.println("Невозможно удалить резюме. Резюме " + uuid + " не существует в базе данных.");
        }
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}