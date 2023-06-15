package com.urise.webapp.storage;

import com.urise.webapp.storage.serializationStrategy.XmlStreamSerializer;

class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}