package com.urise.webapp.storage;

import com.urise.webapp.storage.serializationStrategy.ObjectStreamStorage;

import java.io.File;

class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new ObjectStreamStorage()));
    }
}