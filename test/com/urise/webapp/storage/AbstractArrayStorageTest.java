package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static String UUID_4 = "uuid4";
    private final static String UUID_5 = "uuid5";

    private final static Resume RESUME_1 = new Resume(UUID_1);
    private final static Resume RESUME_2 = new Resume(UUID_2);
    private final static Resume RESUME_3 = new Resume(UUID_3);
    private final static Resume RESUME_4 = new Resume(UUID_4);
    private final static Resume RESUME_5 = new Resume(UUID_5);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        storage.save(RESUME_4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        int expectedSize = storage.size();
        storage.save(RESUME_5);
        Resume resume = storage.get(UUID_5);
        assertEquals(++expectedSize, storage.size());
        assertEquals(RESUME_5, resume);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_4);
    }

    @Test
    public void update() {
        storage.update(RESUME_4);
        Resume resume = storage.get(UUID_4);
        assertEquals(RESUME_4, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_5);
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_4);
        assertEquals(RESUME_4, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_5);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int expectedSize = storage.size();
        storage.delete(UUID_4);
        assertEquals(--expectedSize, storage.size());
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.get(UUID_5);
    }

    @Test
    public void getAll() {
        Resume[] expectedResumes = {RESUME_1, RESUME_2, RESUME_3, RESUME_4};
        Assert.assertArrayEquals(expectedResumes, storage.getAll());
    }

    @Test
    public void getAllCheckNotNull() {
        for (Resume resume : storage.getAll()) {
            Assert.assertNotNull(resume);
        }
    }

    @Test
    public void size() {
        assertEquals(4, storage.size());
    }

    @Test (expected = StorageException.class)
    public void overflowStorage() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Test failed.");
        }
        storage.save(new Resume());
    }
}