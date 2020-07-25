package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(list);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        list.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        list.set((int) searchKey, resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        list.remove((int) searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return list.get((int) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return ((int) searchKey) > -1;
    }
}
