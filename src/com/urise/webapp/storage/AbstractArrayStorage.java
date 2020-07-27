package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList((Arrays.copyOf(storage, size)));
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow. Resume will not be saved.", resume.getUuid());
        } else {
            insertResume(resume, searchKey);
            size++;
        }
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected void doDelete(Integer searchKey) {
        removeResume(searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return (searchKey) > -1;
    }

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index);
}
