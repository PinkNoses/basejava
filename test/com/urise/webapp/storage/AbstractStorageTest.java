package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    ResumeTestData resumeTestData = new ResumeTestData();

    /*    private static final String UUID_1 = "uuid1";
        private static final String UUID_2 = "uuid2";
        private static final String UUID_3 = "uuid3";*/
    private static final String FULL_NAME_1 = "Ivanov";
    private static final String FULL_NAME_2 = "Malinka";
    private static final String FULL_NAME_3 = "Tom";
    private static final String FULL_NAME_4 = "1_Resume without contacts";

    private final Resume resume1 = resumeTestData.createResume(FULL_NAME_1);
    private final Resume resume2 = resumeTestData.createResume(FULL_NAME_2);
    private final Resume resume3 = resumeTestData.createResume(FULL_NAME_3);
    private final Resume resume4 = new Resume(FULL_NAME_4);

    //private static final String UUID_NEW = "uuid_new";
    private static final String FULL_NAME_NEW = "Popova";
    private final Resume NEW_RESUME = resumeTestData.createResume(FULL_NAME_NEW);

    //private static final String UUID_NOT_EXIST = "dummy";
    private static final String FULL_NAME_NOT_EXIST = "Sokolova";
    private final Resume NOT_EXIST_RESUME = resumeTestData.createResume(FULL_NAME_NOT_EXIST);

    //private static final String UUID_UPDATE = "uuid_update";
    private static final String FULL_NAME_UPDATE = "fullName_update";
    private final Resume UPDATE_RESUME = resumeTestData.createResume(FULL_NAME_UPDATE);


    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
        storage.save(resume4);
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
        assertSize(3);
        assertGetNotExist(resume2);
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(NOT_EXIST_RESUME.getUuid()));
    }

    @Test
    public void update() {
        storage.save(UPDATE_RESUME);
        storage.update(UPDATE_RESUME);
        assertEquals(UPDATE_RESUME, storage.get(UPDATE_RESUME.getUuid()));
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
        assertThrows(NotExistStorageException.class, () -> assertGetNotExist(storage.get(NOT_EXIST_RESUME.getUuid())));
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = storage.getAllSorted();
        List<Resume> actual = Arrays.asList(resume4, resume1, resume2, resume3);
        assertEquals(expected, actual);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(storage.getAllSorted().toArray(), new Resume[0]);
    }

    @Test
    public void size() {
        assertSize(4);
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