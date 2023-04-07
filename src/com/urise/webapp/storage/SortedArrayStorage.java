package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume newResume) {
        int index = findIndex(newResume.getUuid());
        if (countResume >= STORAGE_LIMIT) {
            System.out.println("\nНевозможно добавить резюме. База данных переполнена.");
        } else if (isExist(index)) {
            System.out.println("Резюме " + newResume.getUuid() + " уже существует в базе данных.");
        } else {
            System.arraycopy(storage, Math.abs(index) - 1, storage, Math.abs(index), countResume);
            storage[Math.abs(index) - 1] = newResume;
            countResume++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            System.arraycopy(storage, index + 1, storage, index, size() - index - 1);
            storage[size() - 1] = null;
            countResume--;
        } else {
            System.out.println("Невозможно удалить резюме. Резюме " + uuid + " не существует в базе данных.");
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResume, searchKey);
    }
}