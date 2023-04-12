package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void save() {
        Resume newResume = new Resume("new uuid");
        storage.save(newResume);
    }

    @Test
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Storage overflow ahead of time");
        }
        Resume newResume = new Resume("new uuid");
        Assertions.assertThrows(StorageException.class, () -> storage.save(newResume));
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(resume1));
    }

    @Test
    public void delete() {
        storage.delete(resume2.getUuid());
        Assertions.assertEquals(2, storage.size());
    }

    @Test
    public void deleteNotExist() {
        Resume newResume = new Resume("new uuid");
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(newResume.getUuid()));
    }

    @Test
    public void updateNotExist() {
        Resume newResume = new Resume("new resume");
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(newResume));
    }

    @Test
    public void update() {
        storage.update(resume1);
    }

    @Test
    public void get() {
        storage.get(resume1.getUuid());
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    public void getAll() {
        Resume[] expected = storage.getAll();
        Resume[] actual = new Resume[]{resume1, resume2, resume3};
        Assertions.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    public void size() {
        Assertions.assertEquals(3, storage.size());
    }
}