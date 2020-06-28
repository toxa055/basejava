package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (!isResumeExist(r.getUuid())) {
            if (size == 10000) {
                System.out.println("Storage is overfilled. Resume will not be saved.");
            } else {
                storage[size] = r;
                size++;
            }
        } else {
            System.out.println("Resume already exists.");
        }
    }

    public boolean isResumeExist(String uuid) {
        if (uuid != null) {
            for (Resume resume : getAll()) {
                if (resume.getUuid().equals(uuid)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void update(Resume r) {
        if (isResumeExist(r.getUuid())) {
            System.out.println("Resume will be updated.");
        } else {
            System.out.println("Resume does not exist.");
        }
    }

    public Resume get(String uuid) {
        if (isResumeExist(uuid)) {
            for (Resume resume : getAll()) {
                if (resume.getUuid().equals(uuid)) {
                    return resume;
                }
            }
        } else {
            System.out.println("Resume does not exist.");
        }
        return null;
    }

    public void delete(String uuid) {
        if (isResumeExist(uuid)) {
            int indexOfDeletedElement = -1;
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    indexOfDeletedElement = i;
                    break;
                }
            }
            if (indexOfDeletedElement != -1) {
                for (int i = indexOfDeletedElement; i < size - 1; i++) {
                    storage[i] = storage[i + 1];
                }
                storage[size - 1] = null;
                size--;
            }
        } else {
            System.out.println("Resume does not exist.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
