package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.DataStreamStrategy;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new DataStreamStrategy()));
    }
}