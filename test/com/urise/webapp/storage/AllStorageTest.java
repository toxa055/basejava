package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.DataStreamStrategy;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        XMLPathStorageTest.class,
        JsonPathStorageTest.class,
        DataStreamStrategy.class
})

public class AllStorageTest {
}
