package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

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
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            writeResume(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File was not save", file.getName(), e);
        }
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            writeResume(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File was not update", file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("File was not delete", file.getName());
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File was not get", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] listFiles = directory.listFiles();
        List<Resume> listOfResumes = new ArrayList<>();
        if (listFiles == null) {
            throw new StorageException("IO error", null);
        }
        for (File file : listFiles) {
            Resume resume = getResume(file);
            listOfResumes.add(resume);
        }
        return listOfResumes;
    }

    @Override
    public void clear() {
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            throw new StorageException("IO error", null);
        }
        for (File file : listFiles) {
            deleteResume(file);
        }
    }

    @Override
    public int size() {
        File[] resumes = directory.listFiles();
        if (resumes == null) {
            throw new StorageException("IO error", null);
        }
        return resumes.length;
    }

    protected abstract void writeResume(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume readResume(InputStream is) throws IOException;
}