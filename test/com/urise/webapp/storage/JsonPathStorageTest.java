package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.JsonStreamStrategy;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new JsonStreamStrategy()));
    }
}
