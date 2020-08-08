package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Path error", null, e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path file) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getFileName(), file.toString(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    protected void doUpdate(Resume resume, Path file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file.toString())));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Path delete error", file.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file.toString())));
        } catch (IOException e) {
            throw new StorageException("Path read error", file.toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<>();
        try {
            Files.list(directory).forEach(r -> list.add(doGet(r)));
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
        return list;
    }

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;
}
