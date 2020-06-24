/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[size()] = r;
    }

    Resume get(String uuid) {
        for (Resume resume : getAll()) {
            if (resume.uuid.equals(uuid))
                return resume;
        }
        return null;
    }

    void delete(String uuid) {
        int size = size();
        boolean isDeletedElementExist = false;
        int indexOfDeletedElement = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                indexOfDeletedElement = i;
                isDeletedElementExist = true;
                break;
            }
        }
        if (isDeletedElementExist) {
            for (int i = indexOfDeletedElement; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            storage[size - 1] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size()];
        for (int i = 0; i < size(); i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null)
                return i;
        }
        return storage.length;
    }
}
