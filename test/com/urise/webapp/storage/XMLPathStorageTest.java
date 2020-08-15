package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.XmlStreamStrategy;

public class XMLPathStorageTest extends AbstractStorageTest {
    public XMLPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new XmlStreamStrategy()));
    }
}
