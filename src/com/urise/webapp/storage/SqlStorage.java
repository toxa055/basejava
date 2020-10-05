package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final Logger LOG = Logger.getLogger(SqlStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("Clear table resume.");
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume " +
                            "(uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insertContact(resume, conn);
                    insertSection(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, resume.getUuid());
                        executeUpdateAndCheckExists(ps, resume.getUuid());
                    }
                    deleteFromTable(conn, resume, "DELETE FROM contact where resume_uuid=?");
                    deleteFromTable(conn, resume, "DELETE FROM section where resume_uuid=?");
                    insertContact(resume, conn);
                    insertSection(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT * " +
                    "FROM resume r " +
                    "LEFT JOIN contact c " +
                    "ON r.uuid = c.resume_uuid " +
                    "WHERE r.uuid =? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(resume, rs);
                } while (rs.next());
            }
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT * " +
                    "FROM resume r " +
                    "LEFT JOIN section s " +
                    "ON r.uuid = s.resume_uuid " +
                    "WHERE r.uuid =? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resume, rs);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            executeUpdateAndCheckExists(ps, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted.");
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    map.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(map.get(rs.getString("resume_uuid")), rs);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(map.get(rs.getString("resume_uuid")), rs);
                }
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        LOG.info("Size of table resume.");
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void executeUpdateAndCheckExists(PreparedStatement ps, String uuid) throws SQLException {
        if (ps.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            resume.addContact(ContactType.valueOf(type), rs.getString("value"));
        }
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            SectionType sectionType = SectionType.valueOf(type);
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new SimpleTextSection(rs.getString("value")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] items = rs.getString("value").split("\n");
                    resume.addSection(sectionType, new ListSection(items));
                    break;
                default:
                    break;
            }
        }
    }

    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                StringBuilder value = new StringBuilder();
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        value = new StringBuilder(((SimpleTextSection) entry.getValue()).getDescription());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        for (String line : ((ListSection) entry.getValue()).getItems()) {
                            value.append(line).append("\n");
                        }
                        break;
                    default:
                        break;
                }
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, value.toString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteFromTable(Connection conn, Resume resume, String query) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }
}
