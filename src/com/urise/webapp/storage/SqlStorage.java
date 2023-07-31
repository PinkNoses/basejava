package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume newResume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, newResume.getUuid());
                ps.setString(2, newResume.getFullName());
                ps.execute();
            }
            saveContacts(conn, newResume);
            saveSections(conn, newResume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(conn, resume);
            deleteSections(conn, resume);
            saveContacts(conn, resume);
            saveSections(conn, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0)
                        throw new NotExistStorageException(uuid);
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "    SELECT * FROM resume " +
                "LEFT JOIN contact " +
                "       ON resume.uuid = contact.resume_uuid " +
                "LEFT JOIN section " +
                "       ON resume.uuid = section.resume_uuid " +
                "    WHERE resume.uuid =?\n",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, resume);
                        addSection(rs, resume);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>(size());
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resume_uuid = rs.getString("resume_uuid");
                    addContact(rs, resumes.get(resume_uuid));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String resume_uuid = rs.getString("resume_uuid");
                    addSection(rs, resumes.get(resume_uuid));
                }
            }
            return resumes.values().stream().toList();
        });
    }


    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume",
                ps -> {
                    ps.execute();
                    return null;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                });
    }

    private void saveContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "        INSERT " +
                "  INTO contact (type, value, resume_uuid) " +
                "VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, e.getKey().name());
                ps.setString(2, e.getValue());
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void saveSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "        INSERT " +
                "  INTO section (section_type, content, resume_uuid) " +
                "VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, e.getKey().name());
                Section section = e.getValue();
                ps.setString(2, JsonParser.write(section, Section.class));
                ps.setString(3, resume.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid =?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void deleteSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid =?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            resume.addContact(type, value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("section_type"));
            resume.addSection(sectionType, JsonParser.read(content, Section.class));
        }
    }
}