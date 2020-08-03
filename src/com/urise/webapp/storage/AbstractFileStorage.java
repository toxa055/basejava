package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        for (File file : directory.listFiles()) {
            file.delete();
        }
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        file.delete();
    }

    @Override
    protected Resume doGet(File file) {
        Resume resume;
        try {
            resume = doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO Error", file.getName(), e);
        }
        return resume;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        for (File file : directory.listFiles()) {
            try {
                list.add(doRead(file));
            } catch (IOException e) {
                throw new StorageException("IO Error", file.getName(), e);
            }
        }
        return list;
    }

    public abstract void doWrite(Resume resume, File file) throws IOException;

    public abstract Resume doRead(File file) throws IOException;
}
