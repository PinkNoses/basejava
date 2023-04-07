package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MainTestSortedArrayStorage {
    private static final Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume();
        resume1.setUuid("uuid1");
        Resume resume2 = new Resume();
        resume2.setUuid("uuid5");
        Resume resume3 = new Resume();
        resume3.setUuid("uuid3");
        Resume resume4 = new Resume();
        resume4.setUuid("uuid2");
        Resume resume5 = new Resume();
        resume5.setUuid("uuid0");

        SORTED_ARRAY_STORAGE.save(resume1);
        SORTED_ARRAY_STORAGE.save(resume2);
        SORTED_ARRAY_STORAGE.save(resume3);
        SORTED_ARRAY_STORAGE.save(resume4);
        SORTED_ARRAY_STORAGE.save(resume5);

        printAll();

        System.out.println("\nGet resume3: " + SORTED_ARRAY_STORAGE.get(resume3.getUuid()));
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + SORTED_ARRAY_STORAGE.get("dummy"));

        SORTED_ARRAY_STORAGE.delete(resume1.getUuid());
        printAll();

        SORTED_ARRAY_STORAGE.update(resume1);
        SORTED_ARRAY_STORAGE.update(resume3);
        printAll();

        SORTED_ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : SORTED_ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}