package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void perform(String query, SqlPerformer sqlPerformer) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            sqlPerformer.performQuery(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T receive(String query, SqlReceiver<T> sqlReceiver) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            return sqlReceiver.receiveResult(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface SqlPerformer {
        void performQuery(PreparedStatement ps) throws SQLException;
    }

    public interface SqlReceiver<T> {
        T receiveResult(PreparedStatement ps) throws SQLException;
    }
}
