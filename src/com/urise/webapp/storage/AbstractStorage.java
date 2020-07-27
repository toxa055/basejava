package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        doSave(resume, getNotExistedSearchKey(resume.getUuid()));
    }

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        doUpdate(resume, getExistedSearchKey(resume.getUuid()));
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return doGet(getExistedSearchKey(uuid));
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        doDelete(getExistedSearchKey(uuid));
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted.");
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume with " + uuid + " uuid does not exist.");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume with " + uuid + " uuid already exists.");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> doCopyAll();
}
