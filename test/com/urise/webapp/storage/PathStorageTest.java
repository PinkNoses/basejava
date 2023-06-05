package com.urise.webapp.storage;

import com.urise.webapp.storage.serializationStrategy.ObjectStreamStorage;

class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}