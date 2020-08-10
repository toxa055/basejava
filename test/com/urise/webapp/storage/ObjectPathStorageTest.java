package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.ObjectStreamStrategy;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStreamStrategy()));
    }
}