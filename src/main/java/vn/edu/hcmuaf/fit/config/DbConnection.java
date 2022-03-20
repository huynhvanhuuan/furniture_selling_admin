package vn.edu.hcmuaf.fit.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbConnection implements IConnectionPool {
    private static final int MAX_TIMEOUT = 30;
    private String uid;
    private String pwd;
    private String database;
    private static final int MAX_POOL_SIZE = 50;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();

    public static DbConnection init(String uid, String pwd, String database) {
        List<Connection> pool = new ArrayList<>(MAX_POOL_SIZE);
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            pool.add(createConnection(uid, pwd, database));
        }
        return new DbConnection(uid, pwd, database, pool);
    }

    private DbConnection(String uid, String pwd, String database, List<Connection> pool) {
        this.uid = uid;
        this.pwd = pwd;
        this.database = database;
        this.connectionPool = pool;
    }

    @Override
    public Connection getConnection() {
        try {
            if (connectionPool.isEmpty()) {
                if (usedConnections.size() < MAX_POOL_SIZE) {
                    connectionPool.add(createConnection(uid, pwd, database));
                } else {
                    throw new RuntimeException("Maximum pool size reached, no available connections!");
                }
            }
            Connection connection = connectionPool.remove(connectionPool.size() - 1);
            if (!connection.isValid(MAX_TIMEOUT)) {
                connection = createConnection(uid, pwd, database);
            }
            usedConnections.add(connection);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void releaseConnection(Connection connection) {
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }

    private static Connection createConnection(String uid, String pwd, String database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            return DriverManager.getConnection("jdbc:mysql://localhost/" + database, uid, pwd);
        } catch (Exception e) {
            return null;
        }
    }

    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public List<Connection> getConnectionPool() {
        return connectionPool;
    }

    public void setConnectionPool(List<Connection> connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Connection> getUsedConnections() {
        return usedConnections;
    }

    public void setUsedConnections(List<Connection> usedConnections) {
        this.usedConnections = usedConnections;
    }
}
