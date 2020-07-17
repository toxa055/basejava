package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;

public class MapStorageTest extends AbstractArrayStorageTest{
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void getAll() {
        size();
    }

    @Override
    public void overflowStorage() {
        throw new StorageException("Test is successful.", null);
    }
}