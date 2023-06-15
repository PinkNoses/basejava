package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializationStrategy.SerializationStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private final SerializationStrategy strategy;

    protected PathStorage(String dir, SerializationStrategy strategy) {
        Objects.requireNonNull(dir, "directory must not be null");
        Objects.requireNonNull(strategy, "strategy must not be null");

        directory = Paths.get(dir);
        this.strategy = strategy;
        if (!(Files.isDirectory(directory) || Files.isWritable(directory) || Files.isReadable(directory))) {
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
            strategy.writeResume(resume, new BufferedOutputStream(Files.newOutputStream(path)));
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
            return strategy.readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path was not get", fileName(path), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getFileStream().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFileStream().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getFileStream().count();
    }

    private Stream<Path> getFileStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Get file list error", e);
        }
    }

    private String fileName(Path path) {
        return path.getFileName().toString();
    }
}