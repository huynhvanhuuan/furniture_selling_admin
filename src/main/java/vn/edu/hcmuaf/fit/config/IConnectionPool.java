package vn.edu.hcmuaf.fit.config;

import java.sql.Connection;

public interface IConnectionPool {
    Connection getConnection();
    void releaseConnection(Connection connection);
}
