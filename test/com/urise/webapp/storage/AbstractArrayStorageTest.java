package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static String UUID_4 = "uuid4";
    private final static String UUID_5 = "uuid5";

    private final static Resume resume4 = new Resume(UUID_4);
    private final static Resume resume5 = new Resume(UUID_5);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        storage.save(resume4);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(resume5);
        Resume resume = storage.get(resume5.getUuid());
        Assert.assertEquals(resume, resume5);
    }

    @Test
    public void saveCheckSize() {
        int actual = storage.size();
        storage.save(resume5);
        Assert.assertEquals(storage.size(), ++actual);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume4);
    }

    @Test
    public void update() {
        storage.update(resume4);
        Resume resume = storage.get(resume4.getUuid());
        Assert.assertEquals(resume, resume4);
    }

    @Test
    public void get() {
        Resume resume = storage.get(resume4.getUuid());
        Assert.assertEquals(resume, resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(resume4.getUuid());
        storage.get(resume4.getUuid());
    }

    @Test
    public void deleteCheckSize() {
        int actual = storage.size();
        storage.delete(resume4.getUuid());
        Assert.assertEquals(storage.size(), --actual);
    }

    @Test
    public void getAll() {
        Assert.assertEquals(storage.getAll().length, storage.size());
    }

    @Test
    public void getAllCheckNotNull() {
        for (Resume resume : storage.getAll()) {
            Assert.assertNotNull(resume);
        }
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(resume5.getUuid());
    }

    @Test
    public void overfillStorage() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        try {
            storage.save(new Resume());
            Assert.fail();
        } catch (StorageException e) {
            e.printStackTrace();
        }
    }
}