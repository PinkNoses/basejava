package com.urise.webapp.storage;

import com.urise.webapp.storage.serializationStrategy.ObjectStreamSerializer;

import java.io.File;

class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new ObjectStreamSerializer()));
    }
}