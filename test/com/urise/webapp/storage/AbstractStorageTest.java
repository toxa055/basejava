package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.SimpleTextSection;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static com.urise.webapp.ResumeTestData.fillResume;

public abstract class AbstractStorageTest {
    protected Storage storage;
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    private final static String UUID_1 = UUID.randomUUID().toString();
    private final static String UUID_2 = UUID.randomUUID().toString();
    private final static String UUID_3 = UUID.randomUUID().toString();
    private final static String UUID_4 = UUID.randomUUID().toString();
    private final static String UUID_5 = UUID.randomUUID().toString();

    private final static Resume RESUME_1 = new Resume(UUID_1, "Petrov Ivan");
    private final static Resume RESUME_2 = fillResume(UUID_2, "Petrov Aleksey");
    private final static Resume RESUME_3 = fillResume(UUID_3, "Ivanov Andrey");
    private final static Resume RESUME_4 = fillResume(UUID_4, "Sidorov Pavel");
    public final static Resume RESUME_5 = fillResume(UUID_5, "Sidorov Pavel");

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
        Resume resume = new Resume(UUID_4, "dummy");
        resume.addContact(ContactType.SKYPE, "new_skype");
        resume.addSection(SectionType.OBJECTIVE, new SimpleTextSection("new objective"));
        storage.update(resume);
        assertGet(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(fillResume("dummy", "dummy"));
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
        storage.delete(UUID_5);
    }

    @Test
    public void getAllSorted() {
        storage.save(RESUME_5);
        List<Resume> actualList = storage.getAllSorted();
        List<Resume> expectedList = Arrays.asList(RESUME_1, RESUME_2, RESUME_3, RESUME_4, RESUME_5);
        Collections.sort(expectedList);
        assertSize(5);
        assertEquals(expectedList, actualList);
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