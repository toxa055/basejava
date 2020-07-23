package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    public void save(Resume resume) {
        pasteResume(resume, getNotExistedSearchKey(resume.getUuid()));
    }

    public void update(Resume resume) {
        updateResume(resume, getExistedSearchKey(resume.getUuid()));
    }

    public Resume get(String uuid) {
        return getResume(getExistedSearchKey(uuid));
    }

    public void delete(String uuid) {
        deleteResume(getExistedSearchKey(uuid));
    }

    private Object getExistedSearchKey(String uuid) {
        if (!isContainsResume(getSearchKey(uuid))) {
            throw new NotExistStorageException(uuid);
        }
        return getSearchKey(uuid);
    }

    private Object getNotExistedSearchKey(String uuid) {
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
