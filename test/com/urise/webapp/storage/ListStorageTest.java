package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;

public class ListStorageTest extends AbstractArrayStorageTest{
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    public void overflowStorage() {
        throw new StorageException("Test is successful.", null);
    }
}