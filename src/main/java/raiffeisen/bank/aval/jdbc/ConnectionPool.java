package raiffeisen.bank.aval.jdbc;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import raiffeisen.bank.aval.file_operations.Configs;
import raiffeisen.bank.aval.file_operations.ConfigsSingleton;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public enum ConnectionPool {

    INSTANCE;

    private static Context initContext;
    private static Context envContext;
    private static DataSource dataSource;
    private Connection connection;

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);
    private static Configs configs = ConfigsSingleton.INSTANCE;

    private static GenericObjectPool gPool = null;

    private DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPool.class) {
                if (dataSource == null) {
                    try {
                        initContext = new InitialContext();
                        envContext = (Context) initContext.lookup("java:/comp/env");
                        dataSource = (DataSource) envContext.lookup("jdbc/myoracle");

                    } catch (NamingException e) {
                        logger.error(e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }
        return dataSource;
    }

    public Connection getConnection() {
        try {
            if (dataSource == null) {
                synchronized (ConnectionPool.class) {
                    if (dataSource == null) {
                        dataSource = getDataSource();
                    }
                }
            }
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

