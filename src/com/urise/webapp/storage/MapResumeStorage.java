package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void pasteResume(Resume resume, Object searchKey) {
        storage.put(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        storage.put(((Resume) searchKey).getUuid(), resume);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return new Resume(uuid);
    }

    @Override
    protected boolean isContainsResume(Object searchKey) {
        return storage.containsValue(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList(storage.values());
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
