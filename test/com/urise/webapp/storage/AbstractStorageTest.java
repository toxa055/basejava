package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private final static String UUID_0 = "uuid0";
    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static String UUID_4 = "uuid4";
    private final static String UUID_5 = "uuid5";

    private final static String NAME_1 = "Petrov Ivan";
    private final static String NAME_2 = "Petrov Aleksey";
    private final static String NAME_3 = "Ivanov Andrey";
    private final static String NAME_4 = "Sidorov Pavel";
    private final static String NAME_5 = "Sidorov Pavel";

    private final static Resume RESUME_1 = new Resume(UUID_1, NAME_1);
    private final static Resume RESUME_2 = new Resume(UUID_2, NAME_2);
    private final static Resume RESUME_3 = new Resume(UUID_3, NAME_3);
    private final static Resume RESUME_4 = new Resume(UUID_4, NAME_4);
    private final static Resume RESUME_5 = new Resume(UUID_5, NAME_5);
    private final static Resume RESUME_6 = new Resume(UUID_0, NAME_5);

    protected AbstractStorageTest(Storage storage) {
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
        assertSize(0);
    }

    @Test
    public void save() {
        storage.save(RESUME_5);
        assertSize(5);
        assertGet(RESUME_5);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_4);
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_4));
        Resume resume = storage.get(UUID_4);
        assertEquals(RESUME_4, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_5));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
        assertGet(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_5);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_4);
        assertSize(3);
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.get(UUID_5);
    }

    @Test
    public void getAll() {
        storage.save(RESUME_6);
        Resume[] expectedResumes = {RESUME_3, RESUME_2, RESUME_1, RESUME_6, RESUME_4};
        Resume[] resumes = storage.getAllSorted().toArray(new Resume[0]);
        Arrays.stream(resumes).forEach(System.out::println);
        System.out.println("======");
        Assert.assertArrayEquals(expectedResumes, resumes);
    }

    @Test
    public void size() {
        assertSize(4);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}