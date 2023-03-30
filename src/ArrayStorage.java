import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int countResume;

    void clear() {
        Arrays.fill(storage, 0, countResume, null);
        countResume = 0;
    }

    void save(Resume newResume) {
        storage[countResume] = newResume;
        countResume++;
    }

    Resume get(String uuid) {
        int index = indexResume(uuid);
        if (index != -1) {
            return storage[index];
        }
        return null;
    }

    int indexResume(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    void delete(String uuid) {
        int index = indexResume(uuid);
        storage[index] = null;
        System.arraycopy(storage, index + 1, storage, index, countResume);
        countResume--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    int size() {
        return countResume;
    }
}
