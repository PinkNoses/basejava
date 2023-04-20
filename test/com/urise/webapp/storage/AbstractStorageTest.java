package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);

    private static final String UUID_NEW = "uuid_new";
    private static final Resume NEW_RESUME = new Resume(UUID_NEW);

    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume NOT_EXIST_RESUME = new Resume(UUID_NOT_EXIST);

    private static final String UUID_UPDATE = "uuid_update";
    private static final Resume UPDATE_RESUME = new Resume(UUID_UPDATE);

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void save() {
        assertGetNotExist(NEW_RESUME);
        storage.save(NEW_RESUME);
        assertSize(storage.size());
    }

    @Test
    public void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(resume1));
    }

    @Test
    public void delete() {
        storage.delete(resume2.getUuid());
        assertSize(2);
        assertGetNotExist(resume2);
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Test
    public void update() {
        storage.save(UPDATE_RESUME);
        storage.update(UPDATE_RESUME);
        assertSame(UPDATE_RESUME, storage.get(UUID_UPDATE));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(NOT_EXIST_RESUME));
    }

    @Test
    public void get() {
        assertGet(resume2);
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> assertGetNotExist(storage.get(UUID_NOT_EXIST)));
    }

    @Test
    public void getAll() {
        Resume[] expected = storage.getAll();
        Resume[] actual = new Resume[]{resume1, resume2, resume3};
        assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(storage.getAll(), new Resume[0]);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertGetNotExist(Resume resume) {
        assertThrows(NotExistStorageException.class, () -> assertGet(resume));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}