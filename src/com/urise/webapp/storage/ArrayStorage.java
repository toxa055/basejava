package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int index = indexOfFoundElement(resume.getUuid());
        if (index == -1) {
            if (size == storage.length) {
                System.out.println("Storage is overfilled. Resume with " + resume.getUuid() + " uuid will not be saved.");
            } else {
                storage[size] = resume;
                size++;
            }
        } else {
            System.out.println("Resume with " + resume.getUuid() + " uuid already exists.");
        }
    }

    public int indexOfFoundElement(String uuid) {
        if (uuid != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void update(Resume resume) {
        int index = indexOfFoundElement(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
            System.out.println("Resume with " + resume.getUuid() + " uuid has been updated.");
        } else {
            System.out.println("Resume with " + resume.getUuid() + " uuid does not exist.");
        }
    }

    public Resume get(String uuid) {
        int index = indexOfFoundElement(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("Resume with " + uuid + " uuid does not exist.");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = indexOfFoundElement(uuid);
        if (index != -1) {
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume with " + uuid + " uuid does not exist.");
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
