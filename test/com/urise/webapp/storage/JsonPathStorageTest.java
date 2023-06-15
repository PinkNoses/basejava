package com.urise.webapp.storage;

import com.urise.webapp.storage.serializationStrategy.JsonStreamSerializer;

class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}