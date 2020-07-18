package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        pasteResume(resume, checkNotExistResume(resume.getUuid()));
    }

    public void update(Resume resume) {
        updateResume(resume, checkExistResume(resume.getUuid()));
    }

    public Resume get(String uuid) {
        return getResume(checkExistResume(uuid));
    }

    public void delete(String uuid) {
        deleteResume(checkExistResume(uuid));
    }

    private Object checkExistResume(String uuid) {
        if (!isContainsResume(getSearchKey(uuid))) {
            throw new NotExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    private Object checkNotExistResume(String uuid) {
        if (isContainsResume(getSearchKey(uuid))) {
            throw new ExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    protected abstract void pasteResume(Resume resume, Object searchKey);

    protected abstract void updateResume(Resume resume, Object searchKey);

    protected abstract void deleteResume(Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isContainsResume(Object searchKey);
}
