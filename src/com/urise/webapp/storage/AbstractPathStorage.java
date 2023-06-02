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
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!(Files.isDirectory(directory) || Files.isWritable(directory))) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable/readable");
        }
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Failed to create file", fileName(path), e);
        }
        updateResume(resume, path);
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            writeResume(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path was not update/save", fileName(path), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new StorageException("Path was not delete", fileName(path), e);
        }
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path was not get", fileName(path), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> listOfResumes = new ArrayList<>();
        try {
            Files.list(directory).map(this::getResume).forEach(listOfResumes::add);
        } catch (IOException e) {
            throw new StorageException("Path copy error", null, e);
        }
        return listOfResumes;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        return doCopyAll().size();

    }

    private String fileName(Path path) {
        return path.getFileName().toString();
    }

    protected abstract void writeResume(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume readResume(InputStream is) throws IOException;
}