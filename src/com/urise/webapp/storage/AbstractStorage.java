package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            pasteResume(resume, index);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            updateResume(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            return getResume(uuid, index);
        }
        throw new NotExistStorageException(uuid);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            deleteResume(uuid, index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void pasteResume(Resume resume, int index);

    protected abstract void updateResume(Resume resume, int index);

    protected abstract void deleteResume(String uuid, int index);

    protected abstract Resume getResume(String uuid, int index);

    protected abstract int getIndex(String uuid);
}
