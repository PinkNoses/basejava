package com.urise.webapp.storage;

import com.urise.webapp.storage.serializationStrategy.ObjectStreamSerializer;

class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}